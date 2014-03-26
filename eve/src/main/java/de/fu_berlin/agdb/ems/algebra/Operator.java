/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.algebra.windows.IWindow;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Base class for all Operators of the algebra.
 * @author Ralf Oechsner
 *
 */
public abstract class Operator {

	protected boolean state = false;
	private IEvent lastMatchingEvent = null;
	protected Operator[] children = null;
	private IWindow window;
	
	/**
	 * Apply the operator.
	 * @param event current event from event stream
	 * @return true or false depending on the implemented operator
	 * @throws OperatorNotSupportedException thrown if the operation is not supported by the (or attribute) type
	 */
	public abstract boolean apply(IEvent event) throws OperatorNotSupportedException;
	
	/**
	 * Setter for matching events. 
	 * @param events
	 */
	public void setMatchingEvent(IEvent event) {
		
		this.lastMatchingEvent = event;
		this.getWindow().onMatch(event);
	}
	
	/**
	 * Get events that most recently matched the operator.
	 * @return last matching event
	 */
	public IEvent getMatchingEvent() {
		
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
		else if (this.getMatchingEvent() != null) {
			
			result += this.toString() + "{";
			result += this.getMatchingEvent();
			result += "}";
		}
		
		return result;
	}
	
	/**
	 * Sets window to the operator and all it's children (recursively).
	 * @param window window to set
	 */
	public void setWindow(IWindow window) {
	
		this.window = window;
		if (this.children != null) {
			for (Operator curChild : this.children) {
				curChild.setWindow(window.newInstance());
			}
		}
	}
	
	/**
	 * Getter for window that is applied on the operator.
	 * @return window of operator
	 */
	public IWindow getWindow() {
		
		return this.window;
	}
	
	/**
	 * Apply window to the operator and it's children (recursively).
	 */
	public void applyWindow() {
		
		if (this.children != null) {
			for (Operator curChild : this.children) {
				curChild.applyWindow();
			}
		}
		else {
			if (!this.window.apply()) {
				this.reset();
			}
		}
	}
}
