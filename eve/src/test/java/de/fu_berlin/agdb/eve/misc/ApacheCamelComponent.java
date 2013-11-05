/**
 * 
 */
package de.fu_berlin.agdb.eve.misc;

import static org.junit.Assert.assertTrue;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultEndpoint;
import org.junit.Test;

/**
 * @author Ralf Oechsner
 *
 */
public class ApacheCamelComponent {

	public class Verbose implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {
			System.out.println("Test: " + exchange.getIn().getBody(String.class));
		}
	}
	
	// TODO: implement and test
	public class MessageChecker extends DefaultEndpoint {

		@Override
		public Producer createProducer() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Consumer createConsumer(Processor processor) throws Exception {
			
			return null;
		}

		@Override
		public boolean isSingleton() {
			// TODO Auto-generated method stub
			return false;
		}


	}
	
	
	@Test
	public void test() {
	
		CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        // Note we can explicit name the component
        context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
		try {
			context.addRoutes(new RouteBuilder() {
			    public void configure() {
//			    	from("test-jms:queue:test.queue").split().method().process(new Verbose());
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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
