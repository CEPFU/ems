/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.windows;

import java.util.Date;

import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Simple sliding window based on a duration. Matches if all events' time stamps are
 * within a certain time frame.
 * @author Ralf Oechsner
 *
 */
public class TimeWindow implements IWindow {

	private long duration;
	private Date lastMatch = null;
	private boolean state = true;
	
	/**
	 * Create a window that matches for all events that have their time stamp
	 * within a given amount of milliseconds.
	 * @param duration amount milliseconds that the window contains
	 */
	public TimeWindow(long duration) {
		
		this.duration = duration;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#newInstance()
	 */
	@Override
	public IWindow newInstance() {

		return new TimeWindow(duration);
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#apply()
	 */
	@Override
	public boolean apply() {

		return this.state;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.windows.IWindow#onMatch(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public void onMatch(IEvent event) {

		if (this.lastMatch != null) {
			this.state = (event.getTimeStamp().getTime() - this.lastMatch.getTime()) >= this.duration;
		}
		
		this.lastMatch = event.getTimeStamp();
		
	}

}
