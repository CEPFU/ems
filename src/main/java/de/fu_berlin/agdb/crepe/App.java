package de.fu_berlin.agdb.crepe;

import java.io.File;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.core.CommandLineParser;
import de.fu_berlin.agdb.crepe.core.Configuration;
import de.fu_berlin.agdb.crepe.core.SourceHandler;
import de.fu_berlin.agdb.crepe.core.ProfileLoader;
import de.fu_berlin.agdb.crepe.core.SourceParser;

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
    	
    	CommandLineParser commandLineParser = new CommandLineParser();
    	final CommandLine cmd = commandLineParser.parse(mainConfiguration, args);
    	
    	if (cmd.hasOption("version")) {
    		System.out.println("Version: " + App.class.getPackage().getImplementationVersion());
    		return;
    	}
    	
		// create camel context for internal message routing
		CamelContext camelContext = new DefaultCamelContext();
		if (mainConfiguration.getUseMessagingServer()) {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
			camelContext.addComponent("crepe-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		}

		logger.info("Looking for interest files in: " + mainConfiguration.getSourcesFolder());
		try {
			
			final Algebra algebra = new Algebra(camelContext);
			
			final ProfileLoader profileLoader = new ProfileLoader(algebra, mainConfiguration.getProfilesFolder());
			
			final SourceParser sourceParser; 
			if (mainConfiguration.getLoaderPath() != null && mainConfiguration.getInputAdapterPath() != null) {
				logger.info("Loader path: " + mainConfiguration.getLoaderPath());
				logger.info("Input adapter path: " + mainConfiguration.getInputAdapterPath());
				sourceParser = new SourceParser(mainConfiguration.getLoaderPath(), mainConfiguration.getInputAdapterPath());
			}
			else {
				sourceParser = new SourceParser();
			}
			
			if (cmd.hasOption("benchmarking")) {
				sourceParser.setBenchmarking(true);
				System.out.println("Benchmarking activated."); // no logger is used because it usually is turned off for benchmarking
			}
			
			// this route loads source files from disk and process them via the SourceParser
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					if (cmd.getOptionValue("source-file") != null) {
						logger.info("Loading source file: " + cmd.getOptionValue("source-file"));
						File file = new File(cmd.getOptionValue("source-file"));
						System.out.println("file://" + file.getParent() + "?fileName=" + file.getName()
										+ "&noop=true");
						from(
								"file://" + file.getParent() + "?fileName=" + file.getName()
										+ "&noop=true").process(sourceParser);
					}
					else {
						// source files are moved to "inprogress" during processing and to "done" after processing
						from(
								"file://"
										+ mainConfiguration.getSourcesFolder()
										+ "?preMove=inprogress/&move=../done/&moveFailed=failed/")
								.process(sourceParser);
					}
				}
			});
			
			// this route processes all events from the LoaderHandler queue (filled by the Loaders)
			camelContext.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from(SourceHandler.LOADER_HANDLER_QUEUE_URI).to(Algebra.EVENT_QUEUE_URI);
				}
			});
			
			// this route processes all events of the main event queue with the algebra system
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
			    	if (!mainConfiguration.getUseMessagingServer()) {
			    		from(Algebra.EVENT_QUEUE_URI).process(algebra);
			    	}
			    	else {
			    		logger.info("Using messaging server.");
			    		from(Algebra.EVENT_QUEUE_URI).to("crepe-jms:queue:main.queue").process(algebra);
			    	}
			    }
			});
			
			// this route loads profile files from disk and process them via the ProfileLoader
			camelContext.addRoutes(new RouteBuilder() {
			    public void configure() {
					from(
							"file://"
									+ mainConfiguration.getProfilesFolder()
									+ "?noop=true")
							.process(profileLoader);
			    }
			});
			
			// start context and run indefinitely
			camelContext.start();
			
			Object obj = new Object();
			synchronized (obj) {
				obj.wait();
			}
		} catch (Exception e) {
			logger.error(e);
		}		
    }
}
