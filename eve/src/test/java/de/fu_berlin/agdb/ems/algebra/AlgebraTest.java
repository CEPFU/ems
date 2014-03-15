/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.fu_berlin.agdb.ems.App;

/**
 * @author Ralf Oechsner
 *
 */
public class AlgebraTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		App.main(new String[] { "--source-file=testData/source_weather.properties" });
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
