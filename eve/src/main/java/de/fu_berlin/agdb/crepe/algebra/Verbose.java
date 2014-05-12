package de.fu_berlin.agdb.crepe.algebra;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.fu_berlin.agdb.crepe.data.IEvent;


/**
 * Processor that prints its messages to the console. Used for debugging of camel routes.
 * @author Ralf Oechsner
 *
 */
public class Verbose implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {

		System.out.println("[VERBOSE]: " + exchange.getIn().getBody(IEvent.class));
	}
}
