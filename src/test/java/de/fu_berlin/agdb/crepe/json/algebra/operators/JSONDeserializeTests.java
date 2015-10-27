package de.fu_berlin.agdb.crepe.json.algebra.operators;

import de.fu_berlin.agdb.crepe.json.algebra.JSONAlgebraElement;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONVerboseNotification;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONAnd;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONNot;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONOr;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONXor;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

import static de.fu_berlin.agdb.crepe.json.algebra.TestUtil.OBJECT_MAPPER;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

@RunWith(Parameterized.class)
public class JSONDeserializeTests {
    @Parameter(0)
    public JSONAlgebraElement expected;
    @Parameter(1)
    public String jsonValue;

    @Parameters
    public static Collection<Object> data() {
        return asList(
                (Object[]) new Object[][]{
                        {
                                new JSONMockOperator(),
                                "{\"@class\": \"JSONMockOperator\"}"
                        },
                        {
                                new JSONEqual("myAttribute", 0.5),
                                "{\"@class\": \"JSONEqual\", \"attribute\": \"myAttribute\", \"toObject\": 0.5, \"ofOperands\": null}"
                        },
                        {
                                new JSONAnd(asList(new JSONMockOperator(), new JSONMockOperator(), new JSONMockOperator())),
                                "{\"@class\": \"JSONAnd\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {
                                new JSONVerboseNotification("My message!"),
                                "{\"@class\": \"JSONVerboseNotification\", \"message\": \"My message!\"}"
                        },
                        {
                                new JSONMatchToStation(new JSONMockOperator(), 125),
                                "{\"@class\": \"JSONMatchToStation\", \"matchOperator\": {\"@class\": \"JSONMockOperator\"}, \"toStation\": 125}"
                        },
                        {
                                new JSONOr(singletonList(new JSONMockOperator())),
                                "{\"@class\": \"JSONOr\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {
                                new JSONXor(asList(new JSONMockOperator(), new JSONMockOperator())),
                                "{\"@class\": \"JSONXor\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {
                                new JSONNot(new JSONMockOperator()),
                                "{\"@class\": \"JSONNot\", \"ofOperand\": {\"@class\": \"JSONMockOperator\"}}"
                        },
                        {
                                new JSONAdd("MyAttr", asList(new JSONMockOperator(), 5)),
                                "{\"@class\": \"JSONAdd\", \"attribute\": \"MyAttr\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}, 5]}"
                        },
                        {
                                new JSONSubtract("MyAttr", asList(new JSONMockOperator(), new JSONMockOperator())),
                                "{\"@class\": \"JSONSubtract\", \"attribute\": \"MyAttr\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {
                                new JSONMultiply("MyAttr", asList(7.3, 8)),
                                "{\"@class\": \"JSONMultiply\", \"attribute\": \"MyAttr\", \"ofOperands\": [7.3, 8]}"
                        },
                        {
                                new JSONDivide("MyAttr", asList(7.0, 123576124981241L)),
                                "{\"@class\": \"JSONDivide\", \"attribute\": \"MyAttr\", \"ofOperands\": [7.0, 123576124981241]}"
                        },
                        {
                                new JSONAdd("MyAttr", asList(7.0, new JSONMockOperator())),
                                "{\"@class\": \"JSONAdd\", \"attribute\": \"MyAttr\", \"ofOperands\": [7.0, {\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {
                                new JSONGreater("MyAttr", 7.0),
                                "{\"@class\": \"JSONGreater\", \"attribute\": \"MyAttr\", \"toObject\": 7.0}"
                        },
                        {
                                new JSONGreater("MyAttr", new JSONMockOperator()),
                                "{\"@class\": \"JSONGreater\", \"attribute\": \"MyAttr\", \"toOperator\": {\"@class\": \"JSONMockOperator\"}}"
                        }
                        // Add new test values here
                }
        );
    }

    @Test
    public void testDeserialize() throws Exception {
        JSONAlgebraElement deserialized = OBJECT_MAPPER.readValue(jsonValue, expected.getClass());

        assertEquals(
                "Deserialization of " + expected.getClass().getSimpleName() + " object incorrect.",
                expected,
                deserialized
        );
    }

    @Test
    public void testDeserializeAsGenericType() throws Exception {
        JSONAlgebraElement deserialized = OBJECT_MAPPER.readValue(jsonValue, JSONAlgebraElement.class);

        assertEquals(
                "Deserialization of " + expected.getClass().getSimpleName() + " as generic type JSONOperator failed.",
                expected,
                deserialized
        );
    }

    @Test
    public void testSerialize() throws Exception {
        String serialized = OBJECT_MAPPER.writeValueAsString(expected);

        assertThat("Serialization of value to JSON failed.",
                serialized,
                is(sameJSONAs(jsonValue)
                .allowingExtraUnexpectedFields())
        );
    }
}
