/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra;

/**
 * Exception that is used for ignoring operators.
 * @author Ralf Oechsner
 *
 */
public class OperatorNotSupportedException extends Exception {

	/**
	 * OperatorNotSupporedException UID
	 */
	private static final long serialVersionUID = -1519543888045598378L;

	/**
	 * Exception for operators that don't work on the objects they are applied on.
	 */
	public OperatorNotSupportedException() {
		
		super("Operator not supported.");	
	}
	
	/**
	 * Exception for operators that don't work on the objects they are applied on, with
	 * the possibility to give a more detailed reason why the operator can't be applied.
	 * @param message 
	 */
	public OperatorNotSupportedException(String message) {
		super(message);
	}
}
