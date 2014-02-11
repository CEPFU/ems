/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.algebra.notifications.Notification;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Event algebra system.
 * @author Ralf Oechsner
 *
 */
public class Algebra implements Processor {
	
	public static final String EVENT_QUEUE_URI = "direct:eventQueue";
	
	private List<Profile> profiles;
	private History history;
	private ProducerTemplate producer;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Initializes the algebra with an empty set of rules.
	 * @param context camel context in which the algebra is run
	 */
	public Algebra(CamelContext context) {
		
		this.profiles = new ArrayList<Profile>();
		this.history = new History();
		
		// Create producer that is used for sending things back into the queue.
		// It must be created only once because otherwise every time a producer is created
		// another thread is started by apache camel
		this.producer = context.createProducerTemplate();
		this.producer.setDefaultEndpointUri(EVENT_QUEUE_URI);
		
		logger.info("Algebra system initialised.");
	}
	
	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		
		IEvent event = exchange.getIn().getBody(IEvent.class);
		
		logger.debug("Processing event:\n" + event);
		
		// add to history because the rule system looks for a matching event
		// in the history
		this.history.add(event);
		
		// process all profiles
		Iterator<Profile> iter = this.profiles.iterator();
		while (iter.hasNext()) {
			
			Profile curProfile = iter.next();
			try {
				curProfile.apply(event);
			} catch(OperatorNotSupportedException e) {
				// TODO: check if that's really the case
				// profile with a rule that has operators in it that
				// aren't supported are removed
				iter.remove();
			}
		}
//		for (Profile curProfile : this.profiles) {
//			// TODO loeschen bei exception
//			curProfile.apply(event);
//		}
	}
	
	/**
	 * Adds a profile to the algebra.
	 * @param profile profile
	 */
	public void addProfile(Profile profile) {
		
		// all notifications need a reference to it's algebra to access methods
		// as for example queueEvent()
		for (Notification curNotification : profile.getNotifications()) {
			
			curNotification.setAlgebra(this);
		}
		
		this.profiles.add(profile);
	}
	
	
	/**
	 * Sends event into the main algebra queue.
	 * @param event event
	 */
	public void queueEvent(IEvent event) {
		
		this.producer.sendBody(event);
	}
}