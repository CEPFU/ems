/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.data.IEvent;


/**
 * Class for matches of events (actually operators that matched on events).
 * Functions as a terminal symbol in the DSL.
 * @author Ralf Oechsner
 *
 */
public abstract class Match extends Operator {
	
	/**
	 * Setter for matching events. 
	 * @param events
	 */
	public void setLastMatchingEvents(IEvent[] events) {
		
		this.lastMatchingEvent = events;
		this.getWindow().onMatch(events);
	}
}
