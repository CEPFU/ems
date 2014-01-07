/**
 * 
 */
package de.fu_berlin.agdb.ems.producer;

import java.util.List;

import de.fu_berlin.agdb.ems.data.IEvent;
import de.fu_berlin.agdb.ems.importer.CSVImporter;


/**
 * @author Ralf Oechsner
 *
 */
public class CSVSplitter {
	
	public CSVSplitter() {
		
	}

	public List<IEvent> split(String body) {
		
		CSVImporter importer = new CSVImporter(body, "Time", "EEE MMM dd HH:mm:ss z YYYY", ";");
		
		return importer.getEvents();
	}
}
