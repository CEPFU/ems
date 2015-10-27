package de.fu_berlin.agdb.crepe.algebra.operators;

import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOpTest;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorTypeTest;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationTest;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationTypeTest;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationTest;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationTypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for algebra operators.
 *
 * @author Simon Kalt
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BinaryOperatorTypeTest.class,
        BinaryOpTest.class,
        NumericOperationTypeTest.class,
        NumericOperationTest.class,
        ComparisonOperationTypeTest.class,
        ComparisonOperationTest.class
})
public class OperatorTestSuite {
}
