/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Greater operator.
 * @author Ralf Oechsner
 *
 */
public class Greater extends Match {

	private String attribute;
	private Object b;
	private Operator op;
	
	public Greater(String attribute, IEvent b) {

		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	public Greater(String attribute, Object b) {

		this.attribute = attribute;
		this.b = b;
	}
	
	public Greater(String attribute, Operator b) {
		
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
//			this.op.apply(event);
			if (this.op.getMatchingEvent() == null)
				throw new OperatorNotSupportedException("Null event.");
			if (!this.op.getMatchingEvent().getAttributes().containsKey(this.attribute)) {
				throw new OperatorNotSupportedException("Attribute not found.");
			}
			this.b = this.op.getMatchingEvent().getAttributes().get(this.attribute).getValue();
		}
		
		boolean comparison = NumericUtil.compare(this.attribute, event, this.b) > 0;
		
		if (comparison) {
			state = true;
			this.setMatchingEvent(event);
		}
		
		return state;
	}

	@Override
	public String toString() {
		
		return "greater(" + attribute + ", " + b + ")";
	}
}