/**
 * 
 */
package de.fu_berlin.agdb.ems.outputadapters;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Interface for output adapters.
 * @author Ralf Oechsner
 *
 */
public interface IOutputAdapter {

	/**
	 * Loads events and generates output text.
	 * @param list of events to transform
	 */
	public void load(IEvent[] events);
	
	/**
	 * Output text
	 * @return output text.
	 */
	public String getOutput();
}
