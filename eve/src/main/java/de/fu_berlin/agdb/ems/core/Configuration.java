package de.fu_berlin.agdb.ems.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	/**
	 * Main configuration.
	 */
	public Configuration() {
		
		this.properties = new Properties();
	}

	/**
	 * Load configuration from disk.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void load() throws FileNotFoundException {
		
		 try {
			properties.load(new FileInputStream(CONFIG_PATH));
			 this.sourcesFolder = properties.getProperty("sourcesfolder");
			 logger.info("Configuration file " + CONFIG_PATH + " loaded.");
		} catch (IOException e) {
			logger.error("Configuration file cannot be loaded!", e);
		}
	}
	
	/**
	 * Save configuration to disk.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save() throws FileNotFoundException {
		
		properties.setProperty("sourcesfolder", sourcesFolder);
		
		try {
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
		} catch (FileNotFoundException e) {
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
}
