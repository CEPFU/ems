package de.fu_berlin.agdb.eve.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URLLoader implements ILoader {

	private String text;
	private URL url;
	
	/**
	 * Loader for HTTP.
	 * @param url URL of file to load.
	 */
	public URLLoader(URL url) {
		
		this.url = url;
		this.text = "";  // start with an empty string
	}

	/**
	 * Loads file from URL via HTTP.
	 */
	public boolean load() {

        BufferedReader in;
		try {
			in = new BufferedReader(
			new InputStreamReader(url.openStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            text += inputLine + "\n";
	        in.close();
	        
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns loaded file as text (for example content of HTML or XML file).
	 */
	public String getText() {
		
		return this.text;
	}
}
