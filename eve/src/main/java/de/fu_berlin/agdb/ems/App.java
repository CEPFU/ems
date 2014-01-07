package de.fu_berlin.agdb.ems;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import de.fu_berlin.agdb.ems.algebra.Verbose;
import de.fu_berlin.agdb.ems.core.Configuration;


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
		System.out.println("Looking for interest files in: " + mainConfiguration.getInterestsFolder());
		try {
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	// interest files are moved to "inprogress" during processing and to "done" after processing
			    	from("file://" + mainConfiguration.getInterestsFolder() + "?preMove=inprogress/&move=../done/&moveFailed=failed/").process(new Verbose());
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
