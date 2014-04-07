/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.outputadapters.CSVOutputAdapter;
import de.fu_berlin.agdb.ems.outputadapters.IOutputAdapter;

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
}
