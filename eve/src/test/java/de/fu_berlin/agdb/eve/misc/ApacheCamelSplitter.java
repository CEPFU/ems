 /**
 * 
 */
package de.fu_berlin.agdb.eve.misc;

import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import de.fu_berlin.agdb.ems.producer.CSVSplitter;

/**
 * @author Ralf Oechsner
 *
 */
public class ApacheCamelSplitter {

	public class Verbose implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {
			System.out.println("Test: " + exchange.getIn().getBody(String.class));
		}
	}	
	
	@Test
	public void test() {
		
		CamelContext context = new DefaultCamelContext();

		try {
			context.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	from("file:///home/ralf/Documents/Uni/Master/Masterarbeit/Implementierung/data/?fileName=CSVTest1.csv&noop=true").split().method(CSVSplitter.class, "split").process(new Verbose());
			    }
			});
			
			context.start();
			
	        // wait a bit and then stop
	        Thread.sleep(2000);
	        context.stop();
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
