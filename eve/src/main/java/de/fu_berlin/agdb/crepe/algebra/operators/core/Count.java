/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.core;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Count operator.
 * @author Ralf Oechsner
 * 
 */
public class Count extends Match {

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
		this.setChildren(op);
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		if (op.apply(event)) {
			this.count++;
			
			if (this.count >= this.n) {
				this.state = true;
				this.setMatchingEvent(event);
			}
		}
		
		return this.state;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {
		
		this.count = 0;
		this.state = false;
		this.op.reset();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "count(" + this.op + ", " + this.n + ")";
	}
}
