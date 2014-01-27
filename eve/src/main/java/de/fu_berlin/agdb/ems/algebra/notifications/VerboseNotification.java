/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import de.fu_berlin.agdb.ems.algebra.Operator;


/**
 * @author Ralf Oechsner
 *
 */
public class VerboseNotification implements Notification {

	private String message;
	private Operator rule;
	
	public VerboseNotification(String message) {
		
		this.message = message;
	}
	
	@Override
	public void apply() {
		// TODO Auto-generated method stub
		System.out.println(message + " Rule: " + rule);
	}

	public Operator getRule() {
		return rule;
	}

	public void setRule(Operator rule) {
		this.rule = rule;
	}
}
