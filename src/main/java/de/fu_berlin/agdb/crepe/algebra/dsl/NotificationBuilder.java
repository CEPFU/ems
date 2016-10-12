/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.notifications.*;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IEvent;
import de.fu_berlin.agdb.crepe.outputadapters.IOutputAdapter;
import de.fu_berlin.agdb.crepe.writers.IWriter;

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
	
	/**
	 * Creates a notification that sends one composite event to the event stream that has
	 * one attribute.
	 * @param attribute attribute of the event that is sent into the stream
	 * @param attributeValue value of the attribute
	 * @param asAttribute attribute name of composite event attribute
	 * @return CompositeEventNotification CompositeEventNotification
	 */
	public static Notification compositeEventNotification(Match match, String attribute, String asAttribute) {
		
		return new CompositeEventNotification(match, attribute, asAttribute);
	}
	
	/**
	 * Creates a notification that writes to a sink. An output adapter is used to transform the event format.
	 * @param writer writer that is used for writing to the sink
	 * @param outputAdapter output adapter that is used to transform messages
	 * @param matches matching operators whose events are written
	 * @return WriterNotification
	 */
	public static Notification writerNotification(IWriter writer, IOutputAdapter outputAdapter, Match ... matches) {
		
		return new WriterNotification(writer, outputAdapter, matches);
	}
	
	/**
	 * Creates a notification that outputs a message and the matching events to the console.
	 * Meant for debugging purposes.
	 * @param message message that is printed with a match
	 * @return VerboseNotification
	 */
	public static Notification verboseNotification(String message) {
		
		return new VerboseNotification(message);
	}
	
	/**
	 * Creates a blank notification for profiles that don't need to send a notification.
	 * @return BlankNotification
	 */
	public static Notification blankNotification() {
		
		return new BlankNotification();
	}
	
	/**
	 * Creates a notification that prints the current time in milliseconds. Meant for benchmarks.
	 * @return TimeMeasureNotification
	 */
	public static Notification timeMeasureNotification() {
		
		return new TimeMeasureNotification();
	}

	/**
	 * Creates a notification that is being pushed to a user's Android device.
	 *
	 * @param message message that is shown
	 * @param firebaseToken the firebase token needed to push the message
	 * @param profilePrimaryKey
     * @return
     */
	public static AndroidPushNotification androidPushNotification(String message, String firebaseToken, long profilePrimaryKey) {
		return new AndroidPushNotification(message, firebaseToken, profilePrimaryKey);
	}
}
