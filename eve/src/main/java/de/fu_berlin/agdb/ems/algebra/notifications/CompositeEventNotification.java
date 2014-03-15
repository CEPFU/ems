/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.algebra.Algebra;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Notification that sends one or more events into the event queue (composite events).
 * @author Ralf Oechsner
 *
 */
public class CompositeEventNotification implements Notification {

	private IEvent[] compositeEvents;
	private Operator rule;
	private Algebra algebra;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Creates a notification that sends one or more events to the algebra queue.
	 * @param events composite events to be sent
	 */
	public CompositeEventNotification(IEvent ... events) {
		
		this.compositeEvents = new IEvent[events.length];
		
		for (int i = 0; i < events.length; i++) {
			
			this.compositeEvents[i] = events[i];
		}
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#apply()
	 */
	@Override
	public void apply() {
		
		Date curTime = new Date();
		
		for (IEvent curEvent : compositeEvents) {
			
			// time stamp of events have the current time by default
			// so it _must_ be updated, otherwise they have the time
			// of the event creation
			curEvent.setTimeStamp(curTime);
			
			this.algebra.queueEvent(curEvent);
			logger.info("Sent event to queue: " + curEvent);
		}
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#setRule(de.fu_berlin.agdb.ems.algebra.Operator)
	 */
	@Override
	public void setRule(Operator rule) {

		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#getRule()
	 */
	@Override
	public Operator getRule() {

		return this.rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#setAlgebra(de.fu_berlin.agdb.ems.algebra.Algebra)
	 */
	@Override
	public void setAlgebra(Algebra algebra) {
		
		this.algebra = algebra;
	}

	@Override
	public String toString() {
		return "CompositeEventNotification [compositeEvents="
				+ Arrays.toString(compositeEvents) + "]";
	}
}
