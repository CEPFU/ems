package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.windows.IWindow;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.EQUAL;
import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.LESS;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Simon Kalt
 */
@RunWith(MockitoJUnitRunner.class)
public class ComparisonOperationTest {

    private final Object mockObject = new Object();
    private final String ATTRIBUTE_NAME = "SomeAttribute";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private Operator mockOperator;
    @Mock
    private IEvent mockEvent;
    @Mock
    private IEvent mockEvent2;
    @Mock
    private IAttribute mockAttribute;
    @Mock
    private IAttribute mockAttribute2;

    @Test
    public void testConstructor_NullOperation() throws Exception {
        thrown.expect(NullPointerException.class);
        new ComparisonOperation(null, ATTRIBUTE_NAME, mockObject);
    }

    @Test
    public void testConstructor_NullAttribute() throws Exception {
        thrown.expect(NullPointerException.class);
        new ComparisonOperation(EQUAL, null, mockObject);
    }

    @Test
    public void testIEventConstructor_NullEvent() throws Exception {
        thrown.expect(NullPointerException.class);
        new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, (IEvent) null);
    }

    @Test
    public void testOperatorConstructor_NullOperator() throws Exception {
        thrown.expect(NullPointerException.class);
        new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, (Operator) null);
    }

    @Test
    public void testObjectConstructor_NullObject() throws Exception {
        thrown.expect(NullPointerException.class);
        new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, (Object) null);
    }

    @Test
    public void testToString() throws Exception {
        ComparisonOperation comparisonOperation = new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, mockObject);

        assertEquals(
                "EQUAL(" + ATTRIBUTE_NAME + ", " + mockObject + ")",
                comparisonOperation.toString()
        );
    }

    @Test
    public void testApplyObject() throws Exception {
        ComparisonOperation comparisonOperation;

        int firstValue = 7;
        int secondValue = 10;

        prepareMockAttribute(firstValue, mockAttribute, mockEvent);

        comparisonOperation = new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, secondValue);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertFalse(comparisonOperation.apply(mockEvent));

        comparisonOperation = new ComparisonOperation(LESS, ATTRIBUTE_NAME, secondValue);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertTrue(comparisonOperation.apply(mockEvent));
    }

    @Test
    public void testApplyEvent() throws Exception {
        ComparisonOperation comparisonOperation;

        int firstValue = 7;
        int secondValue = 10;

        prepareMockAttribute(firstValue, mockAttribute, mockEvent);
        prepareMockAttribute(secondValue, mockAttribute2, mockEvent2);

        comparisonOperation = new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, mockEvent2);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertFalse(comparisonOperation.apply(mockEvent));

        comparisonOperation = new ComparisonOperation(LESS, ATTRIBUTE_NAME, mockEvent2);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertTrue(comparisonOperation.apply(mockEvent));
    }

    @Test
    public void testApplyOperator() throws Exception {
        ComparisonOperation comparisonOperation;

        int firstValue = 7;
        int secondValue = 10;

        prepareMockAttribute(firstValue, mockAttribute, mockEvent);
        prepareMockAttribute(secondValue, mockAttribute2, mockEvent2);
        when(mockOperator.getMatchingEvent()).thenReturn(mockEvent2);

        comparisonOperation = new ComparisonOperation(EQUAL, ATTRIBUTE_NAME, mockOperator);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertFalse(comparisonOperation.apply(mockEvent));

        comparisonOperation = new ComparisonOperation(LESS, ATTRIBUTE_NAME, mockOperator);
        comparisonOperation.setWindow(mock(IWindow.class));
        assertTrue(comparisonOperation.apply(mockEvent));
    }

    private void prepareMockAttribute(Object value, IAttribute attribute, IEvent event) {
        when(attribute.getValue()).thenReturn(value);
        when(event.getAttributes()).thenReturn(singletonMap(ATTRIBUTE_NAME, attribute));
        when(event.getAttributeValue(ATTRIBUTE_NAME)).thenReturn(Optional.of(value));
    }
}
