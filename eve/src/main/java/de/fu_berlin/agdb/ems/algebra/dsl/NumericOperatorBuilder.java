/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.ems.algebra.operators.numeric.Greater;
import de.fu_berlin.agdb.ems.algebra.operators.numeric.GreaterEqual;
import de.fu_berlin.agdb.ems.algebra.operators.numeric.Less;
import de.fu_berlin.agdb.ems.algebra.operators.numeric.LessEqual;
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
	public static Match equal(String attribute, IEvent b) {
		
		return new Equal(attribute, b);
	}
	
	/**
	 * equal: Checks if attribute of event is equal to the one of event b.
	 * @param attribute attribute that is checked for equality
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attributes are equal, false else
	 */
	public static Match equal(String attribute, Object b) {
		
		return new Equal(attribute, b);
	}
	
	/**
	 * less: True if attribute of event is smaller than that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute smaller, false else
	 */
	public static Match less(String attribute, IEvent b) {

		return new Less(attribute, b);
	}
	
	/**
	 * less: True if attribute of event is smaller than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute smaller, false else
	 */
	public static Match less(String attribute, Object b) {

		return new Less(attribute, b);
	}
	
	/**
	 * lessEqual: True if attribute of event is smaller than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute smaller or equal, false else
	 */
	public static Match lessEqual(String attribute, IEvent b) {

		return new LessEqual(attribute, b);
	}
	
	/**
	 * lessEqual: True if attribute of event is smaller than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute smaller or equal, false else
	 */
	public static Match lessEqual(String attribute, Object b) {

		return new LessEqual(attribute, b);
	}
	
	/**
	 * greater: True if attribute of event is bigger than that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute bigger, false else
	 */
	public static Match greater(String attribute, IEvent b) {

		return new Greater(attribute, b);
	}
	
	/**
	 * greater: True if attribute of event is bigger than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Match greater(String attribute, Object b) {

		return new Greater(attribute, b);
	}
	
	/**
	 * greater: True if attribute of event is bigger than object b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Match greater(String attribute, Operator b) {

		return new Greater(attribute, b);
	}
	
	/**
	 * greaterEqual: True if attribute of event is bigger than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b event for comparison
	 * @return true if attribute bigger or equal, false else
	 */
	public static Match greaterEqual(String attribute, IEvent b) {

		return new GreaterEqual(attribute, b);
	}
	
	/**
	 * greaterEqual: True if attribute of event is bigger than or equal to that of event b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger or equal, false else
	 */
	public static Match greaterEqual(String attribute, Object b) {

		return new GreaterEqual(attribute, b);
	}
}
