/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.Event;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class CoreBuilder {

	/**
	 * True if an event has a given attribute.
	 * @param attribute attribute that is checked.
	 * @return true if event has attribute with that key, false else
	 */
	public static Operator attribute(final String attribute) {
		
		return new Operator() {
			
			private IEvent[] matchingEvents = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) {
				
				if (event.getAttributes().containsKey(attribute))
					this.matchingEvents[0] = event;
				
				return this.matchingEvents[0] != null;
			}
			
			@Override
	        public String toString(){
	            return "attribute(" + attribute + ")";
	        }

			@Override
			public IEvent[] getMatchingEvents() {

				return this.matchingEvents;
			}

			@Override
			public void reset() {

				this.matchingEvents[0] = null;
			}
		};
	}
	
	/**
	 * Creates an event with one attribute.
	 * @param attribute key of attribute
	 * @param attributeValue attribute value.
	 * @return event
	 */
	public static IEvent event(String attribute, Object attributeValue) {
		
		Event event = new Event(attribute, attributeValue);
		
		return event;
	}
}
