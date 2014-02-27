/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.numeric;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class Greater extends Operator {

	private String attribute;
	private Object b;
	
	public Greater(String attribute, IEvent b) {
		
		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	public Greater(String attribute, Object b) {

		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		this.b = b;
	}
	
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		boolean comparison = NumericUtil.compare(this.attribute, event, this.b) > 0;
		
		if (comparison) {
			state = true;
			this.lastMatchingEvent[0] = event;
		}
		
		return state;
	}

	@Override
	public String toString() {
		
		return "greater(" + attribute + ", " + b + ")";
	}
}