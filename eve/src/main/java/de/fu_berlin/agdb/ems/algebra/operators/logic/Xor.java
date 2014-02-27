/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.logic;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Boolean exclusive or operator.
 * @author Ralf Oechsner
 *
 */
public class Xor extends Operator {

	private Operator op1;
	private Operator op2;
	
	/**
	 * Boolean exclusive or operator.
	 * @param op1 first operator
	 * @param op2 second operator
	 */
	public Xor(Operator op1, Operator op2) {
		
		this.op1 = op1;
		this.op2 = op2;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		// xor: ^ same as (op1.apply(event) || op2.apply(event)) && !(op1.apply(event) && op2.apply(event));
		return this.op1.apply(event) ^ this.op2.apply(event);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString(){
		
        return "xor(" + this.op1 + ", " + this.op2 + ")";
    }

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {

		this.op1.reset();
		this.op2.reset();
	} 
}
