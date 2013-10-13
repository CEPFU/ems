/**
 * 
 */
package de.fu_berlin.agdb.eve.data;

import java.util.Date;
import java.util.Map;

/**
 * Event structure for RSS feeds.
 * @author Ralf Oechsner
 *
 */
public class Event implements IEvent {

	private Date timeStamp;
	private Map<String, IAttribute> attributes;
	
	/**
	 * Empty event. Time stamp and at least one Attribute has to be set later!
	 */
	public Event() {
		
	}
	
	/**
	 * Create RSSEvent with timestamp and attributes.
	 * @param timestamp timestamp of event.
	 * @param attributes attribute map of event.
	 */
	public Event(Date timestamp, Map<String, IAttribute> attributes) {
		
		this.timeStamp = timestamp;
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.eve.data.IEvent#getTimeStamp()
	 */
	public Date getTimeStamp() {

		return this.timeStamp;
	}
	
	/**
	 * Setter for timestamp.
	 * @param timeStamp timestamp.
	 */
	public void setTimeStamp(Date timeStamp) {
		
		this.timeStamp = timeStamp;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.eve.data.IEvent#getAttributes()
	 */
	public Map<String, IAttribute> getAttributes() {

		return attributes;
	}
	
	/**
	 * Setter for attribute map.
	 * @param attributes attributes.
	 */
	public void setAttributes(Map<String, IAttribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * Returns string with time stamp and every attribute on a new line.
	 * Every line has the form: Key: Value + newline.
	 */
	public String toString() {
		
		String nl = System.getProperty("line.separator");
		String s = "Timestamp: " + this.timeStamp + nl;
		
		for (Map.Entry<String, IAttribute> entry : this.attributes.entrySet()) {
			
			s += entry.getKey() + ": " + entry.getValue() + nl;
		}
		
		return s;
	}
}
