package de.fu_berlin.agdb.crepe.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Data structure for backend configuration.
 * @author Ralf Oechsner
 *
 */
public class Configuration {
	
	public static final String CONFIG_PATH = "config/main.properties";

	private static Logger logger = LogManager.getLogger();
	private Properties properties;
	private String sourcesFolder = "sources";
	private String profilesFolder = "profiles";
	private boolean useMessagingServer = false;
	private String loaderPath;
	private String inputAdapterPath;

	/**
	 * Main configuration.
	 */
	public Configuration() {
		
		this.properties = new Properties();
	}

	/**
	 * Load configuration from disk.
	 * @throws IOException
	 */
	public void load() throws IOException {
		
		properties.load(new FileInputStream(CONFIG_PATH));
		this.sourcesFolder = properties.getProperty("sourcesfolder");
		this.profilesFolder = properties.getProperty("profilesfolder");
		this.useMessagingServer = Boolean.parseBoolean(properties.getProperty("useMessagingServer"));
		this.setLoaderPath(properties.getProperty("loaderPath"));
		this.setInputAdapterPath(properties.getProperty("inputAdapterPath"));
		logger.info("Configuration file " + CONFIG_PATH + " loaded.");
	}
	
	/**
	 * Save configuration to disk.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save() throws FileNotFoundException {
		
		properties.setProperty("sourcesfolder", this.sourcesFolder);
		properties.setProperty("profilesfolder", this.profilesFolder);
		properties.setProperty("useMessagingServer", String.valueOf(this.useMessagingServer));
		if (loaderPath != null) // doesn't have to be in the config
			properties.setProperty("loaderPath", this.loaderPath);
		if (inputAdapterPath != null)
			properties.setProperty("inputAdapterPath", this.inputAdapterPath);
		
		try {
			if (!(new File("config")).exists()) {
				(new File("config")).mkdir();
			}
			
			properties.store(new FileOutputStream("config/main.properties"), "Main Configuration");
		} catch (IOException e) {
			logger.error("Could not write configuration file.", e);
		}
		
	}
	
	/**
	 * Loads configuration file if it exists and creates a default configuration file if it doesn't.
	 */
	public void loadAndCreate() {
		
		try {
			this.load(); // loads configuration with default values
		} catch (IOException e) {
			try {
				// save default configuration if no configuration file exists
				this.save();
				logger.info("Configuration file didn't exist. Default configuration was created.");
			} catch (FileNotFoundException e1) {
				logger.error("Configuration file not accessible!", e1);
			}
		}
	}

	/**
	 * Returns path of the folder for source files.
	 * @return path of source file folder
	 */
	public String getSourcesFolder() {
		return sourcesFolder;
	}

	/**
	 * Sets path of the folder for source files.
	 * @param sourcesFolder path of source file folder
	 */
	public void setSourcesFolder(String sourcesFolder) {
		this.sourcesFolder = sourcesFolder;
	}

	
	/**
	 * Returns path of profile files.
	 * @return path of profile folder
	 */
	public String getProfilesFolder() {
		return profilesFolder;
	}

	/**
	 * Sets path to profile folder.
	 * @param profilesFolder path to profile folder
	 */
	public void setProfilesFolder(String profilesFolder) {
		this.profilesFolder = profilesFolder;
	}
	
	/**
	 * @return the useMessagingServer
	 */
	public boolean getUseMessagingServer() {
		return useMessagingServer;
	}

	/**
	 * @param useMessagingServer the useMessagingServer to set
	 */
	public void setUseMessagingServer(boolean useMessagingServer) {
		this.useMessagingServer = useMessagingServer;
	}

	/**
	 * @return the loaderPath
	 */
	public URL getLoaderPath() {
		
		URL url = null;
		if (this.loaderPath != null) {
			try {
				url = (new File(this.loaderPath)).toURI().toURL();
			} catch (MalformedURLException e) {
				logger.error("Wrong loader path: " + this.loaderPath);
			}
		}
		
		return url;
	}

	/**
	 * @param loaderPath the loaderPath to set
	 */
	public void setLoaderPath(String loaderPath) {
		
		if (loaderPath != null && new File(loaderPath).exists()) {
			this.loaderPath = loaderPath;
		}
		else {
			// loaderPath stays null
			logger.warn("Loader path doesn't exist! Falling back to default directory.");
		}
	}

	/**
	 * @return the inputAdapterPath
	 */
	public URL getInputAdapterPath() {
		
		URL url = null;
		if (this.inputAdapterPath != null) {
			try {
				url = (new File(this.inputAdapterPath)).toURI().toURL();
			} catch (MalformedURLException e) {
				logger.error("Wrong input adapter path: " + this.inputAdapterPath);
			}
		}
		
		return url;
	}

	/**
	 * @param inputAdapterPath the inputAdapterPath to set
	 */
	public void setInputAdapterPath(String inputAdapterPath) {
		
		if (inputAdapterPath != null && new File(inputAdapterPath).exists()) {
			this.inputAdapterPath = inputAdapterPath;
		}
		else {
			logger.warn("Input adapter path doesn't exist! Falling back to default directory.");
		}
	}
}
