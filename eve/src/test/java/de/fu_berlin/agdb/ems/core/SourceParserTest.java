/**
 * 
 */
package de.fu_berlin.agdb.ems.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ralf Oechsner
 *
 */
public class SourceParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		String properties = "loader = URLLoader\n"
				+ "loader.url = http://www.gnu.org/licenses/gpl-3.0-standalone.html\n"
				+ "importer = CSVInputAdapter\n"
				+ "importer.separator = ;\n";
		
		SourceParser parser = new SourceParser();
		parser.parse(properties);
		
		assertTrue(true);
	}

}
