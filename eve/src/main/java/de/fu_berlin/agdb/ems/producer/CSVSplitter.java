/**
 * 
 */
package de.fu_berlin.agdb.ems.producer;

import java.util.List;

import de.fu_berlin.agdb.ems.data.IEvent;
import de.fu_berlin.agdb.ems.inputadapters.CSVInputAdapter;


/**
 * @author Ralf Oechsner
 *
 */
public class CSVSplitter {
	
	public CSVSplitter() {
		
	}

	public List<IEvent> split(String body) {
		
		CSVInputAdapter importer = new CSVInputAdapter("Time", "EEE MMM dd HH:mm:ss z YYYY", ";");
		importer.load(body);
		
		return importer.getEvents();
	}
}
