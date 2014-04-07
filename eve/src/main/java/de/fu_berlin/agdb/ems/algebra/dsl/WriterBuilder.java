/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import de.fu_berlin.agdb.ems.writers.ConsoleWriter;
import de.fu_berlin.agdb.ems.writers.FileWriter;
import de.fu_berlin.agdb.ems.writers.IWriter;

/**
 * DSL for writers.
 * @author Ralf Oechsner
 *
 */
public class WriterBuilder {

	/**
	 * Creates a file writer. Uses UTF-8 encoding.
	 * @param path path where the file is written
	 * @return file writer
	 */
	public static IWriter fileWriter(String path) {
		
		return new FileWriter(path);
	}
	
	/**
	 * Creates a file writer.
	 * @param path path where the file is written
	 * @param encoded encoding of the (text) file (e.g. utf-8)
	 * @return file writer
	 */
	public static IWriter fileWriter(String path, String encoded) {
		
		return new FileWriter(path, encoded);
	}
	
	/**
	 * Creates a console writer.
	 * @return console writer
	 */
	public static IWriter consoleWriter() {
		
		return new ConsoleWriter();
	}
}
