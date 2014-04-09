/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import de.fu_berlin.agdb.ems.algebra.Algebra;
import de.fu_berlin.agdb.ems.algebra.Operator;


/**
 * Verbose notification.
 * @author Ralf Oechsner
 *
 */
public class VerboseNotification implements Notification {

	private String message;
	private Operator rule;
	
	/**
	 * Creates a notification that just prints a message to the console.
	 * Mainly for debugging purposes. 
	 * @param message message that is printed
	 */
	public VerboseNotification(String message) {
		
		this.message = message;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#apply()
	 */
	@Override
	public void apply() {

		System.out.println(message + " Rule: " + rule);
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#getRule()
	 */
	public Operator getRule() {
		
		return rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#setRule(de.fu_berlin.agdb.ems.algebra.Operator)
	 */
	public void setRule(Operator rule) {
		
		this.rule = rule;
	}

	@Override
	public void setAlgebra(Algebra algebra) {
		
		// not needed here
	}
}
