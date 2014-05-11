/**
 * 
 */
package de.fu_berlin.agdb.crepe.core;

import static de.fu_berlin.agdb.crepe.algebra.dsl.CoreBuilder.attribute;
import static de.fu_berlin.agdb.crepe.algebra.dsl.LogicOperatorBuilder.and;
import static de.fu_berlin.agdb.crepe.algebra.dsl.NotificationBuilder.blankNotification;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fu_berlin.agdb.crepe.algebra.Profile;
import de.fu_berlin.agdb.crepe.algebra.windows.EndlessWindow;


/**
 * Unit test for profile loader.
 * @author Ralf Oechsner
 *
 */
public class ProfileLoaderTest {

	private static final String testProfile = "profile(and(attribute(\"Temperature\"), attribute(\"Humidity\")),"
			+ " blankNotification());";
	private static Profile testResult = new Profile(and(attribute("Temperature"), attribute("Humidity")), new EndlessWindow(), blankNotification());
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		
		ProfileLoader profileLoader = new ProfileLoader(null, "profiles");
		List<Profile> profiles = profileLoader.load(testProfile);
		profiles.get(0).setWindow(new EndlessWindow());

		assertEquals("Compiled profile doesn't match.", testResult.toString(), profiles.get(0).toString());
	}

}
