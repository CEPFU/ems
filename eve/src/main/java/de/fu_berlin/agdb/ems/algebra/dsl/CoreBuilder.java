/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.data.IAttribute;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class CoreBuilder {

	public static Operator attribute(final String key) {
		
		return new Operator() {

			IAttribute matchingAttribute = null;
			
			@Override
			public boolean apply(IEvent event) {
				
				this.matchingAttribute = event.getAttributes().get(key);
				
				return this.matchingAttribute != null;
			}
			
			@Override
	        public String toString(){
	            return "attribute(" + key + ")";
	        } 
		};
	}
}
