/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.temporal;

import java.util.Date;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Temporal before operator.
 * @author Ralf Oechsner
 *
 */
public class Before extends Match {

	private Date b;
	
	/**
	 * Temporal before operator.
	 * @param b event whose time stamp is compared
	 */
	public Before(IEvent b) {

		this.b = b.getTimeStamp();
	}
	
	/**
	 * Temporal before operator.
	 * @param b time stamp that is compared
	 */
	public Before(Date b) {

		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		boolean comparison = event.getTimeStamp().before(b);
		
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
		
		return "before(" + this.b + ")";
	}
}