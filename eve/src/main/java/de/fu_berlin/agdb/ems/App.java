package de.fu_berlin.agdb.ems;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import de.fu_berlin.agdb.ems.algebra.Verbose;
import de.fu_berlin.agdb.ems.core.Configuration;
import de.fu_berlin.agdb.ems.core.SourceParser;


/**
 * Main routine of backend.
 * @author Ralf Oechsner
 *
 */
public class App {
	
	public static final Configuration mainConfiguration = new Configuration();
	
	/**
	 * Main program routine.
	 * @param args not used yet
	 */
    public static void main(String[] args) {
	
    	// load configuration if it exists or create one otherwise
    	mainConfiguration.loadAndCreate();
    	
		// create camel context for internal message routing
		CamelContext camelContext = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		camelContext.addComponent("ems-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		System.out.println("Looking for interest files in: " + mainConfiguration.getInterestsFolder());
		try {
			// this route prints everything that is loaded into the queue (for debugging purposes)
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	from("ems-jms:queue:main.queue").process(new Verbose());
			    }
			});
			
			// this route loads interest files from disk and process them via the InterestParser
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	// interest files are moved to "inprogress" during processing and to "done" after processing
			    	from("file://" + mainConfiguration.getInterestsFolder() + "?preMove=inprogress/&move=../done/&moveFailed=failed/").process(new SourceParser());
			    }
			});
			
			ProducerTemplate template = camelContext.createProducerTemplate();			
			
			// start context and run indefinitely
			camelContext.start();
			
			for (int i = 0; i < 10; i++) {
			    template.sendBody("ems-jms:queue:main.queue", "Test Message: " + i);
			}
			
			Object obj = new Object();
			synchronized (obj) {
				obj.wait();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
}
