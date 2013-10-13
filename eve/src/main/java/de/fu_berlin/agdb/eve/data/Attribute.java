package de.fu_berlin.agdb.eve.data;

/**
 * Generic attribute.
 * @author Ralf Oechsner
 *
 */
public class Attribute implements IAttribute {

	private Object value;
	
	public Attribute(Object value) {
		
		this.value = value;
	}
	
	public Object getValue() {
		
		return value;
	}

	public void setValue(Object value) {
		
		this.value = value;
	}
	
	public String toString() {
		
		return this.value.toString();
	}
}
