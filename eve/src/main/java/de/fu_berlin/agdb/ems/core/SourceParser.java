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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import de.fu_berlin.agdb.ems.importer.IImporter;
import de.fu_berlin.agdb.ems.loader.ILoader;

/**
 * Loads sources from XML text/files.
 * @author Ralf Oechsner
 *
 */
public class SourceParser implements Processor {

	public static final String LOADER_PACKAGE = "de.fu_berlin.agdb.ems.loader";
	public static final String IMPORTER_PACKAGE = "de.fu_berlin.agdb.ems.importer";
	public static final String LOADER_PREFIX = "loader";
	public static final String IMPORTER_PREFIX = "importer";
	
	// Stores the declarations of the constructors of all loader and importer classes
	// First map stores a list of constructor declarations for the class name (in the String).
	// The map inside the List stores pairs of tags (defined in annotations) and the  
	private Map<String, List<Map<String, Class<?>>>> loaderParameters;
	private Map<String, List<Map<String, Class<?>>>> importerParameters;
	
	// temporary hack TODO: do better
	private ILoader parsedLoader;
	
	/**
	 * Loads the source parser.
	 */
	public SourceParser() {

		this.generateParameters();
	}
	
	/**
	 * Generate parameter lists of all constructors of all loaders and importers.
	 */
	private void generateParameters() {

		// first loaders
		this.loaderParameters = new Hashtable<String, List<Map<String,Class<?>>>>();
		List<Class<?>> loaders = this.findClasses(ILoader.class, LOADER_PACKAGE);
		
		for (Class<?> curLoader : loaders) {
			this.loaderParameters.put(curLoader.getSimpleName(), this.classParameters(curLoader));
		}
		
		// then importers
		this.importerParameters = new Hashtable<String, List<Map<String,Class<?>>>>();
		List<Class<?>> importers = this.findClasses(IImporter.class, IMPORTER_PACKAGE);
		
		for (Class<?> curLoader : importers) {
			this.importerParameters.put(curLoader.getSimpleName(), this.classParameters(curLoader));
		}
	}
	
	/**
	 * Find all classes in a given package that implement a given interface.
	 * @param interfaceImplemented interface classes have implement.
	 * @param packageName package name of classes.
	 * @return list of classes in the package that implement the interface.
	 */
	private List<Class<?>> findClasses(Class<?> interfaceImplemented, String packageName) {
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

		// filter .class files.
		File[] files;
		try {
			files = new File(URLDecoder.decode(root.getFile(), "UTF-8")).listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith(".class");
			    }
			});
			
			// find classes that are implementing the interface and add them to the class list
			for (File file : files) {
			    String className = file.getName().replaceAll(".class$", "");
			    Class<?> cls;
				try {
					cls = Class.forName(packageName + "." + className);
					if (!cls.isInterface()) {
						if (interfaceImplemented.isAssignableFrom(cls)) {
							classes.add((Class<?>) cls);
						}
					}
				} catch (ClassNotFoundException e) {
					// shouldn't happen because only classes that are found before are searched for again
				}
			}
		} catch (UnsupportedEncodingException e1) {
			// also shouldn't happen because of file system encoding
			e1.printStackTrace();
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
	private List<Map<String, Class<?>>> classParameters(Class<?> c) {
		
		List<Map<String, Class<?>>> parametersList = new ArrayList<Map<String, Class<?>>>();
		
		Constructor<?>[] constructors = c.getConstructors();
		for (Constructor<?> curConstr : constructors) {
			
			// new parameter map for every constructor
			Map<String, Class<?>> parameters = new Hashtable<String, Class<?>>();
			parametersList.add(parameters);

			Class<?>[] paramTypes = curConstr.getParameterTypes();
			Annotation[][] tags = curConstr.getParameterAnnotations();

			int i = 0;
			for(Annotation[] annotations : tags){
				Class<?> parameterType = paramTypes[i++];

				for(Annotation annotation : annotations) {

					if (annotation instanceof Tag) {
						Tag tagAnn = (Tag) annotation;
						parameters.put(tagAnn.value(), parameterType);
					}
				}
			}
		}
		
		return parametersList;
	}
	
	/**
	 * Parses source definition.
	 * @param source source definition.
	 */
	public void parse(String source) {
		
		// all interests are "properties files"
		Properties prop = new Properties();
		try {
			prop.load(new ByteArrayInputStream(source.getBytes("UTF-8")));

			// first the loader
			String loader = prop.getProperty("loader");
			List<Map<String, Class<?>>> loaderParameters = this.loaderParameters.get(loader);
			// TODO: check for NULL
			
			for (Map<String, Class<?>> curParameters : loaderParameters) {
				
				// check if all parameters of the properties file match the parameters of the loader
				if (curParameters.keySet().equals(this.getPropertiesWithPrefix(LOADER_PREFIX, prop))) {
					System.out.println("found match: " + curParameters.keySet());
					// now load class
				    try {
						Class<?> loaderClass = Class.forName(LOADER_PACKAGE + "." + loader);
						// parameter must be always Strings
						Class<?>[] paramTypes = new Class[curParameters.keySet().size()];
						for (int i = 0; i < paramTypes.length; i++)
							paramTypes[i] = String.class;
						Constructor<?> constructor = loaderClass.getConstructor(paramTypes);
						
						// build array with values in right order
						Object[] paramValues = new Object[curParameters.entrySet().size()];
						int i = 0;
						for (String curEntry : curParameters.keySet()) {							
							paramValues[i++] = prop.getProperty(LOADER_PREFIX + "." + curEntry);
						}
						
						ILoader loaderObject = (ILoader) constructor.newInstance(paramValues);
						this.parsedLoader = loaderObject;
						loaderObject.load();
						
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			// now the importer
			String importer = prop.getProperty("importer");
			List<Map<String, Class<?>>> importerParameters = this.importerParameters.get(importer);
			
			for (Map<String, Class<?>> curParameters : importerParameters) {
				
				if (curParameters.keySet().equals(this.getPropertiesWithPrefix(IMPORTER_PREFIX, prop))) {
					System.out.println("found match: " + curParameters.keySet());
					// now load class
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			// shouldn't happen because all internal encoding is done in UTF-8
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds properties that start with a certain prefix separated by a "." and returns them in
	 * a new set without the prefixes.
	 * @param prefix prefix of property.
	 * @param properties properties object.
	 * @return subset of properties starting without the prefix.
	 */
	private Set<String> getPropertiesWithPrefix(String prefix, Properties properties) {
		
		Set<String> set = new HashSet<String>();
		
		for (String curString : properties.stringPropertyNames()) {
			if (curString.startsWith(prefix + ".")) {
				set.add(curString.substring(prefix.length() + 1)); // + 1 because of the dot
			}
		}
		
		return set;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		
		// first parse the message and find out loader and importer
		this.parse(exchange.getIn().getBody(String.class));
		
		// then send messages into the queue
		ProducerTemplate template = exchange.getContext().createProducerTemplate();
		template.sendBody("ems-jms:queue:main.queue", parsedLoader.getText());
	}
}
