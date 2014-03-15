/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.operators.numeric;

import de.fu_berlin.agdb.ems.algebra.Match;
import de.fu_berlin.agdb.ems.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Equal operator.
 * @author Ralf Oechsner
 *
 */
public class Equal extends Match {
	
	private boolean state = false;
	private String attribute;
	private Object b;
	
	public Equal(String attribute, IEvent b) {
		
		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		if (b != null && b.getAttributes() != null && b.getAttributes().get(attribute) != null) {
			this.b = b.getAttributes().get(attribute).getValue();
		}
	}
	
	public Equal(String attribute, Object b) {
		
		this.lastMatchingEvent = new IEvent[1];
		this.attribute = attribute;
		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.algebra.Operator#apply(de.fu_berlin.agdb.ems.data.IEvent)
	 */
	@Override
	public boolean apply(IEvent event) throws OperatorNotSupportedException {
		
		boolean comparison = NumericUtil.equalAttribute(attribute, event, b);
		
		if (comparison) {
			this.state = true;
			this.lastMatchingEvent[0] = event;
		}
			
		return state;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "equal(" + attribute + ", " + b + ")";
	}
}