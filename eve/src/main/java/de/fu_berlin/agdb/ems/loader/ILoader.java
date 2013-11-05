package de.fu_berlin.agdb.ems.loader;

public interface ILoader {

	/**
	 * Load file.
	 * @return true if successful, false otherwise.
	 */
	public boolean load();
	
	/**
	 * Content of loaded file as a string.
	 * @return content of file.
	 */
	public String getText();
}
