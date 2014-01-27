/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class History {

	private List<IEvent> events;
	
	private static Logger logger = LogManager.getLogger();
	
	public History() {
		
		this.events = new LinkedList<IEvent>();
		logger.info("Event history initialised.");
	}
	
	public void add(IEvent event) {
		
		this.events.add(event);
	}
	
	public void findWithAttribute(String attribute) {
		
		Iterator<IEvent> iter = this.events.iterator();
		while (iter.hasNext()) {
			IEvent event = iter.next();
			if (event.getAttributes().get(attribute) != null) {
				
			}
		}
	}
}
