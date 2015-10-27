package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.algebra.windows.IWindow;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Simon Kalt
 */
public class NumericOperationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private Operator mockOperator1;
    @Mock
    private Operator mockOperator2;
    @Mock
    private Object mockObject1;
    @Mock
    private Object mockObject2;
    @Mock
    private IEvent mockEvent1;
    @Mock
    private IEvent mockEvent2;
    @Mock
    private IAttribute mockAttribute1;
    @Mock
    private IAttribute mockAttribute2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test structural equality on creation with (Operator, Operator).
     */
    @Test
    public void testEqualsOperators() throws Exception {
        NumericOperation op1 = new NumericOperation(ADD, "MyAttribute", mockOperator1, mockOperator2);
        NumericOperation op2 = new NumericOperation(ADD, "MyAttribute", mockOperator1, mockOperator2);
        assertEquals("Same structured operators should be equal.", op1, op2);
    }

    /**
     * Test structural equality on creation with (Operator, Object).
     */
    @Test
    public void testEqualsOperatorObject() throws Exception {
        NumericOperation op1 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockObject1);
        NumericOperation op2 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockObject1);
        assertEquals("Same structured operators should be equal.", op1, op2);
    }

    /**
     * Test structural equality on creation with (Object, Operator).
     */
    @Test
    public void testEqualsObjectOperator() throws Exception {
        NumericOperation op1 = new NumericOperation(DIVIDE, "MyAttribute", mockObject1, mockOperator2);
        NumericOperation op2 = new NumericOperation(DIVIDE, "MyAttribute", mockObject1, mockOperator2);
        assertEquals("Same structured operators should be equal.", op1, op2);
    }

    /**
     * Test structural equality on creation with (Object, Object).
     */
    @Test
    public void testEqualsObjects() throws Exception {
        NumericOperation op1 = new NumericOperation(SUBTRACT, "MyAttribute", mockObject1, mockObject2);
        NumericOperation op2 = new NumericOperation(SUBTRACT, "MyAttribute", mockObject1, mockObject2);
        assertEquals("Same structured operators should be equal.", op1, op2);
    }

    /**
     * Test structural inequality with switched operators.
     */
    @Test
    public void testNotEqualsStructure() throws Exception {
        NumericOperation op1 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockObject1);
        NumericOperation op2 = new NumericOperation(MULTIPLY, "MyAttribute", mockObject1, mockOperator1);
        assertNotEquals("Differently structured operators should not be equal.", op1, op2);
    }

    /**
     * Test structural inequality with different operators.
     */
    @Test
    public void testNotEqualsOperatorTypes() throws Exception {
        NumericOperation op1 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockOperator2);
        NumericOperation op2 = new NumericOperation(MULTIPLY, "MyAttribute", mockObject1, mockOperator2);
        assertNotEquals("Operation on differently typed operators should not be equal.", op1, op2);
    }

    /**
     * Test inequality of operators based on type.
     */
    @Test
    public void testNotEqualsType() throws Exception {
        NumericOperation op1 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockObject2);
        NumericOperation op2 = new NumericOperation(DIVIDE, "MyAttribute", mockOperator1, mockObject2);
        assertNotEquals("Differently typed operators should not be equal.", op1, op2);
    }

    /**
     * Test inequality of operators based on attribute.
     */
    @Test
    public void testNotEqualsAttribute() throws Exception {
        NumericOperation op1 = new NumericOperation(MULTIPLY, "MyAttribute", mockOperator1, mockObject2);
        NumericOperation op2 = new NumericOperation(MULTIPLY, "MyOtherAttribute", mockOperator1, mockObject2);
        assertNotEquals("Operations on different attributes should not be equal.", op1, op2);
    }

    /**
     * Test exception on null reference on first operand.
     */
    @Test
    public void testNullFirstOperand() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new NumericOperation(ADD, "Attribute", null, mockOperator2);
    }

    /**
     * Test exception on null reference on first operand.
     */
    @Test
    public void testNullSecondOperand() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new NumericOperation(ADD, "Attribute", mockOperator1, null);
    }

    /**
     * Test exception on null reference on operation type.
     */
    @Test
    public void testNullType() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new NumericOperation(null, "Attribute", mockObject1, mockOperator2);
    }

    /**
     * Test exception on null reference on attribute.
     */
    @Test
    public void testNullAttribute() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new NumericOperation(ADD, null, mockOperator1, mockOperator2);
    }

    public void testGetAttributeValue_NullEvent() throws OperatorNotSupportedException {
        thrown.expect(OperatorNotSupportedException.class);
        thrown.expectMessage("Matching event is null.");
        NumericOperation.getAttributeValue(mockOperator1, "SomeAttribute");
    }

    public void testGetAttributeValue_AttributeNotFound() throws OperatorNotSupportedException {
        thrown.expect(OperatorNotSupportedException.class);
        thrown.expectMessage("Attribute not found");

        when(mockOperator1.getMatchingEvent()).thenReturn(mockEvent1);
        NumericOperation.getAttributeValue(mockOperator1, "SomeAttribute");
    }

    /**
     * Test apply() on two operators.
     */
    @Test
    public void testApplyOperators() throws Exception {
        HashMap<String, IAttribute> attributes;
        String attribute = "MyAttribute1";
        int firstValue = 123124;
        double secondValue = 1325812D;
        Double expected = firstValue / secondValue;

        // Add mock events to operators
        when(mockOperator1.getMatchingEvent()).thenReturn(mockEvent1);
        when(mockOperator2.getMatchingEvent()).thenReturn(mockEvent2);


        // Add attribute to first event
        attributes = new HashMap<>(1);
        attributes.put(attribute, mockAttribute1);
        when(mockEvent1.getAttributes()).thenReturn(attributes);
        when(mockAttribute1.getValue()).thenReturn(firstValue);

        // Add attribute to second event
        attributes = new HashMap<>(1);
        attributes.put(attribute, mockAttribute2);
        when(mockEvent2.getAttributes()).thenReturn(attributes);
        when(mockAttribute2.getValue()).thenReturn(secondValue);

        NumericOperation operation = NumericOperation.divide(attribute, mockOperator1, mockOperator2);
        // Add mock window
        operation.setWindow(mock(IWindow.class));

        operation.apply(null);

        assertEquals(
                "After apply(), matching event of operation should have result of calculation.",
                expected,
                operation.getMatchingEvent().getAttributes().get(attribute).getValue()
        );
    }
}
