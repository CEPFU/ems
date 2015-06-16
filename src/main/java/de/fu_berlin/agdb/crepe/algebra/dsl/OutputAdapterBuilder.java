/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.outputadapters.CSVOutputAdapter;
import de.fu_berlin.agdb.crepe.outputadapters.IOutputAdapter;
import de.fu_berlin.agdb.crepe.outputadapters.StringOutputAdapter;

/**
 * DSL for output adapters.
 * @author Ralf Oechsner
 *
 */
public class OutputAdapterBuilder {

	/**
	 * Creates a CSVOutputAdapter.
	 * @param timeStampCaption caption of time stamp column
	 * @param timeStampFormat format of time stamp
	 * @param delimiter delimiter
	 * @return CSVOutputAdapter
	 */
	public static IOutputAdapter CSVOutputAdapter(String timeStampCaption, String timeStampFormat, String delimiter) {
		
		return new CSVOutputAdapter(timeStampCaption, timeStampFormat, delimiter);
	}
	
	/**
	 * Creates a CSVOutputAdapter.
	 * @param timeStampCaption caption of time stamp column
	 * @param timeStampFormat format of time stamp
	 * @param delimiter delimiter
	 * @param captions column captions (first line)
	 * @return CSVOutputAdapter
	 */
	public static IOutputAdapter CSVOutputAdapter(String timeStampCaption, String timeStampFormat, String delimiter, String ... captions) {
		
		return new CSVOutputAdapter(timeStampCaption, timeStampFormat, delimiter, captions);
	}
	
	/**
	 * Creates an output adapter for formatted strings.
	 * @param format format string
	 * @param attributes attribute names (use "Timestamp" to get the time stamp)
	 */
	public static IOutputAdapter stringOutputAdapter(String format, String ... attributes) {
		
		return new StringOutputAdapter(format, attributes);
	}
}
