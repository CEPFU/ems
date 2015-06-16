/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.algebra.operators.core.OperatorUtil;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Equal operator.
 * @author Ralf Oechsner
 *
 */
public class Equal extends Match {
	
	private boolean state = false;
	private String attribute;
	private Object a, b;
	private Operator op1, op2;
	
	/**
	 * Equal operator. Matches if attribute is equal to an event.
	 * @param attribute attribute that is checked
	 * @param b event that is compared
	 */
	public Equal(String attribute, IEvent b) {

		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	/**
	 * Equal operator. Matches if attribute is equal to an object.
	 * @param attribute attribute that is checked
	 * @param b object that is compared
	 */
	public Equal(String attribute, Object b) {

		this.attribute = attribute;
		this.b = b;
	}
	
	/**
	 * Equal operator. Matches if attribute of two matches are equal.
	 * @param attribute attribute that is checked
	 * @param a first match
	 * @param b second match
	 */
	public Equal(String attribute, Operator a, Operator b) {
		
		this.attribute = attribute;
		this.op1 = a;
		this.op2 = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		this.a = OperatorUtil.getOperands(this.attribute, op1);
		if (this.a != null) {
			this.b = OperatorUtil.getOperands(this.attribute, op2);
			return a.equals(b);
		}
		
		boolean comparison = NumericUtil.equalAttribute(attribute, event, b);
		
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
		
		return "equal(" + this.attribute + ", " + this.b + ")";
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.Operator#reset()
	 */
	public void reset() {
		
		this.state = false;
	}
}