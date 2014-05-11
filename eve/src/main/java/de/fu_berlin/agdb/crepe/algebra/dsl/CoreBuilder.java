/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.algebra.operators.core.Attribute;
import de.fu_berlin.agdb.crepe.algebra.operators.core.Count;
import de.fu_berlin.agdb.crepe.algebra.operators.core.ForEvent;
import de.fu_berlin.agdb.crepe.algebra.windows.EndlessWindow;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * Basic operators.
 * @author Ralf Oechsner
 *
 */
public class CoreBuilder {

	/**
	 * True if an event has a given attribute.
	 * @param attribute attribute that is checked.
	 * @return true if event has attribute with that key, false else
	 */
	public static Match attribute(String attribute) {
		
		return new Attribute(attribute);
	}
	
	/**
	 * Creates an event with one attribute.
	 * @param attribute key of attribute
	 * @param attributeValue attribute value.
	 * @return event created event
	 */
	public static IEvent event(String attribute, Object attributeValue) {
		
		return new Event(attribute, attributeValue);
	}
	
	/**
	 * Evaluates the second operator only on the matches
	 * of the first operator.
	 * @param match matched operator
	 * @param op operator that is evaluated
	 * @return true if second operator matched for event of first operator
	 */
	public static Operator forEvent(Match match, Operator op) {
		
		return new ForEvent(match, op);
	}
	
	/**
	 * Creates a static match with a given event.
	 * @param event static value of match
	 * @return match operator with static matching event
	 */
	public static Match match(final IEvent event) {
		
		Match m = new Match() {
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				return true;
			}
			
			public String toString() {
				return "match(" + this.getMatchingEvent() + ")";
			}
		};

		m.setWindow(new EndlessWindow());
		m.setMatchingEvent(event);
		
		return m;
	}
	
	/**
	 * Sets the matching event of the first operator as matching event of the 
	 * second operator.
	 * @param match operator whose matching event is used
	 * @param to operator whose event is set
	 * @return operator that sets the matching event (apply returns same as of first operator)
	 */
	public static Operator setMatchTo(final Match match, final Match to) {
		
		Operator op = new Operator() {
			
			@Override
			public boolean apply(IEvent event) throws OperatorNotSupportedException {
				
				boolean opState = match.apply(event);
				if (opState) {
					to.setMatchingEvent(match.getMatchingEvent());
				}
				
				return opState;
			}
			
			@Override
			public void reset() {
				
				match.reset();
			}
			
			@Override
			public String toString() {
				
				return "setMatchTo(" + match + ", " + to + ")";
			}
		};
		op.setChildren(match);
		
		return op;
	}
	
	public static Match count(Operator op, long count) {
		
		return new Count(op, count);
	}
}
