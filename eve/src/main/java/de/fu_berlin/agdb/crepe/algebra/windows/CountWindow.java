/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.windows;

import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Simple rectangle window function. A rule matches in the window if all of it's
 * matching events are within the last n events.
 * 
 * @author Ralf Oechsner
 */
public class CountWindow implements IWindow {

	private long n;
	private long eventsSinceMatch = 0;
	private boolean hasMatched = false;

	/**
	 * Create a window that matches for the last n events.
	 * @param n amount of events the window matches for
	 */
	public CountWindow(long n) {
		
		this.n = n;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#newInstance()
	 */
	@Override
	public IWindow newInstance() {
		
		return new CountWindow(n);
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#apply()
	 */
	@Override
	public boolean apply() {

		if (this.hasMatched) {
			this.eventsSinceMatch++;
			
			if (this.eventsSinceMatch >= n) {
				this.hasMatched = false;
				return false;
			}
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#onMatch()
	 */
	@Override
	public void onMatch(IEvent event) {
		
		this.eventsSinceMatch = 0;
		this.hasMatched = true;
	}
}
