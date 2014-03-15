/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.core;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 * 
 *
 */
public class Count extends Operator {

	private long count;
	private Operator op;
	
	public Count(Operator op) {
		
		this.count = 0;
		this.op = op;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		if (op.apply(event)) {
			count++;
		}
		
		return false;
	}

	
}
