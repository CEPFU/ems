/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class NumericOperatorBuilder {

	public static Operator less(final String attribute, final IEvent b) {

		return new Operator() {

			public boolean apply(IEvent event) {
				
				// ok this one is a bit tricky: the idea is that not every operation is supported by every attribute
				// type and therefore the operator can't know if it works. For example the less than operator needs the
				// attributes to implement the (typed) comparable interface but it can't know it at runtime (type erasure in java).
				// So the operator just the cast to comparable and if it doesn't work the rule isn't executed.
				try {
					@SuppressWarnings("unchecked")
					Comparable<Object> c1 = (Comparable<Object>) event.getAttributes().get(attribute).getValue();
					@SuppressWarnings("unchecked")
					Comparable<Object> c2 = (Comparable<Object>) b.getAttributes().get(attribute).getValue();

					return c1.compareTo(c2) < 0;
				} catch (ClassCastException e) {
					System.out.println("Class cast");
					return false;
				}
			}
		};
	}
	
	public static Operator greater(final String attribute, final IEvent b) {

		return new Operator() { 

			public boolean apply(IEvent event) {
				
				// ok this one is a bit tricky: the idea is that not every operation is supported by every attribute
				// type and therefore the operator can't know if it works. For example the less than operator needs the
				// attributes to implement the (typed) comparable interface but it can't know it at runtime (type erasure in java).
				// So the operator just the cast to comparable and if it doesn't work the rule isn't executed.
				try {
					@SuppressWarnings("unchecked")
					Comparable<Object> c1 = (Comparable<Object>) event.getAttributes().get(attribute).getValue();
					@SuppressWarnings("unchecked")
					Comparable<Object> c2 = (Comparable<Object>) b.getAttributes().get(attribute).getValue();

					return c1.compareTo(c2) > 0;
				} catch (ClassCastException e) {
					System.out.println("Class cast");
					return false;
				}
			}
		};
	}
}
