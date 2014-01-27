/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class LogicOperatorBuilder {

	
	public static Operator and(final Operator op1, final Operator op2) {
		
		return new Operator() {
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				return op1.apply(event) && op2.apply(event);
			}
			
			@Override
	        public String toString(){
	            return "and(" + op1 + ", " + op2 + ")";
	        } 
		};
	}
	
	public static Operator or(final Operator op1, final Operator op2) {
		
		return new Operator() {
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				return op1.apply(event) || op2.apply(event);
			}
			
			@Override
	        public String toString(){
	            return "or(" + op1 + ", " + op2 + ")";
	        } 
		};
	}
	
	public static Operator xor(final Operator op1, final Operator op2) {
		
		return new Operator() {
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				return (op1.apply(event) || op2.apply(event)) && !(op1.apply(event) && op2.apply(event));
			}
			
			@Override
	        public String toString(){
	            return "xor(" + op1 + ", " + op2 + ")";
	        } 
		};
	}
	
	public static Operator not(final Operator op) {
		
		return new Operator() {
			
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				return !op.apply(event);
			}
			
			@Override
	        public String toString(){
	            return "not(" + op + ")";
	        } 
		};
	}
}
