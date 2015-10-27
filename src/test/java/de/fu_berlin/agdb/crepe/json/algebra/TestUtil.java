package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.json.algebra.operators.JSONMockOperator;

public class TestUtil {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerSubtypes(JSONMockOperator.class);
    }
}
