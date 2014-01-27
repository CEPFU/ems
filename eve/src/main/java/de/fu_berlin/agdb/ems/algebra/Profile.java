/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.algebra.notifications.Notification;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class Profile {

	private Operator rule;
	private Notification[] notifications;
	
	public Profile(Operator rule, Notification notification) {
		
		this.rule = rule;
		this.notifications = new Notification[1];
		this.notifications[0] = notification;
		this.notifications[0].setRule(rule);
	}
	
	public Profile(Operator rule, Notification[] notifications) {
	
		this.rule = rule;
		this.notifications = notifications;
		
		for (Notification curNotification : notifications) {
			curNotification.setRule(rule);
		}
	}
	
	public void apply(IEvent event) throws OperatorNotSupportedException {
		
		if (this.rule.apply(event)) {
			for (Notification curNotification : notifications) {
				curNotification.apply();
			}
		}
	}
}
