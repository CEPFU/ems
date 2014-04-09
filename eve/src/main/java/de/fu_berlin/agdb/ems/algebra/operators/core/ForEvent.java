/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.core;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * ForEvent operator.
 * @author Ralf Oechsner
 *
 */
public class ForEvent extends Operator {

	private Match matchOp;
	private Operator op; 
	
	/**
	 * ForEvent operator. Evaluates the second operator only for the matches
	 * on the first operator.
	 * @param match operator that contains the event(s)
	 * @param op operator that is evaluated on eventOp
	 */
	public ForEvent(Match match, Operator op) {
		
		this.matchOp = match;
		this.op = op;
		this.children = new Operator[2];
		this.children[0] = match;
		this.children[1] = op;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		// don't use the current event but that of eventOp
		IEvent match = matchOp.getMatchingEvent();
		if (match == null) {
			throw new OperatorNotSupportedException("Referenced event Operator has no match.");
		}
			
		return op.apply(match);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return "forEvent(" + this.matchOp + ", " + this.op + ")";
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {
		
		// TODO: check
		this.matchOp.reset();
		this.op.reset();
	}
}
