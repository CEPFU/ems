package de.fu_berlin.agdb.eve.data;

import java.util.Date;
import java.util.Map;

/**
 * Interface for generic events (used for algebra).
 * @author Ralf Oechsner
 *
 */
public interface IEvent {

	/**
	 * Timestamp of event.
	 * @return timestamp of event.
	 */
	public Date getTimeStamp();
	
	/**
	 * Map that contains all attributes of an event. Every attribute has a name
	 * that is used as a key in the map.
	 * @return all attributes of the event
	 */
	public Map<String, IAttribute> getAttributes();
}
