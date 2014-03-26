/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.temporal;

import java.util.Date;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Temporal concurrent operator.
 * @author Ralf Oechsner
 *
 */
public class Concurrent extends Match {

	private Date b;
	
	/**
	 * Temporal concurrent operator.
	 * @param b event whose time stamp is compared
	 */
	public Concurrent(IEvent b) {

		this.b = b.getTimeStamp();
	}
	
	/**
	 * Temporal concurrent operator.
	 * @param b time stamp that is compared
	 */
	public Concurrent(Date b) {

		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		boolean comparison = event.getTimeStamp().equals(b);
		
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
		
		return "concurrent(" + this.b + ")";
	}
}
