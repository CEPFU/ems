/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class CoreBuilder {

	public static Operator attribute(final String key) {
		
		return new Operator() {
			
			private IEvent[] matchingEvents = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) {
				
				if (event.getAttributes().containsKey(key))
					this.matchingEvents[0] = event;
				
				return this.matchingEvents[0] != null;
			}
			
			@Override
	        public String toString(){
	            return "attribute(" + key + ")";
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
}
