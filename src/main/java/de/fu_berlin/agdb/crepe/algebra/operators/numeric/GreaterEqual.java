/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * GreaterEqual operator.
 * @author Ralf Oechsner
 *
 */
public class GreaterEqual extends Match {
	
	private boolean state = false;
	private String attribute;
	private Object b;
	private Operator op;
	
	/**
	 * GreaterEqual operator.
	 * @param attribute attribute that is compared
	 * @param b event that it is compared to
	 */
	public GreaterEqual(String attribute, IEvent b) {

		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	/**
	 * LessEqual operator.
	 * @param attribute attribute that  is compared
	 * @param b operator that it is compared to
	 */
	public GreaterEqual(String attribute, Operator b) {
		
		this.attribute = attribute;
		this.op = b;
		this.setChildren(op);
	}
	
	
	/**
	 * GreaterEqual operator.
	 * @param attribute attribute that  is compared
	 * @param b object that it is compared to (primitives like e.g. int or double work as well)
	 */
	public GreaterEqual(String attribute, Object b) {

		this.attribute = attribute;
		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		if (this.op != null) {

			if (this.op.getMatchingEvent() == null)
				throw new OperatorNotSupportedException("Null event.");
			if (!this.op.getMatchingEvent().getAttributes().containsKey(this.attribute)) {
				throw new OperatorNotSupportedException("Attribute not found.");
			}
			this.b = this.op.getMatchingEvent().getAttributes().get(this.attribute).getValue();
		}
		
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