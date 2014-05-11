/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import java.util.LinkedList;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Moving average operator.
 * @author Ralf Oechsner
 *
 */
public class MovingAverage extends Match {

	private String attribute;
	private String averageAttribute;
	private int windowSize;
	private LinkedList<Double> windowElements;
	
	/**
	 * Moving average operator.
	 * @param attribute attribute whose average is calculated
	 * @param averageAttribute name of average attribute for the matching event
	 * @param windowSize window size
	 */
	public MovingAverage(String attribute, String averageAttribute, int windowSize) {
		
		this.attribute = attribute;
		this.averageAttribute = averageAttribute;
		this.windowSize = windowSize;
		this.windowElements = new LinkedList<Double>();
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.crepe.algebra.Operator#apply(de.fu_berlin.agdb.crepe.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {

		IAttribute attr = event.getAttributes().get(attribute);
		if (attr == null) {
			throw new OperatorNotSupportedException("Attribute not found.");
		}
		
		Object a = attr.getValue();
		if (!(a instanceof Number)) {
			throw new OperatorNotSupportedException("Attribute value not a Number.");
		}
		Double currentValue = ((Number) a).doubleValue();
		
		if (this.windowElements.size() < this.windowSize) {
			this.windowElements.addLast(currentValue);
		}
		else {
			this.windowElements.removeFirst();
			this.windowElements.addLast(currentValue);
		}
		
		Double average = 0.0;
		for (Double curElement : this.windowElements) {
			average += curElement;
		}
		average /= windowSize;
		
		Event averageEvent = new Event(this.averageAttribute, average);
		averageEvent.setTimeStamp(event.getTimeStamp());
		this.setMatchingEvent(averageEvent);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "movingAverage(" + attribute + ", " + averageAttribute + ", "
				+ windowSize + ")";
	}
}
