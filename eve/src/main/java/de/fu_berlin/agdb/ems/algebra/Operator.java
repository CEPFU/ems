/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public interface Operator {

	public boolean apply(IEvent event) throws OperatorNotSupportedException;
}
