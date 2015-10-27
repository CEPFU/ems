package de.fu_berlin.agdb.crepe.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

/**
 * Test for algebra boolean operators.
 *
 * @author Simon Kalt
 */
public class BinaryOpTest {

    @Mock
    private Operator mockOperator;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test structural equality of operators.
     */
    @Test
    public void testEquals() throws Exception {
        BinaryOp op1 = new BinaryOp(AND, emptyList());
        BinaryOp op2 = new BinaryOp(AND, emptyList());
        assertEquals("Same structured operators should be equal.", op1, op2);
    }

    /**
     * Test structural inequality of operators.
     */
    @Test
    public void testNotEqualsStructure() throws Exception {
        BinaryOp op1 = new BinaryOp(OR, singletonList(mockOperator));
        BinaryOp op2 = new BinaryOp(OR, asList(mockOperator, mockOperator));
        assertNotEquals("Differently structured operators should not be equal.", op1, op2);
    }

    /**
     * Test inequality of operators based on type.
     */
    @Test
    public void testNotEqualsType() throws Exception {
        BinaryOp op1;
        BinaryOp op2;
        op1 = new BinaryOp(XOR, emptyList());
        op2 = new BinaryOp(AND, emptyList());
        assertNotEquals("Operators of different type should not be equal.", op1, op2);
    }

    /**
     * Test exception on null reference for operand list.
     */
    @Test
    public void testNullOfOperandsList() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new BinaryOp(AND, null);
    }

    /**
     * Test exception on null reference for operator type.
     */
    @Test
    public void testNullType() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        new BinaryOp(null, emptyList());
    }

    /**
     * Test apply() on empty list of operands.
     */
    @Test
    public void testApplyEmpty() throws Exception {
        boolean result = new BinaryOp(AND, emptyList()).apply(null);
        assertEquals("Expected to receive the unit of the operator!", AND.getUnit(), result);
    }

    /**
     * Test apply() on single operand.
     */
    @Test
    public void testApplySingle() throws Exception {
        when(mockOperator.apply(null)).thenReturn(true, false);

        BinaryOp binaryOp = BinaryOp.or(singletonList(mockOperator));
        assertEquals("Binary operator with single argument should return argument.", true, binaryOp.apply(null));
        assertEquals("Binary operator with single argument should return argument.", false, binaryOp.apply(null));
    }

    /**
     * Test apply() on list of operands.
     */
    @Test
    public void testApplyMulti() throws Exception {
        when(mockOperator.apply(null)).thenReturn(true, true, false);

        BinaryOp binaryOp = BinaryOp.and(asList(mockOperator, mockOperator, mockOperator));
        assertEquals("Expected to receive (true && true && false) == false.", false, binaryOp.apply(null));
    }
}
