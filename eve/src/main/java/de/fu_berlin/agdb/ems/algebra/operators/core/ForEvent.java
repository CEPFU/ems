/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.core;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * ForEvent operator.
 * @author Ralf Oechsner
 *
 */
public class ForEvent extends Operator {

	private Operator eventOp;
	private Operator op; 
	
	/**
	 * ForEvent operator. Evaluates the second operator only for the matches
	 * on the first operator.
	 * @param eventOp operator that contains the event(s)
	 * @param op operator that is evaluated on eventOp
	 */
	public ForEvent(Operator eventOp, Operator op) {
		
		this.eventOp = eventOp;
		this.op = op;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		// don't use the current event but that of eventOp
		IEvent[] matches = eventOp.getMatchingEvents();
		if (matches == null || matches.length == 0)
			throw new OperatorNotSupportedException("Referenced event Operator has no match.");
		
		return op.apply(matches[0]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return "forEvent(" + eventOp + ", " + op + ")";
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {
		
		// TODO: check
		eventOp.reset();
		op.reset();
	}
}
