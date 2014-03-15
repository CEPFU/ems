/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Base class for all Operators of the algebra.
 * @author Ralf Oechsner
 *
 */
public abstract class Operator {

	protected boolean state = false;
	protected IEvent[] lastMatchingEvent = null;
	protected Operator[] children = null;
	
	/**
	 * Apply the operator.
	 * @param event current event from event stream
	 * @return true or false depending on the implemented operator
	 * @throws OperatorNotSupportedException thrown if the operation is not supported by the (or attribute) type
	 */
	public abstract boolean apply(IEvent event) throws OperatorNotSupportedException;
	
	/**
	 * Get events that most recently matched the operator.
	 * @return last matching event
	 */
	public IEvent[] getMatchingEvents() {
		
		return this.lastMatchingEvent;
	}
	
	/**
	 * Sets all matches to false (needed when a rule matched once).
	 */
	public void reset() {
		
		this.state = false;
	}
	
	/**
	 * Get children operators if they exist, else null.
	 * @return children if exist, else null
	 */
	public Operator[] getChildren() {
		
		return this.children;
	}
	
	/**
	 * Get matched events as a String.
	 * @return
	 */
	public String matchToString() {
		
		String result = "";
		
		if (this.getChildren() != null) {
			for (Operator curChild : this.getChildren()) {
				result += curChild.matchToString();
			}
		}
		else if (this.getMatchingEvents() != null) {
			
			result += this.toString() + "{";
			
			for (IEvent curEvent : this.getMatchingEvents()) {
				result += curEvent;
			}
			
			result += "}";
		}
		
		return result;
	}
}
