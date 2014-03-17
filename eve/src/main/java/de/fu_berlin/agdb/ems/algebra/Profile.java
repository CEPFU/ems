/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.algebra.notifications.Notification;
import de.fu_berlin.agdb.ems.algebra.windows.IWindow;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Defines a profile (user interest) in the rule system (algebra).
 * @author Ralf Oechsner
 *
 */
public class Profile {

	private Operator rule;
	private Notification[] notifications;
	private IWindow window;

	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Profile with a rule and a list of notifications that are send when the
	 * rule matches.
	 * @param rule rule
	 * @param notifications list of notifications
	 */
	public Profile(Operator rule, IWindow window, Notification ... notifications) {
	
		this.rule = rule;
		this.notifications = notifications;
		
		for (Notification curNotification : notifications) {
			curNotification.setRule(rule);
		}
		
		this.setWindow(window);
	}
		
	/**
	 * Checks if the rule matches on an event and sends the notification(s) if
	 * necessary.
	 * @throws OperatorNotSupportedException thrown when an event type is not
	 *                                       by an operator of the rule
	 */
	public void apply(IEvent event) throws OperatorNotSupportedException {
		
		if (this.rule.apply(event)) {
			
			logger.info("Matching events: " + this.rule.matchToString());
			
			// throw notifications
			for (Notification curNotification : notifications) {
				curNotification.apply();
			}
			
			// and reset matches so that rule only fires once (important!)
			// unlike in previous versions, the rule is now reset after the
			// application of the notifications. This gives the notifications
			// access to the matched events. To prevent an endless loop the events
			// are now queued in the algebra and sent after completion of the profile
			this.rule.reset();
		}
		
		// windowing is checked regardless of match TODO: check
		if (!this.window.apply()) {
			this.rule.reset();
		}
	}

	/**
	 * Getter for notifications
	 * @return notifications
	 */
	public Notification[] getNotifications() {
		
		return notifications;
	}
	
	/**
	 * Sets the window for the rule of the profile.
	 * @param window window for the rule
	 */
	public void setWindow(IWindow window) {
		
		this.window = window;
		this.rule.setWindow(window);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "Profile [rule=" + rule + ", notifications="
				+ Arrays.toString(notifications) + "]";
	}
}
