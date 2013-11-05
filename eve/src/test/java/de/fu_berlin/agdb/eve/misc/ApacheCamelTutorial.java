/**
 * 
 */
package de.fu_berlin.agdb.eve.misc;

import static org.junit.Assert.assertTrue;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

/**
 * Simple test of Apache Camel library.
 * @author Ralf Oechsner
 *
 */
public class ApacheCamelTutorial {

	public class Verbose implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {

			System.out.println("Header: " + exchange.getIn().getHeaders());
			System.out.println("Test: " + exchange.getIn().getBody(String.class));
		}
	}	
	
	@Test
	public void test() throws Exception {
		
		CamelContext context = new DefaultCamelContext();	
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        // Note we can explicit name the component
        context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
		context.addRoutes(new RouteBuilder() {
		    public void configure() {
		    	from("test-jms:queue:test.queue").process(new Verbose());
		    }
		});
		
		ProducerTemplate template = context.createProducerTemplate();
		
		context.start();
		
		
		for (int i = 0; i < 10; i++) {
		    template.sendBody("test-jms:queue:test.queue", "Test Message: " + i);
		}
		
        // wait a bit and then stop
        Thread.sleep(500);
        context.stop();
		
		assertTrue(true);
	}

}
