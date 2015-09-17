package de.fu_berlin.agdb.crepe.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
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

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter;
import de.fu_berlin.agdb.crepe.loader.ILoader;

/**
 * Loads sources from XML text/files.
 * @author Ralf Oechsner
 *
 */
public class SourceParser implements Processor {

	public static final String LOADER_PACKAGE = "de.fu_berlin.agdb.crepe.loader";
	public static final String INPUT_ADAPTER_PACKAGE = "de.fu_berlin.agdb.crepe.inputadapters";
	public static final String LOADER_PREFIX = "loader";
	public static final String INPUT_ADAPTER_PREFIX = "inputAdapter";
	
	private static Logger logger = LogManager.getLogger();
	SourceHandler loaderHandler;
	
	private URL loaderPath;
	private URL inputAdapterPath;
	
	private Map<String, List<List<String>>> loaderParameters;
	private Map<String, List<List<String>>> inputAdapterParameters;
	private boolean benchmarking;
	
	/**
	 * Loads the source parser.
	 */
	public SourceParser() {
		loaderHandler = createAndStartLoaderHandler();
		
		loaderPath = Thread.currentThread().getContextClassLoader().getResource(LOADER_PACKAGE.replace(".", "/"));
		inputAdapterPath = Thread.currentThread().getContextClassLoader().getResource(INPUT_ADAPTER_PACKAGE.replace(".", "/"));
		generateParameters();
	}
	
	/**
	 * Loads the source loader with non default paths.
	 * @param loaderPath loader path
	 * @param inputAdapterPath input adapter path
	 */
	public SourceParser(URL loaderPath, URL inputAdapterPath) {
		loaderHandler = createAndStartLoaderHandler();
		
		this.loaderPath = loaderPath;
		this.inputAdapterPath = inputAdapterPath;
		this.generateParameters();
	}
	
	private SourceHandler createAndStartLoaderHandler() {
		loaderHandler = new SourceHandler(Algebra.EVENT_QUEUE_URI);
		Thread loaderHandlerThread = new Thread(loaderHandler);
		loaderHandlerThread.start();
		return loaderHandler;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("Source file loaded.");
		if (this.benchmarking) {
			// no logger is used because logging is turned off for benchmarking
			System.out.println("Benchmark start: " + System.currentTimeMillis());
		}

		String body = exchange.getIn().getBody(String.class);
		
		Properties properties = new Properties();
		properties.load(new ByteArrayInputStream(body.getBytes("UTF-8")));
	
		ILoader loader = parseLoader(properties);
		if (loader == null){
			logger.error("No matching loader declaration found! Not loading source.");
			return;
		}
		
		IInputAdapter inputAdapter = parseInputAdapter(properties);
		if (inputAdapter == null){
			logger.error("No matching input adapter declaration found! Not loading source.");
			return;
		}
		
		loaderHandler.addLoaderAndInputAdapter(new Triple<ILoader, IInputAdapter, ProducerTemplate>(loader, inputAdapter, exchange.getContext().createProducerTemplate()));
	}

	private ILoader parseLoader(Properties properties) {
		String loader = properties.getProperty("loader");
		logger.info("Loader specified in source file: " + loader);
		List<List<String>> loaderParameters = this.loaderParameters.get(loader);
		if (loaderParameters == null) {
			logger.error("Loader specified in source file could not be found! Not loading source.");
			return null;
		}
			
		ILoader loaderObject = null;
		
		for (List<String> curParameters : loaderParameters) {
			
			// check if all parameters of the properties file match the parameters of the loader
			if (curParameters.equals(this.getPropertiesWithPrefix(LOADER_PREFIX, properties))) {
				
				// now load class
			    loaderObject = (ILoader) this.loadClass(LOADER_PACKAGE + "." + loader, curParameters, properties, LOADER_PREFIX);
			    break; // only first possible match is used
			}
		}
		return loaderObject;
	}
	
	private IInputAdapter parseInputAdapter(Properties properties) {
		IInputAdapter inputAdapterObject = null;
		String inputAdapter = properties.getProperty(INPUT_ADAPTER_PREFIX);
		List<List<String>> inputAdapterParameters = this.inputAdapterParameters.get(inputAdapter);
		if (inputAdapterParameters == null) {
			logger.error("Input adapter specified in source file could not be found! Not loading source.");
			return null;
		}
		
		for (List<String> curParameters : inputAdapterParameters) {
			
			if (equalsIgnoringOrder(curParameters, this.getPropertiesWithPrefix(INPUT_ADAPTER_PREFIX, properties))) {
		
				inputAdapterObject = (IInputAdapter) this.loadClass(INPUT_ADAPTER_PACKAGE + "." + inputAdapter, curParameters, properties, INPUT_ADAPTER_PREFIX);
				break; // only first possible input adapter (shouldn't be more than one anyway)
			}
		}
		return inputAdapterObject;
	}
	
	/**
	 * Generate parameter lists of all constructors of all loaders and inputAdapters.
	 */
	private void generateParameters() {

		// first look for loaders in the loader package
		this.loaderParameters = new Hashtable<String, List<List<String>>>();
		List<Class<?>> loaders = findClasses(ILoader.class, loaderPath, LOADER_PACKAGE);
		for (Class<?> curLoader : loaders) {
			this.loaderParameters.put(curLoader.getSimpleName(), classParameters(curLoader));
		}
		logger.info("Found the following loader plugins: " + loaderParameters.keySet());
		
		// then for input adapters in the input adapter package
		this.inputAdapterParameters = new Hashtable<String, List<List<String>>>();
		List<Class<?>> inputAdapters = findClasses(IInputAdapter.class, inputAdapterPath, INPUT_ADAPTER_PACKAGE);
		for (Class<?> curInputAdapter : inputAdapters) {
			this.inputAdapterParameters.put(curInputAdapter.getSimpleName(), classParameters(curInputAdapter));
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
			
			if (files == null){
				return classes;
			}
			
			// find classes that are implementing the interface and add them to the class list
			for (File file : files) {
			    String className = file.getName().replaceAll(".class$", "");
			    Class<?> cls;
				try {
					cls = Class.forName(packageName + "." + className, true, classLoader);
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
	
	/**
	 * Checks if two collections (e.g. lists) contain equal objects (ignoring the order). 
	 * @param c1 first collection.
	 * @param c2 second collection.
	 * @return true if equal false otherwise.
	 */
	private <T> boolean equalsIgnoringOrder(Collection<T> c1, Collection<T> c2) {
		
		if (c1.size() != c2.size())
			return false;
		
		if (c1.containsAll(c2))
			return true;
		
		return false;
	}

	public void setBenchmarking(boolean benchmarking) {
		this.benchmarking = benchmarking;
	}

}
