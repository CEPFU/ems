package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONEqual;

import java.io.IOException;
import java.util.List;

public class JSONEqualDeserializer extends JsonDeserializer<JSONEqual> {
    private static final String PROPERTY_ATTRIBUTE = "attribute";
    private static final String PROPERTY_COMPARE_TO = "toObject";
    private static final String PROPERTY_OPERANDS = "ofOperands";
    private static final Class<JSONEqual> targetType = JSONEqual.class;

    @Override
    public JSONEqual deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String attribute = null;
        Object compareTo = null;
        List<JSONOperator<?>> operands = null;

        JsonToken token = jp.nextValue();

        // TODO: Check for an IEvent as second parameter??
        while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_OBJECT) {
            String currentName = jp.getCurrentName();
            if (currentName != null) {
                switch (currentName) {
                    case PROPERTY_ATTRIBUTE:
                        attribute = jp.getText();
                        break;
                    case PROPERTY_COMPARE_TO:
                        if (token.isScalarValue()) {
                            switch (token) {
                                case VALUE_STRING:
                                    compareTo = jp.getText();
                                    break;
                                case VALUE_NUMBER_INT:
                                    compareTo = jp.getIntValue();
                                    break;
                                case VALUE_NUMBER_FLOAT:
                                    compareTo = jp.getDoubleValue();
                                    break;
                                case VALUE_TRUE:
                                    compareTo = Boolean.TRUE;
                                    break;
                                case VALUE_FALSE:
                                    compareTo = Boolean.FALSE;
                                    break;
                            }
                        } else {
                            compareTo = jp.readValueAs(Object.class);
                        }
                        break;
                    case PROPERTY_OPERANDS:
                        operands = jp.readValueAs(new TypeReference<List<JSONOperator<?>>>() {
                        });
                        break;
                }
            }
            token = jp.nextValue();
        }

        String msg = null;

        if (attribute != null && !attribute.isEmpty()) {
            if (operands != null && operands.size() >= 2)
                return new JSONEqual(attribute, operands);
            else if (compareTo != null)
                return new JSONEqual(attribute, compareTo);
            else {
                msg = String.format(
                        "Could not deserialize instance of %s, need to either specify '%s' or '%s'.",
                        targetType, PROPERTY_COMPARE_TO, PROPERTY_OPERANDS
                );
            }
        } else {
            msg = String.format(
                    "Could not deserialize instance of %s, property '%s' is missing.",
                    targetType, PROPERTY_ATTRIBUTE
            );
        }

        throw new InvalidFormatException(
                msg,
                jp.getCurrentLocation(),
                null,
                JSONEqual.class
        );
    }
}
