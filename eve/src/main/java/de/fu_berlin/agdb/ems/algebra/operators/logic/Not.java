/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.logic;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Boolean not operator.
 * @author Ralf Oechsner
 *
 */
public class Not extends Operator {

	private Operator op;
	
	/**
	 * Boolean not operator.
	 * @param op operator that is negated
	 */
	public Not(Operator op) {
		
		this.op = op;
		this.children = new Operator[1];
		this.children[0] = op;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		return !this.op.apply(event);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString(){
    
		return "not(" + this.op + ")";
    }

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {

		this.op.reset();
	} 
}
