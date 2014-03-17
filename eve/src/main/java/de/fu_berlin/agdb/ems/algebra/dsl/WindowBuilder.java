/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.algebra.windows.CountWindow;
import de.fu_berlin.agdb.ems.algebra.windows.IWindow;

/**
 * DSL for windows.
 * @author Ralf Oechsner
 *
 */
public class WindowBuilder {

	/**
	 * Creates a count window which 
	 * @param n
	 * @return
	 */
	public static IWindow countWindow(long n) {
		
		return new CountWindow(n);
	}
}
