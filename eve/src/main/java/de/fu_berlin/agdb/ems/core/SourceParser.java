/**
 * 
 */
package de.fu_berlin.agdb.ems.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.data.IEvent;
import de.fu_berlin.agdb.ems.inputadapters.IInputAdapter;
import de.fu_berlin.agdb.ems.loader.ILoader;

/**
 * Loads sources from XML text/files.
 * @author Ralf Oechsner
 *
 */
public class SourceParser implements Processor {

	public static final String LOADER_PACKAGE = "de.fu_berlin.agdb.ems.loader";
	public static final String INPUT_ADAPTER_PACKAGE = "de.fu_berlin.agdb.ems.inputadapters";
	public static final String LOADER_PREFIX = "loader";
	public static final String INPUT_ADAPTER_PREFIX = "inputAdapter";
	
	private static Logger logger = LogManager.getLogger();
	
	private URL loaderPath;
	private URL inputAdapterPath;
	
	// Stores the declarations of the constructors of all loader and input adapter classes
	// The map stores a list of constructor declarations for the class name (in the String).
	// The second list contains all tags of a declaration.
	private Map<String, List<List<String>>> loaderParameters;
	private Map<String, List<List<String>>> inputAdapterParameters;
	
	/**
	 * Loads the source parser.
	 */
	public SourceParser() {
		
		this.loaderPath = Thread.currentThread().getContextClassLoader().getResource(LOADER_PACKAGE.replace(".", "/"));
		this.inputAdapterPath = Thread.currentThread().getContextClassLoader().getResource(INPUT_ADAPTER_PACKAGE.replace(".", "/"));
		this.generateParameters();
	}
	
	public SourceParser(URL loaderPath, URL inputAdapterPath) {
		
		this.loaderPath = loaderPath;
		this.inputAdapterPath = inputAdapterPath;
		this.generateParameters();
	}
	
	/**
	 * Generate parameter lists of all constructors of all loaders and inputAdapters.
	 */
	private void generateParameters() {

		// first look for loaders in the loader package
		this.loaderParameters = new Hashtable<String, List<List<String>>>();
		List<Class<?>> loaders = this.findClasses(ILoader.class, loaderPath, LOADER_PACKAGE);
		for (Class<?> curLoader : loaders) {
			this.loaderParameters.put(curLoader.getSimpleName(), this.classParameters(curLoader));
		}
		logger.info("Found the following loader plugins: " + loaderParameters.keySet());
		
		// then for input adapters in the input adapter package
		this.inputAdapterParameters = new Hashtable<String, List<List<String>>>();
		List<Class<?>> inputAdapters = this.findClasses(IInputAdapter.class, inputAdapterPath, INPUT_ADAPTER_PACKAGE);
		for (Class<?> curInputAdapter : inputAdapters) {
			this.inputAdapterParameters.put(curInputAdapter.getSimpleName(), this.classParameters(curInputAdapter));
		}
		logger.info("Found the following input adapter plugins: " + inputAdapterParameters.keySet());
	}
	
	/**
	 * Find all classes in a given package that implement a given interface.
	 * @param interfaceImplemented interface classes have implement.
	 * @param classPath path to classes.
	 * @param packageName package name of classes.
	 * @return list of classes in the package that implement the interface.
	 */
	private List<Class<?>> findClasses(Class<?> interfaceImplemented, URL classPath, String packageName) {
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		//URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classPath });

		// filter .class files.
		File[] files;
		try {
			files = new File(URLDecoder.decode(classPath.getFile(), "UTF-8")).listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith(".class");
			    }
			});
			
			// find classes that are implementing the interface and add them to the class list
			for (File file : files) {
			    String className = file.getName().replaceAll(".class$", "");
			    Class<?> cls;
				try {
					cls = Class.forName(packageName + "." + className, true, classLoader);
//					cls = Class.forName(className, true, classLoader);
					if (!cls.isInterface()) {
						if (interfaceImplemented.isAssignableFrom(cls)) {
							classes.add((Class<?>) cls);
						}
					}
				} catch (ClassNotFoundException e) {
					// shouldn't happen because only classes that are found before are searched for again
					logger.error(e);
				}
			}
		} catch (UnsupportedEncodingException e1) {
			// also shouldn't happen because of file system encoding
			logger.error(e1);
		}
		
		return classes;
	}
	
	
	/**
	 * Generates a list of all parameters for every constructor of a class. Every list entry represents one
	 * constructor where the entry itself is a map which contains all the parameters. Each key is defined by a
	 * Tag annotation and the value is the type of the parameter.
	 * @param c class for which the parameters should be generated.
	 * @return list of maps with constructor parameters.
 	 */
	private List<List<String>> classParameters(Class<?> c) {
		
		List<List<String>> parametersList = new ArrayList<List<String>>();
		
		Constructor<?>[] constructors = c.getConstructors();
		for (Constructor<?> curConstr : constructors) {
			
			// new parameter map for every constructor
			List<String> parameters = new ArrayList<String>();
			parametersList.add(parameters);

			Annotation[][] tags = curConstr.getParameterAnnotations();
			for(Annotation[] annotations : tags){

				for(Annotation annotation : annotations) {

					if (annotation instanceof Tag) {
						Tag tagAnn = (Tag) annotation;
						parameters.add(tagAnn.value());
					}
				}
			}
		}
		
		return parametersList;
	}
	
	/**
	 * Instantiate a class with given parameters for it's constructor.
	 * @param className name of class that is instantiated (full package name).
	 * @param parameters list of tags of parameters (in the right order).
	 * @param prop property object where parameter values are stored.
	 * @param prefix prefix of the input adapter / loader.
	 * @return instantiation of the class.
	 */
	public Object loadClass(String className, List<String> parameters, Properties prop, String prefix) {
		
		try {
			Class<?> loaderClass = Class.forName(className);
			
			// parameter must be always Strings
			Class<?>[] paramTypes = new Class[parameters.size()];
			for (int i = 0; i < paramTypes.length; i++)
				paramTypes[i] = String.class;
			Constructor<?> constructor = loaderClass.getConstructor(paramTypes);
			
			// build array with values in right order
			Object[] paramValues = new Object[parameters.size()];
			int i = 0;
			for (String curEntry : parameters) {
				paramValues[i++] = prop.getProperty(prefix + "." + curEntry);
			}
			
			return constructor.newInstance(paramValues);
		} catch (ClassNotFoundException e) {
			// should not happen because only classes that where previously found are loaded
			logger.error(e);
		} catch (InstantiationException e) {
			// should also not happen because of previous reason
			logger.error(e);
		} catch (IllegalAccessException e) {
			// same
			logger.error(e);
		} catch (IllegalArgumentException e) {
			// same
			logger.error(e);
		} catch (InvocationTargetException e) {
			// same
			logger.error(e);
		} catch (NoSuchMethodException e) {
			// same
			logger.error(e);
		} catch (SecurityException e) {
			// same
			logger.error(e);
		}		
		
		return null;
	}
	
	/**
	 * Parses source definition.
	 * @param source source definition.
	 */
	public IInputAdapter parse(String source) {
		
		// all interests are "properties files"
		Properties prop = new Properties();
		try {
			prop.load(new ByteArrayInputStream(source.getBytes("UTF-8")));

			// first the loader
			String loader = prop.getProperty("loader");
			logger.info("Loader specified in source file: " + loader);
			List<List<String>> loaderParameters = this.loaderParameters.get(loader);
			if (loaderParameters == null) {
				logger.error("Loader specified in source file could not be found! Not loading source.");
				return null;
			}
				
			ILoader loaderObject = null;
			
			for (List<String> curParameters : loaderParameters) {
				
				// check if all parameters of the properties file match the parameters of the loader
				if (curParameters.equals(this.getPropertiesWithPrefix(LOADER_PREFIX, prop))) {
					
					// now load class
				    loaderObject = (ILoader) this.loadClass(LOADER_PACKAGE + "." + loader, curParameters, prop, LOADER_PREFIX);
				    break; // only first possible match is used
				}
			}

			// when no loader was found there's no need for parsing the input adapter
			if (loaderObject == null) {
				logger.error("No matching loader declaration found! Not loading source.");
				return null;
			}
			
			// now the input adapter
			IInputAdapter inputAdapterObject = null;
			String inputAdapter = prop.getProperty(INPUT_ADAPTER_PREFIX);
			List<List<String>> inputAdapterParameters = this.inputAdapterParameters.get(inputAdapter);
			if (inputAdapterParameters == null) {
				logger.error("Input adapter specified in source file could not be found! Not loading source.");
				return null;
			}
			
			for (List<String> curParameters : inputAdapterParameters) {
				
				if (equalsIgnoringOrder(curParameters, this.getPropertiesWithPrefix(INPUT_ADAPTER_PREFIX, prop))) {
			
					inputAdapterObject = (IInputAdapter) this.loadClass(INPUT_ADAPTER_PACKAGE + "." + inputAdapter, curParameters, prop, INPUT_ADAPTER_PREFIX);
					break; // only first possible input adapter (shouldn't be more than one anyway)
				}
			}
			
			if (inputAdapterObject != null) {
				logger.info("Loading source.");
				loaderObject.load();
				logger.info("Done.");
				logger.info("Importing events.");
				inputAdapterObject.load(loaderObject.getText());
				logger.info("Done.");
			}
			
			return inputAdapterObject;
		} catch (UnsupportedEncodingException e) {
			// shouldn't happen because all internal encoding is done in UTF-8
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		// should never happen. Only for compiling reasons.
		return null;
	}
	
	/**
	 * Finds properties that start with a certain prefix separated by a "." and returns them in
	 * a new list without the prefixes.
	 * @param prefix prefix of property.
	 * @param properties properties object.
	 * @return subset of properties starting without the prefix.
	 */
	private List<String> getPropertiesWithPrefix(String prefix, Properties properties) {

		List<String> list = new ArrayList<String>();
		
		for (String curString : properties.stringPropertyNames()) {
			if (curString.startsWith(prefix + ".")) {
				list.add(curString.substring(prefix.length() + 1)); // + 1 because of the dot
			}
		}
		
		return list;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		
		logger.info("Source file loaded.");
		
		// first parse the message and find out loader and input adapter
		IInputAdapter inputAdapter = this.parse(exchange.getIn().getBody(String.class));
		if (inputAdapter == null)
			return;
		
		// then send messages into the queue
		ProducerTemplate template = exchange.getContext().createProducerTemplate();
		for (IEvent curEvent : inputAdapter.getEvents()) {
			template.sendBody("ems-jms:queue:main.queue", curEvent);
		}
	}
	
	public List<IEvent> split(String body) {
		
		logger.info("Source file loaded.");
		
		// first parse the message and find out loader and input adapter
		IInputAdapter inputAdapter = this.parse(body);
		if (inputAdapter == null)
			return null;
		
		return inputAdapter.getEvents();
	}
	
	/**
	 * @return the loaderPath
	 */
	public URL getLoaderPath() {
		return loaderPath;
	}

	/**
	 * @param loaderPath the loaderPath to set
	 */
	public void setLoaderPath(URL loaderPath) {
		this.loaderPath = loaderPath;
	}

	/**
	 * @return the inputAdapterPath
	 */
	public URL getInputAdapterPath() {
		return inputAdapterPath;
	}

	/**
	 * @param inputAdapterPath the inputAdapterPath to set
	 */
	public void setInputAdapterPath(URL inputAdapterPath) {
		this.inputAdapterPath = inputAdapterPath;
	}

	/**
	 * Checks if two collections (e.g. lists) contain equal objects (ignoring the order). 
	 * @param c1 first collection.
	 * @param c2 second collection.
	 * @return true if equal false otherwise.
	 */
	public static <T> boolean equalsIgnoringOrder(Collection<T> c1, Collection<T> c2) {
		
		if (c1.size() != c2.size())
			return false;
		
		if (c1.containsAll(c2))
			return true;
		
		return false;
	}
}
