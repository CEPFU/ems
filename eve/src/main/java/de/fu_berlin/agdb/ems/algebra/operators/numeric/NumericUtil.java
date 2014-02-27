/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.numeric;

import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class NumericUtil {

	/**
	 * Compares two attributes to each other provided that they implement the comparable interface. If not an Exception is thrown.
	 * @param attribute attribute that is compared
	 * @param a first event
	 * @param b object for comparison
	 * @return < 0 if a less b, = 0 if a == b, > 0 if a greater b.
	 * @throws OperatorNotSupportedException indicates that the attributes aren't comparable
	 */
	public static int compare(String attribute, IEvent a, Object b) throws OperatorNotSupportedException {
		
		if (a == null) {
			throw new OperatorNotSupportedException("Null event.");
		}
		
		if (a.getAttributes().get(attribute) == null) {
			throw new OperatorNotSupportedException("Attribute not found.");
		}
		
		// ok this one is a bit tricky: the idea is that not every operation is supported by every attribute
		// type and therefore the operator can't know if it works. For example the less than operator needs the
		// attributes to implement the (typed) comparable interface but it can't know it at runtime (type erasure in java).
		// So the operator just the cast to comparable and if it doesn't work the rule isn't executed.
		try {
			@SuppressWarnings("unchecked")
			Comparable<Object> c1 = (Comparable<Object>) a.getAttributes().get(attribute).getValue();

			return c1.compareTo(b);
		} catch (ClassCastException e) {
			throw new OperatorNotSupportedException("Numeric comparison operator not supported between attribute types "
					+ a.getAttributes().get(attribute).getValue().getClass() + " and "
					+ b.getClass());
		}
	}
	
	/**
	 * Checks if two attributes are equal.
	 * @param attribute attribute for which equality is checked
	 * @param a first event
	 * @param b object
	 * @return true if attributes are equal, else false
	 * @throws OperatorNotSupportedException 
	 */
	public static boolean equalAttribute(String attribute, IEvent a, Object b) throws OperatorNotSupportedException {
		
		if (a == null) {
			throw new OperatorNotSupportedException("Null event.");
		}
			
		if (a.getAttributes().get(attribute) == null) {
			throw new OperatorNotSupportedException("Attribute not found.");
		}
		
		return a.getAttributes().get(attribute).getValue().equals(b);
	}
}
