/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
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
	public static Operator before(final IEvent b) {
		
		return new Operator() {

			@Override
			public boolean apply(IEvent event)
					throws OperatorNotSupportedException {
				
				return event.getTimeStamp().before(b.getTimeStamp());
			}
			
			@Override
			public String toString() {
				
				return "before(" + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	/**
	 * after: True if event happens after event b.
	 * @param b event b
	 * @return true if event happens after event b, false else
	 */
	public static Operator after(final IEvent b) {
		
		return new Operator() {

			@Override
			public boolean apply(IEvent event)
					throws OperatorNotSupportedException {
				
				return event.getTimeStamp().after(b.getTimeStamp());
			}
			
			@Override
			public String toString() {
				
				return "after(" + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	/**
	 * concurrent: True if time stamp of event equals the one of event b.
	 * @param b event b
	 * @return true if time stamp of event equals the one of event b, false else
	 */
	public static Operator concurrent(final IEvent b) {
		
		return new Operator() {

			@Override
			public boolean apply(IEvent event)
					throws OperatorNotSupportedException {
				
				return event.getTimeStamp().equals(b.getTimeStamp());
			}
			
			@Override
			public String toString() {
				
				return "concurrent(" + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
