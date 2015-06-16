/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Plus operator.
 * @author Ralf Oechsner
 *
 */
public class Plus extends Match {

	private static String DSL_NAME = "plus";
	
	private String attribute;
	private Operator op1, op2;
	private Object a, b;
	Number result;
	
	/**
	 * Plus operator.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public Plus(String attribute, Operator a, Operator b) {
		
		this.attribute = attribute;
		this.op1 = a;
		this.op2 = b;
		this.setChildren(op1, op2);
	}
	
	/**
	 * Plus operator.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public Plus(String attribute, Object a, Object b) {
		
		this.attribute = attribute;
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Plus operator.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public Plus(String attribute, Operator a, Object b) {
		
		this.op1 = a;
		this.setChildren(op1);
		this.b = b;
		this.attribute = attribute;
	}
	
	/**
	 * Plus operator.
	 * @param attribute attribute whose values are added
	 * @param a first operand
	 * @param b second operand
	 */
	public Plus(String attribute, Object a, Operator b) {
		
		this.a = a;
		this.setChildren(op2);
		this.op2 = b;
		this.attribute = attribute;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		if (this.op1 != null) {
			 if (this.op1.getMatchingEvent() == null) {
				 throw new OperatorNotSupportedException("Null event.");
			 }
			
			if (!op1.getMatchingEvent().getAttributes().containsKey(this.attribute))
				throw new OperatorNotSupportedException("Attribute not found.");
			a =  op1.getMatchingEvent().getAttributes().get(this.attribute).getValue();
		}
			
		if (this.op2 != null) {
			 if (this.op2.getMatchingEvent() == null) {
				 throw new OperatorNotSupportedException("Null event.");
			 }
			
			if (!op2.getMatchingEvent().getAttributes().containsKey(this.attribute))
				throw new OperatorNotSupportedException("Attribute not found.");
			b =	op2.getMatchingEvent().getAttributes().get(this.attribute).getValue();
		}
		
		// For primitives the unboxing of the java compiler is used.
		// This is ugly but it doesn't work any other way in Java.
		
		// a is Integer
		if (a instanceof Integer && b instanceof Integer)
			result = (Integer) a + (Integer) b;
		else if (a instanceof Integer && b instanceof Float)
			result = (Integer) a + (Float) b;
		else if (a instanceof Integer && b instanceof Double)
			result = (Integer) a + (Double) b;
		else if (a instanceof Integer && b instanceof Long)
			result = (Integer) a + (Long) b;

		// a is Float
		else if (a instanceof Float && b instanceof Integer)
			result = (Float) a + (Integer) b;
		else if (a instanceof Float && b instanceof Float)
			result = (Float) a + (Float) b;
		else if (a instanceof Float && b instanceof Double)
			result = (Float) a + (Double) b;
		else if (a instanceof Float && b instanceof Long)
			result = (Float) a + (Long) b;
		
		// a is Double
		else if (a instanceof Double && b instanceof Integer)
			result = (Double) a + (Integer) b;
		else if (a instanceof Double && b instanceof Float)
			result = (Double) a + (Float) b;
		else if (a instanceof Double && b instanceof Double)
			result = (Double) a + (Double) b;
		else if (a instanceof Double && b instanceof Long)
			result = (Double) a + (Long) b;
		
		// a is Long
		else if (a instanceof Long && b instanceof Integer)
			result = (Long) a + (Integer) b;
		else if (a instanceof Long && b instanceof Float)
			result = (Long) a + (Float) b;
		else if (a instanceof Long && b instanceof Double)
			result = (Long) a + (Double) b;
		else if (a instanceof Long && b instanceof Long)
			result = (Long) a + (Long) b;
		
		else
			throw new OperatorNotSupportedException("Object types not supported by operator!");
		
		this.setMatchingEvent(new Event(this.attribute, result));
		
		return true; // this operator matches always
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String s = DSL_NAME + "(" + attribute;
		
		if (this.op1 != null)
			s += this.op1;
		else 
			s += this.a;
		s += ", ";
		
		if (this.op2 != null)
			s += this.op2;
		else
			s += this.b;
		
		return s + ")";
	}
}
