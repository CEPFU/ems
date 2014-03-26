/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.core;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Count operator.
 * @author Ralf Oechsner
 * 
 */
public class Count extends Operator {

	private long count;
	private long n;
	private Operator op;
	
	/**
	 * Count operator. Matches when op matches n times.
	 * There can be non matching events in between.
	 * @param op operator to match]
	 * @param n times the operator has to match
	 */
	public Count(Operator op, long n) {
		
		this.n = n;
		this.count = 0;
		this.op = op;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		if (op.apply(event)) {
			count++;
		}
		
		return this.count >= this.n;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {
		
		this.count = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "count(" + this.op + ", " + this.n + ")";
	}
}
