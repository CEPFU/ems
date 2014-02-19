/**
 * 
 */
package de.fu_berlin.agdb.ems.core;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ralf Oechsner
 *
 */
public class ProfileLoaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		
		ProfileLoader profileLoader = new ProfileLoader(null);
		profileLoader.setProfilesFolder("profiles");
		profileLoader.load("profile(and(attribute(\"Temperature\"), attribute(\"Humidity\")), new CompositeEventNotification(new Event(\"description\", \"SEND CEP\")));");
		
		assertTrue(true);
	}

}
