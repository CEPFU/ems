package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.*;
import static org.junit.Assert.assertEquals;

/**
 * Test for numeric operation types.
 *
 * @author Simon Kalt
 */
public class NumericOperationTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private Object mockObject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAssertNumber() throws Exception {
        assertNumber(5);
        assertNumber(3L);
        assertNumber(7.3f);
        assertNumber(5.5);
    }

    @Test
    public void testAssertNumberException() throws OperatorNotSupportedException {
        thrown.expect(OperatorNotSupportedException.class);
        assertNumber(mockObject);
    }

    @Test
    public void testIntegerAddition() throws Exception {
        int first = 1249812, second = 135812;
        IntBinaryOperator integerOperator = ADD.getIntegerOperator();
        assertEquals("Addition failed.", (Number) (first + second), integerOperator.applyAsInt(first, second));
    }

    @Test
    public void testIntegerSubtraction() throws Exception {
        int first = 2356234, second = 135123;
        IntBinaryOperator integerOperator = SUBTRACT.getIntegerOperator();
        assertEquals("Addition failed.", (Number) (first - second), integerOperator.applyAsInt(first, second));
    }

    @Test
    public void testIntegerMultiplication() throws Exception {
        int first = 124124, second = 536345;
        IntBinaryOperator integerOperator = MULTIPLY.getIntegerOperator();
        assertEquals("Addition failed.", (Number) (first * second), integerOperator.applyAsInt(first, second));
    }

    @Test
    public void testIntegerDivision() throws Exception {
        int first = 673452, second = 253234;
        IntBinaryOperator integerOperator = DIVIDE.getIntegerOperator();
        assertEquals("Addition failed.", (Number) (first / second), integerOperator.applyAsInt(first, second));
    }

    @Test
    public void testLongAddition() throws Exception {
        long first = 12498124112L, second = 13581213512L;
        LongBinaryOperator longOperator = ADD.getLongOperator();
        assertEquals("Addition failed.", (Number) (first + second), longOperator.applyAsLong(first, second));
    }

    @Test
    public void testLongSubtraction() throws Exception {
        long first = 124121212512L, second = 1351231325124L;
        LongBinaryOperator longOperator = SUBTRACT.getLongOperator();
        assertEquals("Addition failed.", (Number) (first - second), longOperator.applyAsLong(first, second));
    }

    @Test
    public void testLongMultiplication() throws Exception {
        long first = 124124235412L, second = 5363451241L;
        LongBinaryOperator longOperator = MULTIPLY.getLongOperator();
        assertEquals("Addition failed.", (Number) (first * second), longOperator.applyAsLong(first, second));
    }

    @Test
    public void testLongDivision() throws Exception {
        long first = 6734522351L, second = 2532345124L;
        LongBinaryOperator longOperator = DIVIDE.getLongOperator();
        assertEquals("Addition failed.", (Number) (first / second), longOperator.applyAsLong(first, second));
    }

    @Test
    public void testFloatAddition() throws Exception {
        float first = 12124.123523f, second = 135812.4124f;
        BinaryOperator<Float> floatOperator = ADD.getFloatOperator();
        assertEquals("Addition failed.", (Number) (first + second), floatOperator.apply(first, second));
    }

    @Test
    public void testFloatSubtraction() throws Exception {
        float first = 23562.124f, second = 135123.12412f;
        BinaryOperator<Float> floatOperator = SUBTRACT.getFloatOperator();
        assertEquals("Addition failed.", (Number) (first - second), floatOperator.apply(first, second));
    }

    @Test
    public void testFloatMultiplication() throws Exception {
        float first = 124124.12411f, second = 536345;
        BinaryOperator<Float> floatOperator = MULTIPLY.getFloatOperator();
        assertEquals("Addition failed.", (Number) (first * second), floatOperator.apply(first, second));
    }

    @Test
    public void testFloatDivision() throws Exception {
        float first = 673452, second = 253234;
        BinaryOperator<Float> floatOperator = DIVIDE.getFloatOperator();
        assertEquals("Addition failed.", (Number) (first / second), floatOperator.apply(first, second));
    }

    @Test
    public void testDoubleAddition() throws Exception {
        double first = 12124.123523, second = 135812.4124;
        DoubleBinaryOperator doubleOperator = ADD.getDoubleOperator();
        assertEquals("Addition failed.", (Number) (first + second), doubleOperator.applyAsDouble(first, second));
    }

    @Test
    public void testDoubleSubtraction() throws Exception {
        double first = 23562.124, second = 135123.12412;
        DoubleBinaryOperator doubleOperator = SUBTRACT.getDoubleOperator();
        assertEquals("Addition failed.", (Number) (first - second), doubleOperator.applyAsDouble(first, second));
    }

    @Test
    public void testDoubleMultiplication() throws Exception {
        double first = 124124.12411, second = 536345;
        DoubleBinaryOperator doubleOperator = MULTIPLY.getDoubleOperator();
        assertEquals("Addition failed.", (Number) (first * second), doubleOperator.applyAsDouble(first, second));
    }

    @Test
    public void testDoubleDivision() throws Exception {
        double first = 673452, second = 253234;
        DoubleBinaryOperator doubleOperator = DIVIDE.getDoubleOperator();
        assertEquals("Addition failed.", (Number) (first / second), doubleOperator.applyAsDouble(first, second));
    }

    @Test
    public void testApplyInteger() throws Exception {
        int first = 7457634, second = 152123;
        Integer expected = first + second;
        Integer actual = ADD.apply(first, second);
        assertEquals("NumericOperationType.apply(int, int) failed.", expected, actual);
    }

    @Test
    public void testApplyLong() throws Exception {
        long first = 7457631351244L;
        long second = 1521231531241L;
        Long expected = first - second;
        Long actual = SUBTRACT.apply(first, second);
        assertEquals("NumericOperationType.apply(long, long) failed.", expected, actual);
    }

    @Test
    public void testApplyFloat() throws Exception {
        float first = 12412.124f;
        float second = 3.14159f;
        float expected = first / second;
        float actual = DIVIDE.apply(first, second);
        assertEquals("NumericOperationType.apply(float, float) failed.", expected, actual, 0.0001);
    }

    @Test
    public void testApplyDouble() throws Exception {
        double first = 135124.124121;
        double second = Math.PI;
        double expected = first * second;
        double actual = MULTIPLY.apply(first, second);
        assertEquals("NumericOperationType.apply(double, double) failed.", expected, actual, 0.0001);
    }

    @Test
    public void testApplyNumber() throws Exception {
        long first = 13512235124121L;
        double second = Math.PI;
        Double expected = first * second;
        Number actual = MULTIPLY.apply(first, (Number) second);
        assertEquals("NumericOperationType.apply(Number, Number) failed.", expected, actual);
    }

    @Test
    public void testApplyObj() throws Exception {
        long first = 13512235124121L;
        double second = Math.PI;
        double expected = first / second;
        Number actual = DIVIDE.applyObj(first, second);
        assertEquals("NumericOperationType.apply(Number, Number) failed.", expected, actual);
    }
}
