package de.fu_berlin.agdb.eve.importer;

import java.util.List;

import de.fu_berlin.agdb.eve.data.IEvent;

/**
 * Interface for generic importer.
 * @author Ralf Oechsner
 *
 */
public interface IImporter {

	/**
	 * List of imported events.
	 * @return imported events.
	 */
	public List<IEvent> getEvents();
}
