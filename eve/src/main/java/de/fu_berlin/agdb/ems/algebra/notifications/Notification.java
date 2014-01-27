/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import de.fu_berlin.agdb.ems.algebra.Operator;

/**
 * @author Ralf Oechsner
 *
 */
public interface Notification {

	public void apply();
	
	public void setRule(Operator rule);
	public Operator getRule();
}
