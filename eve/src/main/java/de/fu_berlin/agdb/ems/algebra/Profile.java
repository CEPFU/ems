/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.algebra.notifications.Notification;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Defines a profile (user interest) in the rule system (algebra).
 * @author Ralf Oechsner
 *
 */
public class Profile {

	private Operator rule;
	private Notification[] notifications;

	private static Logger logger = LogManager.getLogger();
	
	public Profile(Operator rule, Notification notification) {
		
		this.rule = rule;
		this.notifications = new Notification[1];
		this.notifications[0] = notification;
		this.notifications[0].setRule(rule);
	}
	
	public Profile(Operator rule, Notification ... notifications) {
	
		this.rule = rule;
		this.notifications = notifications;
		
		for (Notification curNotification : notifications) {
			curNotification.setRule(rule);
		}
	}
	
	public void apply(IEvent event) throws OperatorNotSupportedException {
		
		if (this.rule.apply(event)) {
			
			logger.info("Matching events: " + this.rule.matchToString());
		
			// and reset matches so that rule only fires once (important!)
			// it's also important to reset it before throwing the notification
			// because otherwise it will result in an endless loop
			this.rule.reset();
			
			// throw notifications
			for (Notification curNotification : notifications) {
				curNotification.apply();
			}
		}
	}

	/**
	 * Getter for notifications
	 * @return notifications
	 */
	public Notification[] getNotifications() {
		
		return notifications;
	}

	@Override
	public String toString() {
		return "Profile [rule=" + rule + ", notifications="
				+ Arrays.toString(notifications) + "]";
	}
}
