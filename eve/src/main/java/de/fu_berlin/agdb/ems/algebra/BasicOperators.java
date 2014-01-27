package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Basic operators of algebra.
 * @author Ralf Oechsner
 *
 */
public class BasicOperators {
	
	/**
	 * Checks if the value of the given attribute of two events is equal.
	 * @param attribute attribute that is checked for equality
	 * @param event1 first event
	 * @param event2 second event
	 * @return true if attributes are equal
	 */
	public static boolean equals(String attribute, IEvent event1, IEvent event2) {
		
		return (event1.getAttributes().get(attribute).equals(event2.getAttributes().get(attribute)));
	}
	
	public static boolean less(String attribute, IEvent event1, IEvent event2) throws OperatorNotSupportedException {
		
		// ok this one is a bit tricky: the idea is that not every operation is supported by every attribute
		// type and therefore the operator can't know if it works. For example the less than operator needs the
		// attributes to implement the (typed) comparable interface but it can't know it at runtime (type erasure in java).
		// So the operator just the cast to comparable and if it doesn't work the rule isn't executed.
		try {
			@SuppressWarnings("unchecked")
			Comparable<Object> c1 = (Comparable<Object>) event1.getAttributes().get(attribute).getValue();
			@SuppressWarnings("unchecked")
			Comparable<Object> c2 = (Comparable<Object>) event2.getAttributes().get(attribute).getValue();

			return c1.compareTo(c2) < 0;
		} catch (ClassCastException e) {
			throw new OperatorNotSupportedException("Less operator not supported between attribute types "
					+ event1.getAttributes().get(attribute).getValue().getClass() + " and "
					+ event2.getAttributes().get(attribute).getValue().getClass());
		}
	}

	public static boolean greater(String attribute, IEvent event1, IEvent event2) throws OperatorNotSupportedException {
		
		// ok this one is a bit tricky: the idea is that not every operation is supported by every attribute
		// type and therefore the operator can't know if it works. For example the less than operator needs the
		// attributes to implement the (typed) comparable interface but it can't know it at runtime (type erasure in java).
		// So the operator just the cast to comparable and if it doesn't work the rule isn't executed.
		try {
			@SuppressWarnings("unchecked")
			Comparable<Object> c1 = (Comparable<Object>) event1.getAttributes().get(attribute).getValue();
			@SuppressWarnings("unchecked")
			Comparable<Object> c2 = (Comparable<Object>) event2.getAttributes().get(attribute).getValue();

			return c1.compareTo(c2) > 0;
		} catch (ClassCastException e) {
			throw new OperatorNotSupportedException("Greater operator not supported between attribute types "
					+ event1.getAttributes().get(attribute).getValue().getClass() + " and "
					+ event2.getAttributes().get(attribute).getValue().getClass());
		}
	}
	
	public static boolean lessEqual(String attribute, IEvent event1, IEvent event2) throws OperatorNotSupportedException {
		
		return less(attribute, event1, event2) || equals(attribute, event1, event2);
	}
	
	public static boolean greaterEqual(String attribute, IEvent event1, IEvent event2) throws OperatorNotSupportedException {
		
		return greater(attribute, event1, event2) || equals(attribute, event1, event2);
	}
	
	public static boolean before(IEvent event1, IEvent event2) {
		
		return event1.getTimeStamp().before(event2.getTimeStamp());
	}
	
	public static boolean after(IEvent event1, IEvent event2) {
		
		return event1.getTimeStamp().after(event2.getTimeStamp());
	}
	
	public static boolean and(boolean expression1, boolean expression2) {
		
		return expression1 && expression2;
	}
	
	public static boolean or(boolean expression1, boolean expression2) {
		
		return expression1 || expression2;
	}
	
	public static boolean xor(boolean expression1, boolean expression2) {
		
		return (expression1 || expression2) && !(expression1 && expression2);
	}
	
	public static boolean not(boolean expression) {
		return !expression;
	}
}
