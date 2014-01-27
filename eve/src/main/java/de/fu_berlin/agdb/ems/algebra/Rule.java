/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Represents a rule of the algebra.
 * @author Ralf Oechsner
 *
 */
public class Rule {
	
	private Operator operator;
	
	public Rule(Operator operator) {
		this.operator = operator;
	}
	
	public void apply(IEvent event) {  
		
	}
}
