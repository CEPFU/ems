/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Less operator.
 * @author Ralf Oechsner
 *
 */
public class Less extends Match {

	private String attribute;
	private Object b;
	private Operator op;
	
	/**
	 * Less operator.
	 * @param attribute attribute that is compared
	 * @param b event that it is compared to
	 */
	public Less(String attribute, IEvent b) {

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

		this.attribute = attribute;
		this.b = b;
	}
	
	/**
	 * Less operator.
	 * @param attribute attribute that  is compared
	 * @param b operator that it is compared to
	 */
	public Less(String attribute, Operator b) {
		
		this.attribute = attribute;
		this.op = b;
		this.setChildren(op);
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
		
		boolean comparison = NumericUtil.compare(this.attribute, event, this.b) < 0;
		
		if (comparison) {
			state = true;
			this.setMatchingEvent(event);
		}
		
		return state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "less(" + this.attribute + ", " + this.b + ")";
	}
}
