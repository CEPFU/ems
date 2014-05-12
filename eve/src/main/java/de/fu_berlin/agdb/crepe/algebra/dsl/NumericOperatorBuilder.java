/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Divide;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Greater;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.GreaterEqual;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Less;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.LessEqual;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Minus;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.MovingAverage;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Plus;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Times;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * DSL for numeric operators.
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
	 * equal: Checks if matching events of two operators are equal.
	 * @param attribute attribute that is checked for equality
	 * @param a first operator
	 * @param b second operator
	 * @return true if attributes are equal, false else
	 */
	public static Match equal(String attribute, Operator a, Operator b) {
		
		return new Equal(attribute, a, b);
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
	 * less: True if attribute of event is smaller than matching event of operator b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute smaller, false else
	 */
	public static Match less(String attribute, Operator b) {

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
	 * lessEqual: True if attribute of event is small than or equal to matching event of operator b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Match lessEqual(String attribute, Operator b) {

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
	 * greater: True if attribute of event is bigger than matching event of operator b.
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
	
	/**
	 * greaterEqual: True if attribute of event is bigger than or equal to matching event of operator b.
	 * @param attribute attribute that is compared
	 * @param b object for comparison (primitives like e.g. int or double work as well)
	 * @return true if attribute bigger, false else
	 */
	public static Match greaterEqual(String attribute, Operator b) {

		return new GreaterEqual(attribute, b);
	}
	
	/**
	 * plus: Adds two operands.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match plus(String attribute, Operator a, Operator b) {
		
		return new Plus(attribute, a, b);
	}
	
	/**
	 * plus: Adds two operands.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match plus(String attribute, Object a, Object b) {
		
		return new Plus(attribute, a, b);
	}
	
	/**
	 * plus: Adds two operands.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match plus(String attribute, Operator a, Object b) {
		
		return new Plus(attribute, a, b);
	}
	
	/**
	 * plus: Adds two operands.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match plus(String attribute, Object a, Operator b) {
		
		return new Plus(attribute, a, b);
	}
	
	/**
	 * minus: Subtracts two operands.
	 * @param attribute attribute whose values are subtracted
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match minus(String attribute, Operator a, Operator b) {
		
		return new Minus(attribute, a, b);
	}
	
	/**
	 * minus: Subtracts two operands.
	 * @param attribute attribute whose values are subtracted
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match minus(String attribute, Object a, Object b) {
		
		return new Minus(attribute, a, b);
	}
	
	/**
	 * minus: Subtracts two operands.
	 * @param attribute attribute whose values are subtracted
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match minus(String attribute, Operator a, Object b) {
		
		return new Minus(attribute, a, b);
	}
	
	/**
	 * minus: Subtracts two operands.
	 * @param attribute attribute whose values are subtracted
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match minus(String attribute, Object a, Operator b) {
		
		return new Minus(attribute, a, b);
	}
	
	
	/**
	 * times: Multiplies two operands.
	 * @param attribute attribute whose values are multiplied
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match times(String attribute, Operator a, Operator b) {
		
		return new Times(attribute, a, b);
	}
	
	/**
	 * times: Multiplies two operands.
	 * @param attribute attribute whose values are multiplied
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match times(String attribute, Object a, Object b) {
		
		return new Times(attribute, a, b);
	}
	
	/**
	 * times: Multiplies two operands.
	 * @param attribute attribute whose values are multiplied
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match times(String attribute, Operator a, Object b) {
		
		return new Times(attribute, a, b);
	}
	
	/**
	 * times: Multiplies two operands.
	 * @param attribute attribute whose values are multiplied
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match times(String attribute, Object a, Operator b) {
		
		return new Times(attribute, a, b);
	}
	
	/**
	 * divide: Divides two operands.
	 * @param attribute attribute whose values are divided
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match divide(String attribute, Operator a, Operator b) {
		
		return new Divide(attribute, a, b);
	}
	
	/**
	 * divide: Divides two operands.
	 * @param attribute attribute whose values are divided
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match divide(String attribute, Object a, Object b) {
		
		return new Divide(attribute, a, b);
	}
	
	/**
	 * divide: Divides two operands.
	 * @param attribute attribute whose values are divided
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match divide(String attribute, Operator a, Object b) {
		
		return new Divide(attribute, a, b);
	}
	
	/**
	 * divide: Divides two operands.
	 * @param attribute attribute whose values are divided
	 * @param a first operand
	 * @param b second operand
	 */
	public static Match divide(String attribute, Object a, Operator b) {
		
		return new Divide(attribute, a, b);
	}
	
	/**
	 * movingAverage: Calculates the moving average of an attribute.
	 * @param attribute attribute whose average is calculated
	 * @param averageAttribute attribute name of average
	 * @param windowSize window size
	 */
	public static Match movingAverage(String attribute, String averageAttribute, int windowSize) {
		
		return new MovingAverage(attribute, averageAttribute, windowSize);
	}
}
