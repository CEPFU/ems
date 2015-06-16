/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.windows;

import de.fu_berlin.agdb.crepe.data.IEvent;


/**
 * Interface for all windows.
 * @author Ralf Oechsner
 *
 */
public interface IWindow {

	/**
	 * Creates a window in a initial state.
	 * @return window in initial state
	 */
	public IWindow newInstance();
	
	/**
	 * Applies windowing function
	 * @return true if event is within window, false else
	 */
	public boolean apply();
	
	/**
	 * Is called when an operator matches an event.
	 * @param event matching event
	 */
	public void onMatch(IEvent event);
}
