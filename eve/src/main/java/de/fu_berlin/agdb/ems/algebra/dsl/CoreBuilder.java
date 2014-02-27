/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.operators.core.Attribute;
import de.fu_berlin.agdb.ems.algebra.operators.core.ForEvent;
import de.fu_berlin.agdb.ems.data.Event;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Basic operators.
 * @author Ralf Oechsner
 *
 */
public class CoreBuilder {

	/**
	 * True if an event has a given attribute.
	 * @param attribute attribute that is checked.
	 * @return true if event has attribute with that key, false else
	 */
	public static Operator attribute(String attribute) {
		
		return new Attribute(attribute);
	}
	
	/**
	 * Creates an event with one attribute.
	 * @param attribute key of attribute
	 * @param attributeValue attribute value.
	 * @return event created event
	 */
	public static IEvent event(String attribute, Object attributeValue) {
		
		return new Event(attribute, attributeValue);
	}
	
	/**
	 * Evaluates the second operator only on the matches
	 * of the first operator.
	 * @param eventOp
	 * @param op
	 * @return
	 */
	public static Operator forEvent(Operator eventOp, Operator op) {
		
		return new ForEvent(eventOp, op);
	}
}
