/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import de.fu_berlin.agdb.ems.algebra.Algebra;
import de.fu_berlin.agdb.ems.algebra.Operator;

/**
 * Interface for notifications.
 * @author Ralf Oechsner
 *
 */
public interface Notification {

	/**
	 * Apply notification.
	 */
	public void apply();
	
	/**
	 * Setter for the rule that belongs to the notification.
	 * @param rule that belongs to the notification
	 */
	public void setRule(Operator rule);
	
	/**
	 * Getter for the rule that belongs to the notification.
	 * @return rule that belongs to the notification
	 */
	public Operator getRule();
	
	/**
	 * Setter for the algebra the notification belongs to.
	 * @param algebra algebra of the notification
	 */
	public void setAlgebra(Algebra algebra);
	
}
