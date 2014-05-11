/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.notifications;

import java.util.Arrays;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.data.IEvent;
import de.fu_berlin.agdb.crepe.outputadapters.IOutputAdapter;
import de.fu_berlin.agdb.crepe.writers.IWriter;

/**
 * Writer notification.
 * @author Ralf Oechsner
 *
 */
public class WriterNotification implements Notification {

	private Operator rule;
	private IWriter writer;
	private IOutputAdapter outputAdapter;
	private Match[] matches;
	
	/**
	 * Creates a notification that sends matches to a writer. The messages are
	 * transformed by a specified output adapter before they written.
	 * @param writer writer where the events of the matches are sent
	 * @param outputAdapter output adapter that transforms the events to output format
	 * @param matches matches whose events are sent
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "writerNotification(" + writer
				+ ", " + outputAdapter + ", " + Arrays.toString(matches) + ")";
	}

}
