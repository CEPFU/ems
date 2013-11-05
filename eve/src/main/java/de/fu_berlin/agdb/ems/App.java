package de.fu_berlin.agdb.ems;

import java.net.MalformedURLException;
import java.net.URL;

import de.fu_berlin.agdb.ems.loader.URLLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("HTTP-Test:");
        
        try {
//			HTTPLoader httpLoader = new HTTPLoader(new URL("http://heise.de.feedsportal.com/c/35207/f/653902/index.rss"));
        	URLLoader urlLoader = new URLLoader(new URL("file:///home/ralf/Documents/Uni/Master/Masterarbeit/Thema.txt"));
			urlLoader.load();
			System.out.println(urlLoader.getText());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
