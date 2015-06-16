/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.algebra.windows.CountWindow;
import de.fu_berlin.agdb.crepe.algebra.windows.IWindow;
import de.fu_berlin.agdb.crepe.algebra.windows.TimeWindow;

/**
 * DSL for windows.
 * @author Ralf Oechsner
 *
 */
public class WindowBuilder {

	/**
	 * Creates a count window which matches if all matches of a rule happened within the
	 * last n events.
	 * @param n window size
	 * @return count window
	 */
	public static IWindow countWindow(long n) {
		
		return new CountWindow(n);
	}
	
	/**
	 * Creates a time window which matches if all matches of a rule happened within a
	 * certain time.
	 * @param duration window size in milliseconds
	 * @return time window
	 */
	public static IWindow timeWindow(long duration) {
		
		return new TimeWindow(duration);
	}
}
