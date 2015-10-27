package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

/**
 * Serializer for a list of objects that may contain JSONOperators.
 *
 * @author Simon Kalt
 */
public class GenericListSerializer extends JsonSerializer<List<Object>> {
    @Override
    public void serialize(List<Object> values, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (values != null) {
            gen.writeStartArray();

            // For some reason this is necessary for Jackson to generate the correct "@class" attribute
            for (Object value : values) {
                provider.defaultSerializeValue(value, gen);
            }
            gen.writeEndArray();
        } else {
            provider.defaultSerializeValue(null, gen);
        }
    }
}
