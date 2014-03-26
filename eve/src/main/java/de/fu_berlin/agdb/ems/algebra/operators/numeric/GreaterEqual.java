/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.numeric;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * GreaterEqual operator.
 * @author Ralf Oechsner
 *
 */
public class GreaterEqual extends Match {
	
	private boolean state = false;
	private String attribute;
	private Object b;
	
	public GreaterEqual(String attribute, IEvent b) {

		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	public GreaterEqual(String attribute, Object b) {

		this.attribute = attribute;
		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		// is not the same as just compare(attribute, event, b) >= 0 because an object
		// can be equal to another without being a comparable (which compare() requires).
		boolean comparison = NumericUtil.compare(this.attribute, event, this.b) > 0 
				|| NumericUtil.equalAttribute(this.attribute, event, this.b);
		
		if (comparison) {
			this.state = true;
			this.setMatchingEvent(event);
		}
			
		return state;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "greaterEqual(" + this.attribute + ", " + this.b + ")";
	}
}