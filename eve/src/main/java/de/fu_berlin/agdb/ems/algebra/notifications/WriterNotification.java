/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.notifications;

import de.fu_berlin.agdb.ems.algebra.Algebra;
import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.data.IEvent;
import de.fu_berlin.agdb.ems.outputadapters.IOutputAdapter;
import de.fu_berlin.agdb.ems.writers.IWriter;

/**
 * @author Ralf Oechsner
 *
 */
public class WriterNotification implements Notification {

	private Operator rule;
	private IWriter writer;
	private IOutputAdapter outputAdapter;
	private Match[] matches;
	
	public WriterNotification(IWriter writer, IOutputAdapter outputAdapter, Match[] matches) {
		
		this.writer = writer;
		this.outputAdapter = outputAdapter;
		this.matches = matches;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#apply()
	 */
	@Override
	public void apply() {

		// get currently matched events
		IEvent[] events = new IEvent[this.matches.length];
		for (int i = 0; i < this.matches.length; i++) {
			events[i] = this.matches[i].getMatchingEvent();
		}
		
		this.outputAdapter.load(events);
		this.writer.setText(this.outputAdapter.getOutput());
		this.writer.write();
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#setRule(de.fu_berlin.agdb.ems.algebra.Operator)
	 */
	@Override
	public void setRule(Operator rule) {

		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#getRule()
	 */
	@Override
	public Operator getRule() {

		return this.rule;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.notifications.Notification#setAlgebra(de.fu_berlin.agdb.ems.algebra.Algebra)
	 */
	@Override
	public void setAlgebra(Algebra algebra) {

		// not needed here
	}

}
