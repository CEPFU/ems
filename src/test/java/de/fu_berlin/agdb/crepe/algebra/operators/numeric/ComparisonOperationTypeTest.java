package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Optional;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Simon Kalt
 */
public class ComparisonOperationTypeTest {

    private final String ATTRIBUTE_NAME = "SomeAttribute";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Object mockObject1 = new Object();
    private Object mockObject2 = new Object();
    @Mock
    private IEvent mockEvent;
    @Mock
    private IEvent mockEvent2;
    @Mock
    private IAttribute mockAttribute;
    @Mock
    private IAttribute mockAttribute2;
    @Mock
    private Operator mockOperator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * {@link ComparisonOperationType#equalAttribute(String, IEvent, Object)}
     */
    @Test
    public void testEqualAttributeNullEvent() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        thrown.expectMessage("Null event.");

        equalAttribute(ATTRIBUTE_NAME, null, mockObject1);
    }

    /**
     * {@link ComparisonOperationType#equalAttribute(String, IEvent, Object)}
     */
    @Test
    public void testEqualAttributeNullAttribute() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        thrown.expectMessage("Attribute not found.");

        when(mockEvent.getAttributes()).thenReturn(new HashMap<>());

        equalAttribute(ATTRIBUTE_NAME, mockEvent, mockObject1);
    }

    /**
     * {@link ComparisonOperationType#equalAttribute(String, IEvent, Object)}
     */
    @Test
    public void testEqualAttribute_True() throws Exception {
        when(mockAttribute.getValue()).thenReturn(mockObject1);

        HashMap<String, IAttribute> attributes = new HashMap<>();
        attributes.put(ATTRIBUTE_NAME, mockAttribute);
        when(mockEvent.getAttributes()).thenReturn(attributes);

        assertTrue("Attribute value does not match expected object", equalAttribute(ATTRIBUTE_NAME, mockEvent, mockObject1));
    }

    /**
     * {@link ComparisonOperationType#equalAttribute(String, IEvent, Object)}
     */
    @Test
    public void testEqualAttribute_False() throws Exception {
        when(mockAttribute.getValue()).thenReturn(mockObject2);

        HashMap<String, IAttribute> attributes = new HashMap<>();
        attributes.put(ATTRIBUTE_NAME, mockAttribute);
        when(mockEvent.getAttributes()).thenReturn(attributes);

        assertFalse("Attribute should not match expected object", equalAttribute(ATTRIBUTE_NAME, mockEvent, mockObject1));
    }

    /**
     * {@link ComparisonOperationType#compare(Object, Object)}
     */
    @Test
    public void testCompareObjects_FirstIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        ComparisonOperationType.compare(null, mockObject2);
    }

    /**
     * {@link ComparisonOperationType#compare(Object, Object)}
     */
    @Test
    public void testCompareObjects_SecondIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        ComparisonOperationType.compare(mockObject1, null);
    }

    /**
     * {@link ComparisonOperationType#compare(Object, Object)}
     */
    @Test
    public void testCompareObjects_NotComparable() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        ComparisonOperationType.compare(mockObject1, mockObject2);
    }

    /**
     * {@link ComparisonOperationType#compare(Object, Object)}
     */
    @Test
    public void testCompareObjects() throws Exception {
        assertEquals(
                "Comparison of 5 and 7 should have same result as 5.compareTo(7).",
                ((Integer) 5).compareTo(7),
                ComparisonOperationType.compare(5, 7)
        );
    }

    /**
     * {@linkplain ComparisonOperationType#compare(String, IEvent, Object)}
     */
    @Test
    public void testCompareAttribute_Equals() throws Exception {
        when(mockAttribute.getValue()).thenReturn(mockObject1);

        HashMap<String, IAttribute> attributes = new HashMap<>();
        attributes.put(ATTRIBUTE_NAME, mockAttribute);
        when(mockEvent.getAttributes()).thenReturn(attributes);

        assertEquals(0, compare(ATTRIBUTE_NAME, mockEvent, mockObject1));
    }

    /**
     * {@linkplain ComparisonOperationType#compare(String, IEvent, Object)}
     */
    @Test
    public void testCompareAttribute_NotEquals() throws Exception {
        when(mockAttribute.getValue()).thenReturn(8);

        HashMap<String, IAttribute> attributes = new HashMap<>();
        attributes.put(ATTRIBUTE_NAME, mockAttribute);
        when(mockEvent.getAttributes()).thenReturn(attributes);

        // Otherwise this method does not work on the mock event
        when(mockEvent.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(8));

        assertEquals(Integer.compare(8, 4), compare(ATTRIBUTE_NAME, mockEvent, 4));
    }

    @Test
    public void testApplyToObject() throws Exception {
        int firstValue = 8;
        int secondValue = 4;

        when(mockAttribute.getValue()).thenReturn(firstValue);
        when(mockEvent.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, mockAttribute));

        // Otherwise this method does not work on the mock event
        when(mockEvent.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(firstValue));

        assertFalse(LESS.applyTo(ATTRIBUTE_NAME, mockEvent, secondValue));
        assertFalse(LESS_EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, secondValue));
        assertFalse(EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, secondValue));
        assertTrue(GREATER_EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, secondValue));
        assertTrue(GREATER.applyTo(ATTRIBUTE_NAME, mockEvent, secondValue));
    }

    @Test
    public void testApplyToEvent() throws Exception {
        int firstValue = 4;
        int secondValue = 17;
        when(mockAttribute.getValue()).thenReturn(firstValue);
        when(mockAttribute2.getValue()).thenReturn(secondValue);

        when(mockEvent.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, mockAttribute));
        when(mockEvent2.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, mockAttribute2));

        // Otherwise this method does not work on the mock event
        when(mockEvent.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(firstValue));
        when(mockEvent2.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(secondValue));

        assertTrue(LESS_EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockEvent2));
        assertFalse(EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockEvent2));
    }

    @Test
    public void testApplyToEvent_NullEvent() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, (IEvent) null);
    }

    @Test
    public void testApplyToEvent_NoSuchAttribute() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        when(mockEvent2.getAttributes()).thenReturn(emptyMap());
        EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockEvent2);
    }

    @Test
    public void testApplyToOperator_NullOperator() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, (Operator) null);
    }

    @Test
    public void testApplyToOperator_NullEventInOperator() throws Exception {
        thrown.expect(OperatorNotSupportedException.class);
        EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockOperator);
    }

    @Test
    public void testApplyToOperator() throws Exception {
        int firstValue = 125;
        int secondValue = 125;
        when(mockAttribute.getValue()).thenReturn(firstValue);
        when(mockAttribute2.getValue()).thenReturn(secondValue);

        when(mockEvent.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, mockAttribute));
        when(mockEvent2.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, mockAttribute2));

        // Otherwise this method does not work on the mock event
        when(mockEvent.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(firstValue));
        when(mockEvent2.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(secondValue));

        when(mockOperator.getMatchingEvent()).thenReturn(mockEvent2);

        assertTrue(LESS_EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockOperator));
        assertTrue(EQUAL.applyTo(ATTRIBUTE_NAME, mockEvent, mockOperator));
    }
}
