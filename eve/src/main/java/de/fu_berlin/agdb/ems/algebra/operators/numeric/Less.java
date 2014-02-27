/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.numeric;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Less operator.
 * @author Ralf Oechsner
 *
 */
public class Less extends Operator {

	private String attribute;
	private Object b;
	
	/**
	 * Less operator.
	 * @param attribute attribute that is compared
	 * @param b event that it is compared to
	 */
	public Less(String attribute, IEvent b) {
		
		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	/**
	 * Less operator.
	 * @param attribute attribute that  is compared
	 * @param b object that it is compared to (primitives like e.g. int or double work as well)
	 */
	public Less(String attribute, Object b) {

		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		boolean comparison = NumericUtil.compare(this.attribute, event, this.b) < 0;
		
		if (comparison) {
			state = true;
			this.lastMatchingEvent[0] = event;
		}
		
		return state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "less(" + attribute + ", " + b + ")";
	}
}
