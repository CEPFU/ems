package de.fu_berlin.agdb.crepe.json.algebra.operators;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.MockOperator;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOp;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperation;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperation;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;
import de.fu_berlin.agdb.crepe.json.algebra.operators.JSONMockOperator;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONAnd;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONOr;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONXor;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JSONOperatorGeneratedEqualityTest {
    @Parameter(0)
    public Operator expected;
    @Parameter(1)
    public JSONOperator testObject;

    @Parameters
    public static Collection<Object> data() {
        return asList(
                (Object[]) new Object[][]{
                        {new MockOperator(), new JSONMockOperator()},
                        {
                                BinaryOp.and(asList(new MockOperator(), new MockOperator())),
                                new JSONAnd(asList(new JSONMockOperator(), new JSONMockOperator()))
                        },
                        {
                                BinaryOp.or(asList(new MockOperator(), new MockOperator(), new MockOperator())),
                                new JSONOr(asList(new JSONMockOperator(), new JSONMockOperator(), new JSONMockOperator()))
                        },
                        {
                                BinaryOp.xor(singletonList(new MockOperator())),
                                new JSONXor(singletonList(new JSONMockOperator()))
                        },
                        {
                                NumericOperation.add("MyAttr", new MockOperator(), 5),
                                new JSONAdd("MyAttr", asList(new JSONMockOperator(), 5))
                        },
                        {
                                NumericOperation.subtract("MyAttr", new MockOperator(), new MockOperator()),
                                new JSONSubtract("MyAttr", asList(new JSONMockOperator(), new JSONMockOperator()))
                        },
                        {
                                NumericOperation.multiply("MyAttr", 7.3, 8),
                                new JSONMultiply("MyAttr", asList(7.3, 8))
                        },
                        {
                                NumericOperation.divide("MyAttr", 7f, 8L),
                                new JSONDivide("MyAttr", asList(7f, 8L))
                        },
                        {
                                NumericOperation.divide("MyAttr", 7f, new MockOperator()),
                                new JSONDivide("MyAttr", asList(7f, new JSONMockOperator()))
                        },
                        {
                                new ComparisonOperation(ComparisonOperationType.GREATER, "MyAttr", 7.0),
                                new JSONGreater("MyAttr", 7.0)
                        },
                        {
                                new ComparisonOperation(ComparisonOperationType.GREATER, "MyAttr", new MockOperator()),
                                new JSONGreater("MyAttr", new JSONMockOperator())
                        }
                }
        );
    }

    @Test
    public void testEqualityOfGeneratedElements() throws Exception {
        Object generated = testObject.getAlgebraElement();

        assertEquals(
                "Generated algebra element of " + testObject.getClass().getSimpleName() + " does not match expected object.",
                expected,
                generated
        );
    }
}

