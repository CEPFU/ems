package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.fu_berlin.agdb.crepe.json.algebra.JSONAlgebraElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializer for a list of objects and JSONOperator's.
 *
 * @author Simon Kalt
 */
public class AlgebraElementListDeserializer extends JsonDeserializer<List<Object>> {
    @Override
    public List<Object> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.nextValue();
        ArrayList<Object> result = new ArrayList<>();

        while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_ARRAY) {
            Object deserialized;

            if (token == JsonToken.START_OBJECT) {
                try {
                    deserialized = jp.readValueAs(JSONAlgebraElement.class);
                } catch (IOException ignored) {
                    deserialized = jp.readValueAs(Object.class);
                }
            } else {
                deserialized = jp.readValueAs(Object.class);
            }

            result.add(deserialized);

            token = jp.nextValue();
        }

        return result;
    }
}
