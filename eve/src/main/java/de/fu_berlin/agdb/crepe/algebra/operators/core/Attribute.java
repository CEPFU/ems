/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.core;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Attribute operator.
 * @author Ralf Oechsner
 *
 */
public class Attribute extends Match {

	private String attribute;
	
	/**
	 * Attribute operator (matches if attribute exists). 
	 * @param attribute attribute that is checked
	 */
	public Attribute(String attribute) {
		
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
		
		if (event.getAttributes().containsKey(attribute)) {
			this.setMatchingEvent(event);
			this.state = true;
		}
		
		return this.state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString(){
		
        return "attribute(" + this.attribute + ")";
    }
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {

		this.setMatchingEvent(null);
		this.state = false;
	}
}
