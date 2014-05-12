/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.temporal;

import java.util.Date;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Temporal concurrent operator.
 * @author Ralf Oechsner
 *
 */
public class Concurrent extends Match {

	private Date b;
	private Match match;
	private Match m1, m2;
	
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
	
	/**
	 * Temporal concurrent operator. 
	 * @param match concurrent to this match
	 */
	public Concurrent(Match match) {
		
		this.match = match;
		this.setChildren(match);
	}
	
	/**
	 * Temporal concurrent operator.
	 * @param m1 match 
	 * @param m2 match
	 */
	public Concurrent(Match m1, Match m2) {
		
		this.m1 = m1;
		this.m2 = m2;
		this.setChildren(m1, m2);
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		// operator compares two matches
		if (this.m1 != null && this.m2 != null) {

			m2.apply(event);
			IEvent e1 = this.m1.getMatchingEvent();
			IEvent e2 = this.m2.getMatchingEvent();

			if (e1 != null && e2 != null) {
				if (e1.getTimeStamp().after(e2.getTimeStamp())) {
					state = true;
					this.setMatchingEvent(e2);
				}
			}
		}
		// operator compares to the current event
		else {
			
			if (this.match != null && this.match.getMatchingEvent() != null) {
				this.b = this.match.getMatchingEvent().getTimeStamp();
			}

			// in case of no match (yet)
			if (this.b == null) {
				throw new OperatorNotSupportedException("Null event.");
			}

			
			boolean comparison = event.getTimeStamp().equals(b);

			if (comparison) {
				state = true;
				this.setMatchingEvent(event);
			}
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
