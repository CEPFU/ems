package de.fu_berlin.agdb.ems;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.ems.algebra.Verbose;
import de.fu_berlin.agdb.ems.core.Configuration;
import de.fu_berlin.agdb.ems.core.SourceParser;


/**
 * Main routine of backend.
 * @author Ralf Oechsner
 *
 */
public class App {
	
	private static Logger logger = LogManager.getLogger();
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

		logger.info("Looking for interest files in: " + mainConfiguration.getSourcesFolder());
		try {
			// this route prints everything that is loaded into the queue (for debugging purposes)
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	from("ems-jms:queue:main.queue").process(new Verbose());
			    }
			});
			
			// this route loads source files from disk and process them via the InterestParser
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	// source files are moved to "inprogress" during processing and to "done" after processing
			    	from("file://" + mainConfiguration.getSourcesFolder() + "?preMove=inprogress/&move=../done/&moveFailed=failed/").process(new SourceParser());
			    }
			});	
			
			// start context and run indefinitely
			camelContext.start();
			
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
