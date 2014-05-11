/**
 * 
 */
package de.fu_berlin.agdb.crepe.algebra;

import static de.fu_berlin.agdb.crepe.algebra.dsl.CoreBuilder.attribute;
import static de.fu_berlin.agdb.crepe.algebra.dsl.LogicOperatorBuilder.and;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ralf Oechsner
 *
 */
public class DSLTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		and(attribute("Temperature"), attribute("Humidity"));
		
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
