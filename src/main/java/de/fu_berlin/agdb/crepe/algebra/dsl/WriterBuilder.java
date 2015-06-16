/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra.dsl;

import de.fu_berlin.agdb.crepe.writers.ConsoleWriter;
import de.fu_berlin.agdb.crepe.writers.FileWriter;
import de.fu_berlin.agdb.crepe.writers.IWriter;

/**
 * DSL for writers.
 * @author Ralf Oechsner
 *
 */
public class WriterBuilder {

	/**
	 * Creates a file writer. If file exists the new text is appended.
	 * @param path path where the file is written
	 * @return file writer
	 */
	public static IWriter fileWriter(String path) {
		
		return new FileWriter(path);
	}
	
	/**
	 * Creates a file writer.
	 * @param path path where the file is written
	 * @param append if true the new text is append, else overwritten
	 * @return file writer
	 */
	public static IWriter fileWriter(String path, boolean append) {
		
		return new FileWriter(path, append);
	}
	
	/**
	 * Creates a console writer.
	 * @return console writer
	 */
	public static IWriter consoleWriter() {
		
		return new ConsoleWriter();
	}
}
