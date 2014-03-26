/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.temporal;

import java.util.Date;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Temporal after operator.
 * @author Ralf Oechsner
 *
 */
public class After extends Match {

	private Date b;
	private Operator op;
	
	public After(Operator op) {
		
		this.children = new Operator[1];
		this.children[0] = op;
	}
	
	/**
	 * Temporal after operator.
	 * @param b event whose time stamp is compared
	 */
	public After(IEvent b) {

		this.b = b.getTimeStamp();
	}
	
	/**
	 * Temporal after operator.
	 * @param b time stamp that is compared
	 */
	public After(Date b) {

		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		if (op != null && op.getMatchingEvent() != null) {
			this.b = op.getMatchingEvent().getTimeStamp();
		}
		
		boolean comparison = event.getTimeStamp().after(b);
		
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
		
		return "after(" + this.b + ")";
	}
}
