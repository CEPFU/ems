/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import java.util.Date;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.operators.temporal.After;
import de.fu_berlin.agdb.ems.algebra.operators.temporal.Before;
import de.fu_berlin.agdb.ems.algebra.operators.temporal.Concurrent;
import de.fu_berlin.agdb.ems.algebra.operators.temporal.Sequence;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Temporal operators.
 * @author Ralf Oechsner
 *
 */
public class TemporalOperatorBuilder {

	/**
	 * before: True if event happens before event b.
	 * @param b event b
	 * @return true if event happens before event b, false else
	 */
	public static Operator before(IEvent b) {
		
		return new Before(b);
	}
	
	/**
	 * before: True if event happens before event b.
	 * @param b time stamp
	 * @return true if event happens before time stamp b, false else
	 */
	public static Operator before(Date b) {
		
		return new Before(b);
	}
	
	/**
	 * after: True if event happens after event b.
	 * @param b event b
	 * @return true if event happens after event b, false else
	 */
	public static Operator after(IEvent b) {
		
		return new After(b);
	}
	
	/**
	 * after: True if event happens after event b.
	 * @param b time stamp
	 * @return true if event happens after time stamp b, false else
	 */
	public static Operator after(Date b) {
		
		return new After(b);
	}
	
	/**
	 * after: True if event happens after event b.
	 * @param b operator
	 * @return true if event happens after time stamp b, false else
	 */
	public static Operator after(Match b) {
		
		return new After(b);
	}
	
	public static Match after(Match m1, Match m2) {
		// TODO: match or operator?
		return new After(m1, m2);
	}
	
	/**
	 * concurrent: True if time stamp of event equals the one of event b.
	 * @param b event b
	 * @return true if time stamp of event equals the one of event b, false else
	 */
	public static Operator concurrent(IEvent b) {
		
		return new Concurrent(b);
	}
	
	/**
	 * concurrent: True if time stamp of event equals the one of event b.
	 * @param b time stamp
	 * @return true if time stamp of event equals the time stamp b, false else
	 */
	public static Operator concurrent(Date b) {
		
		return new Concurrent(b);
	}
	
	/**
	 * sequence: True if all given operators match in a row (no non-matching events
	 * in between).
	 * @param operators list of operators that have to match in a row
	 * @return true if all given operators match in a row, false else
	 */
	public static Sequence sequence(Operator ... operators) {
		
		return new Sequence(operators);
	}
}
