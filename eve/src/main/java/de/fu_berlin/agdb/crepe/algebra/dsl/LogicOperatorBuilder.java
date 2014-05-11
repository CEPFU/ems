/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.And;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.Not;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.Or;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.Xor;

/**
 * Logic Operators.
 * @author Ralf Oechsner
 *
 */
public class LogicOperatorBuilder {

	/**
	 * Boolean and operator.
	 * @param op1 first operator
	 * @param op2 second operator
	 * @return and operator
	 */
	public static Operator and(Operator op1, Operator op2) {
		
		return new And(op1, op2);
	}
	
	/**
	 * Boolean or operator.
	 * @param op1 first operator
	 * @param op2 second operator
	 * @return or operator
	 */
	public static Operator or(final Operator op1, final Operator op2) {
		
		return new Or(op1, op2);
	}
	
	/**
	 * Boolean xor operator.
	 * @param op1 first operator
	 * @param op2 second operator
	 * @return xor operator
	 */
	public static Operator xor(final Operator op1, final Operator op2) {
		
		return new Xor(op1, op2);
	}
	
	/**
	 * Boolean not operator.
	 * @param op operator that is negated
	 * @return not operator
	 */
	public static Operator not(final Operator op) {
		
		return new Not(op);
	}
}
