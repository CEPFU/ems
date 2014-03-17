/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.windows;

import de.fu_berlin.agdb.ems.data.IEvent;


/**
 * Interface for all windows.
 * @author Ralf Oechsner
 *
 */
public interface IWindow {

	public IWindow newInstance();
	
	public boolean apply();
	
	public void onMatch(IEvent[] events);
}
