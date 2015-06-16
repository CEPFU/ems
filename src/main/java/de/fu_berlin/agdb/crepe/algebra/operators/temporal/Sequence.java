/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.temporal;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Sequence operator.
 * @author Ralf Oechsner
 *
 */
public class Sequence extends Operator {
	
	private int currentOp = 0;
	
	/**
	 * Sequence operator. Matches if all operators match in a row (non
	 * matching events mustn't be in between).
	 * @param operators sequence of operators
	 */
	public Sequence(Operator ... operators) {
		
		this.setChildren(operators);
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		if (!state) {
			if (children[currentOp].apply(event)) {
				if (currentOp == this.children.length - 1) {
					state = true;
				}
				else {
					currentOp++;
				}
			}
			else {
				currentOp = 0;
				if (children[0].apply(event)) {
					currentOp++;
				}
			}
		}

		return state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String s = this.children[0].toString();
		for (int i = 1; i < this.children.length; i++) {
			s += ", " + this.children[i];
		}
		
		return "sequence(" + s + ")";
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#reset()
	 */
	@Override
	public void reset() {
		
		this.state = false;
		this.currentOp = 0;
		for (Operator curChild : this.getChildren()) {
			curChild.reset();
		}
	}
}
