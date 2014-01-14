package de.fu_berlin.agdb.ems.importer;

import java.util.List;

import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Interface for generic importer.
 * @author Ralf Oechsner
 *
 */
public interface IImporter {

	/**
	 * Loads source text and generates events from it.
	 * @param text source text.
	 */
	public void load(String text);
	
	/**
	 * List of imported events.
	 * @return imported events.
	 */
	public List<IEvent> getEvents();
}
