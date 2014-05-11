/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.notifications;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Operator;

/**
 * Notification for benchmarks. Simply prints current time milliseconds.
 * @author Ralf Oechsner
 *
 */
public class TimeMeasureNotification implements Notification {

	private Operator rule;
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.notifications.Notification#apply()
	 */
	@Override
	public void apply() {
		// TODO Auto-generated method stub
		System.out.println("Benchmark end: " + System.currentTimeMillis());
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.notifications.Notification#setRule(de.fu_berlin.agdb.crepe.algebra.Operator)
	 */
	@Override
	public void setRule(Operator rule) {

		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.notifications.Notification#getRule()
	 */
	@Override
	public Operator getRule() {

		return this.rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.notifications.Notification#setAlgebra(de.fu_berlin.agdb.crepe.algebra.Algebra)
	 */
	@Override
	public void setAlgebra(Algebra algebra) {

		// not needed here
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "timeMeasureNotification()";
	}

}
