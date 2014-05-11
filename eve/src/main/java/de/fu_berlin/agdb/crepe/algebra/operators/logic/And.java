package de.fu_berlin.agdb.crepe.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Boolean and operator.
 * @author Ralf Oechsner
 *
 */
public class And extends Operator {

	private Operator op1;
	private Operator op2;
	
	/**
	 * Boolean and operator.
	 * @param op1 first operator
	 * @param op2 second operator
	 */
	public And(Operator op1, Operator op2) {
		
		this.op1 = op1;
		this.op2 = op2;
		this.children = new Operator[2];
		this.children[0] = op1;
		this.children[1] = op2;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		// The order in which the operators have to match is arbitrary.
		// Therefore both operators have to be executed regardless of thrown
		// exceptions.
		boolean left, right;
		try {
			left = this.op1.apply(event);
		}
		finally {
			right = this.op2.apply(event);
		};
		
		return left && right;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString(){
		
        return "and(" + this.op1 + ", " + this.op2 + ")";
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
