package de.fu_berlin.agdb.ems.algebra;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.fu_berlin.agdb.ems.data.IEvent;

public class Verbose implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		System.out.println("[VERBOSE]: " + exchange.getIn().getBody(IEvent.class));
	}
}
