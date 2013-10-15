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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
