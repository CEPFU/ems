/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.notifications.CompositeEventNotification;
import de.fu_berlin.agdb.ems.algebra.notifications.Notification;
import de.fu_berlin.agdb.ems.data.Event;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * DSL for notifications.
 * @author Ralf Oechsner
 *
 */
public class NotificationBuilder {

	/**
	 * Creates a notification that sends one or more composite events to the event stream.
	 * @param events events to be sent
	 * @return CompositeEventNotification CompositeEventNotification
	 */
	public static Notification compositeEventNotification(IEvent ... events) {
		
		return new CompositeEventNotification(events);
	}
	
	/**
	 * Creates a notification that sends one composite event to the event stream that has
	 * one attribute.
	 * @param attribute attribute of the event that is sent into the stream
	 * @param attributeValue value of the attribute
	 * @return CompositeEventNotification CompositeEventNotification
	 */
	public static Notification compositeEventNotification(String attribute, Object attributeValue) {
		
		return new CompositeEventNotification(new Event(attribute, attributeValue)); 
	}
}
