/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.core;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Attribute operator.
 * @author Ralf Oechsner
 *
 */
public class Attribute extends Operator {

	private String attribute;
	
	/**
	 * Attribute operator (matches if attribute exists). 
	 * @param attribute attribute that is checked
	 */
	public Attribute(String attribute) {
		
		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		if (event == null || event.getAttributes() == null) {
			throw new OperatorNotSupportedException("Null event.");
		}
		
		if (event.getAttributes().containsKey(attribute))
			this.lastMatchingEvent[0] = event;
		
		return this.lastMatchingEvent[0] != null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString(){
		
        return "attribute(" + attribute + ")";
    }
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {

		this.lastMatchingEvent[0] = null;
	}
}
