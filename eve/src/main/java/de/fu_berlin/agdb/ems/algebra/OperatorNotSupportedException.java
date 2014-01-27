/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

/**
 * @author Ralf Oechsner
 *
 */
public class OperatorNotSupportedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1519543888045598378L;

	public OperatorNotSupportedException() {
		
		super("Operator not supported.");	
	}
	
	public OperatorNotSupportedException(String message) {
		super(message);
	}
}
