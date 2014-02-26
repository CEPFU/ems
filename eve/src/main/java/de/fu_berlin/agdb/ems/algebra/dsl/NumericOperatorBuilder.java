/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Numeric operators.
 * @author Ralf Oechsner
 *
 */
public class NumericOperatorBuilder {

	/**
	 * equal: Checks if attribute of event is equal to the one of event b.
	 * @param attribute attribute that is checked for equality
	 * @param b event for comparison
	 * @return true if attributes are equal, false else
	 */
	public static Operator equal(final String attribute, final IEvent b) {
		
		return new Operator() {
			
			IEvent[] matchingEvent = new IEvent[1];
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean comparison = equalAttribute(attribute, event, b);
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "equal(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {

				// nothing needs to be done to reset this operator
			}
		};
	}
	
	/**
	 * less: True if attribute of event is smaller than that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute smaller, false else
	 */
	public static Operator less(final String attribute, final IEvent b) {

		return new Operator() {

			IEvent[] matchingEvent = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean comparison = compare(attribute, event, b) < 0;
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "less(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {
				
				return this.matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * less: True if attribute of event is smaller than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute smaller, false else
	 */
	public static Operator less(final String attribute, final Object b) {

		return new Operator() {

			IEvent[] matchingEvent = new IEvent[1];
			boolean state = false;
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean comparison = compare(attribute, event, b) < 0;				
				
				if (comparison) {
					this.state = comparison;
					this.matchingEvent[0] = event;
				}
				
				return state;
			}
			
			@Override
			public String toString() {
				
				return "less(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {
				
				return this.matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * lessEqual: True if attribute of event is smaller than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute smaller or equal, false else
	 */
	public static Operator lessEqual(final String attribute, final IEvent b) {

		return new Operator() {

			IEvent[] matchingEvent = new IEvent[1];
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				// is not the same as just compare(attribute, event, b) <= 0 because an object
				// can be equal to another without being a comparable (which compare() requires).
				boolean comparison = compare(attribute, event, b) < 0 || equalAttribute(attribute, event, b);
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "lessEqual(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * lessEqual: True if attribute of event is smaller than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute smaller or equal, false else
	 */
	public static Operator lessEqual(final String attribute, final Object b) {

		return new Operator() {

			IEvent[] matchingEvent = new IEvent[1];
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				// is not the same as just compare(attribute, event, b) <= 0 because an object
				// can be equal to another without being a comparable (which compare() requires).
				boolean comparison = compare(attribute, event, b) < 0 || equalAttribute(attribute, event, b);
				
				if (comparison)
					this.matchingEvent[0] = event;
					
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "lessEqual(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * greater: True if attribute of event is bigger than that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute bigger, false else
	 */
	public static Operator greater(final String attribute, final IEvent b) {

		return new Operator() { 

			IEvent[] matchingEvent = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean comparison = compare(attribute, event, b) < 0 || equalAttribute(attribute, event, b);
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "greater(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * greater: True if attribute of event is bigger than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Operator greater(final String attribute, final Object b) {

		return new Operator() { 

			IEvent[] matchingEvent = new IEvent[1];
			boolean state = false;
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean comparison = compare(attribute, event, b) > 0;
				
				if (comparison) {
					this.state = comparison;
					this.matchingEvent[0] = event;
				}
				
				return state;
			}
			
			@Override
			public String toString() {
				
				return "greater(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * greater: True if attribute of event is bigger than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Operator greater(final String attribute, final Operator b) {

		return new Operator() { 

			IEvent[] matchingEvent = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				if (b.getMatchingEvents() == null)
					throw new OperatorNotSupportedException("Comparison to null-event.");
				
				IEvent eventB = b.getMatchingEvents()[0];
				
				boolean comparison = compare(attribute, event, eventB) > 0;
				
				if (comparison)
					this.matchingEvent[0] = event;
					
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "greater(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * greaterEqual: True if attribute of event is bigger than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute bigger or equal, false else
	 */
	public static Operator greaterEqual(final String attribute, final IEvent b) {

		return new Operator() { 

			IEvent[] matchingEvent = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				// is not the same as just compare(attribute, event, b) >= 0 because an object
				// can be equal to another without being a comparable (which compare() requires).
				boolean comparison = compare(attribute, event, b) > 0 || equalAttribute(attribute, event, b);
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "greaterEqual(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * greaterEqual: True if attribute of event is bigger than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger or equal, false else
	 */
	public static Operator greaterEqual(final String attribute, final Object b) {

		return new Operator() { 

			IEvent[] matchingEvent = new IEvent[1];
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				// is not the same as just compare(attribute, event, b) >= 0 because an object
				// can be equal to another without being a comparable (which compare() requires).
				boolean comparison = compare(attribute, event, b) > 0;
				
				if (comparison)
					this.matchingEvent[0] = event;
				
				return comparison;
			}
			
			@Override
			public String toString() {
				
				return "greaterEqual(" + attribute + ", " + b + ")";
			}

			@Override
			public IEvent[] getMatchingEvents() {

				return matchingEvent;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * Compares two attributes to each other provided that they implement the comparable interface. If not an Exception is thrown.
	 * @param attribute attribute that is compared
	 * @param a first event
	 * @param b second event
	 * @return < 0 if a less b, = 0 if a == b, > 0 if a greater b.
	 * @throws OperatorNotSupportedException indicates that the attributes aren't comparable
	 */
	private static int compare(String attribute, IEvent a, IEvent b) throws OperatorNotSupportedException {
		
		if (b == null) {
			throw new OperatorNotSupportedException("Comparison with null-event.");
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
			@SuppressWarnings("unchecked")
			Comparable<Object> c2 = (Comparable<Object>) b.getAttributes().get(attribute).getValue();

			return c1.compareTo(c2);
		} catch (ClassCastException e) {
			throw new OperatorNotSupportedException("Numeric comparison operator not supported between attribute types "
					+ a.getAttributes().get(attribute).getValue().getClass() + " and "
					+ b.getAttributes().get(attribute).getValue().getClass());
		}
	}
	
	/**
	 * Compares two attributes to each other provided that they implement the comparable interface. If not an Exception is thrown.
	 * @param attribute attribute that is compared
	 * @param a first event
	 * @param b object for comparison
	 * @return < 0 if a less b, = 0 if a == b, > 0 if a greater b.
	 * @throws OperatorNotSupportedException indicates that the attributes aren't comparable
	 */
	private static int compare(String attribute, IEvent a, Object b) throws OperatorNotSupportedException {
		
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
	 * @param b second event
	 * @return true if attributes are equal, else false
	 * @throws OperatorNotSupportedException 
	 */
	private static boolean equalAttribute(String attribute, IEvent a, IEvent b) throws OperatorNotSupportedException {
		
		if (a.getAttributes().get(attribute) == null) {
			throw new OperatorNotSupportedException("Attribute not found.");
		}
		
		return a.getAttributes().get(attribute).getValue().equals(b.getAttributes().get(attribute).getValue());
	}
	
	/**
	 * Checks if two attributes are equal.
	 * @param attribute attribute for which equality is checked
	 * @param a first event
	 * @param b object
	 * @return true if attributes are equal, else false
	 * @throws OperatorNotSupportedException 
	 */
	private static boolean equalAttribute(String attribute, IEvent a, Object b) throws OperatorNotSupportedException {
		
		if (a.getAttributes().get(attribute) == null) {
			throw new OperatorNotSupportedException("Attribute not found.");
		}
		
		return a.getAttributes().get(attribute).getValue().equals(b);
	}
}
