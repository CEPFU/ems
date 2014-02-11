/**
 * 
 */
package de.fu_berlin.agdb.ems.inputadapters;

import java.util.List;

import org.apache.camel.Body;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public interface ISplitter {
	
	public List<IEvent> splitMessage( String header, @Body String body);

}
