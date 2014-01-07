package de.fu_berlin.agdb.ems.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Data structure for backend configuration.
 * @author Ralf Oechsner
 *
 */
public class Configuration {
	
	public static final String CONFIG_PATH = "config/main.properties";

	private Properties properties;
	private String interestsFolder = "interests";
	
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
	public void load() throws FileNotFoundException, IOException {
		
		 properties.load(new FileInputStream(CONFIG_PATH));
		 this.interestsFolder = properties.getProperty("interestsfolder");
	}
	
	/**
	 * Save configuration to disk.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save() throws FileNotFoundException, IOException {
		
		properties.setProperty("interestsfolder", interestsFolder);
		properties.store(new FileOutputStream("config/main.properties"), "Main Configuration");
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
			} catch (FileNotFoundException e1) {
				System.err.println("Configuration file not accessible!");
			} catch (IOException e1) {
				System.err.println("Configuration file cannot be created!");
			}
		} catch (IOException e) {
			System.err.println("Configuration file cannot be loaded!");
		}
	}

	public String getInterestsFolder() {
		return interestsFolder;
	}

	public void setInterestsFolder(String interestsFolder) {
		this.interestsFolder = interestsFolder;
	}
	
}
