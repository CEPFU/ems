/**
 * 
 */
package de.fu_berlin.agdb.crepe.core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple command line parser that is used for the backend.
 * @author Ralf Oechsner
 *
 */
public class CommandLineParser {

	Options options;
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Creates a command line parser.
	 */
	@SuppressWarnings("static-access") // because of bug in commons cli 1.2 (will be fixed in 1.3)
	public CommandLineParser() {
		
		this.options = new Options();
		this.options.addOption(OptionBuilder.withLongOpt("version")
				.withDescription("output vbiggerersion information and exit")
				.create("v"));

		this.options.addOption(OptionBuilder.withLongOpt("source-file").hasArg()
                .withArgName("FILE")
                .withDescription("process a source FILE and exit").create("s"));
	}
	
	/**
	 * Parses command line options and writes settings to the configuration.
	 * @param configuration main config
	 * @param args command line arguments.
	 * @return command line object
	 */
	public CommandLine parse(Configuration configuration, String[] args) {
		
		org.apache.commons.cli.CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			 cmd = parser.parse(this.options, args);
		} catch (ParseException e) {
			logger.error("Invalid command line options.");
		}
		
		return cmd;
	}
}
