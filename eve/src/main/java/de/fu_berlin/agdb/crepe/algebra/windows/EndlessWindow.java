/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.windows;

import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class EndlessWindow implements IWindow {

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#newInstance()
	 */
	@Override
	public IWindow newInstance() {
		
		return this; // special case: all endless windows can share the same object
		             // because the apply function only returns true anyway
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#apply(de.fu_berlin.agdb.ems.algebra.Operator)
	 */
	@Override
	public boolean apply() {

		// never reset a rule because of the window (which means no window at all)
		return true;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#onMatch()
	 */
	@Override
	public void onMatch(IEvent event) {
		
		// nothing to do for endless window
	}
}
