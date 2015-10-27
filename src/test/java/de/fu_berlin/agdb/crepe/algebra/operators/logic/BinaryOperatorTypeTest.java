package de.fu_berlin.agdb.crepe.algebra.operators.logic;

import org.junit.Test;

import java.util.function.BinaryOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test boolean binary operator types.
 *
 * @author Simon Kalt
 */
public class BinaryOperatorTypeTest {

    @Test
    public void testAndOperator() throws Exception {
        BinaryOperator<Boolean> and = AND.getOperator();
        assertThat("false && false should return false", and.apply(false, false), is(false));
        assertThat("false && true should return false", and.apply(false, true), is(false));
        assertThat("true && false should return false", and.apply(true, false), is(false));
        assertThat("true && true should return true", and.apply(true, true), is(true));
    }

    @Test
    public void testOrOperator() throws Exception {
        BinaryOperator<Boolean> or = OR.getOperator();
        assertThat("false && false should return false", or.apply(false, false), is(false));
        assertThat("false && true should return true", or.apply(false, true), is(true));
        assertThat("true && false should return true", or.apply(true, false), is(true));
        assertThat("true && true should return true", or.apply(true, true), is(true));
    }

    @Test
    public void testXorOperator() throws Exception {
        BinaryOperator<Boolean> xor = XOR.getOperator();
        assertThat("false && false should return false", xor.apply(false, false), is(false));
        assertThat("false && true should return true", xor.apply(false, true), is(true));
        assertThat("true && false should return true", xor.apply(true, false), is(true));
        assertThat("true && true should return false", xor.apply(true, true), is(false));
    }


    @Test
    public void testGetUnit() throws Exception {
        assertThat("Unit for 'AND' is incorrect.", AND.getUnit(), is(true));
        assertThat("Unit for 'OR' is incorrect.", OR.getUnit(), is(false));
        assertThat("Unit for 'XOR' is incorrect.", XOR.getUnit(), is(false));
    }
}
