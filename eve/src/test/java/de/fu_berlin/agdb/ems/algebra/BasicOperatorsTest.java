/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.fu_berlin.agdb.ems.data.Attribute;
import de.fu_berlin.agdb.ems.data.Event;
import de.fu_berlin.agdb.ems.data.IAttribute;

/**
 * @author Ralf Oechsner
 *
 */
public class BasicOperatorsTest {

	private Event event1;
	private Event event2;
	private Event event3;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Map<String, IAttribute> attributes1 = new Hashtable<String, IAttribute>();
		attributes1.put("temperature", new Attribute(new Integer(25)));
		event1 = new Event(new Date(), attributes1);
		
        // ensure time difference between event1 and event2
		Thread.sleep(5);
		
		Map<String, IAttribute> attributes2 = new Hashtable<String, IAttribute>();
		attributes2.put("temperature", new Attribute(new Integer(10)));
		event2 = new Event(new Date(), attributes2);
		
		Map<String, IAttribute> attributes3 = new Hashtable<String, IAttribute>();
		attributes3.put("temperature", new Attribute(new Integer(10)));
		event3 = new Event(new Date(), attributes3);
	}

	@Test
	public void testEquals() {

		assertTrue("Equals operator failed.", BasicOperators.equals("temperature", event2, event3));
		assertFalse("Equals operator failed.", BasicOperators.equals("temperature", event1, event2));
	}
	
	@Test
	public void testLess() {

		try {
			assertTrue("Less operator failed.", BasicOperators.less("temperature", event2, event1));
			assertFalse("Less operator failed.", BasicOperators.less("temperature", event1, event2));
		} catch (OperatorNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGreater() {

		try {
			assertTrue("Greater operator failed.", BasicOperators.greater("temperature", event1, event2));
			assertFalse("Greater operator failed.", BasicOperators.greater("temperature", event2, event1));
		} catch (OperatorNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLessEqual() {

		try {
			assertTrue("LessEqual operator failed.", BasicOperators.lessEqual("temperature", event2, event1));
			assertTrue("LessEqual operator failed.", BasicOperators.lessEqual("temperature", event2, event3));
			assertFalse("LessEqual operator failed.", BasicOperators.lessEqual("temperature", event1, event2));
		} catch (OperatorNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGreaterEqual() {

		try {
			assertTrue("GreaterEqual operator failed.", BasicOperators.greaterEqual("temperature", event1, event2));
			assertTrue("GreaterEqual operator failed.", BasicOperators.greaterEqual("temperature", event2, event3));
			assertFalse("GreaterEqual operator failed.", BasicOperators.greaterEqual("temperature", event2, event1));
		} catch (OperatorNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBefore() {

		assertTrue("Before operator failed.", BasicOperators.before(event1, event2));
		assertFalse("Before operator failed.", BasicOperators.before(event2, event1));
	}
	
	@Test
	public void testAfter() {

		assertTrue("After operator failed.", BasicOperators.after(event2, event1));
		assertFalse("After operator failed.", BasicOperators.after(event1, event2));
	}
}
