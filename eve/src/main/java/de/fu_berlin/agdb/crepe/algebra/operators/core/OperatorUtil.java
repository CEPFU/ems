/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.core;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;

/**
 * Utility functions for operators.
 * @author Ralf Oechsner
 *
 */
public class OperatorUtil {
	
	/**
	 * Utility function to get operands.
	 * @param attribute attribute of operand
	 * @param op operator whose attribute should be returned
	 * @return object with operand
	 * @throws OperatorNotSupportedException thrown if null event or attribute not found
	 */
	public static Object getOperands(String attribute, Operator op) throws OperatorNotSupportedException {
		
		Object a = null;
		
		if (op != null) {
			 if (op.getMatchingEvent() != null) {
				 throw new OperatorNotSupportedException("Null event.");
			 }
			
			if (!op.getMatchingEvent().getAttributes().containsKey(attribute))
				throw new OperatorNotSupportedException("Attribute not found.");
			a =  op.getMatchingEvent().getAttributes().get(attribute).getValue();
		}
		
		return a;
	}
}
