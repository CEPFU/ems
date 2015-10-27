package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.databind.JsonNode;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONVerboseNotification;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONEqual;
import org.junit.Test;

import java.util.Collections;

import static de.fu_berlin.agdb.crepe.json.algebra.TestUtil.OBJECT_MAPPER;
import static org.junit.Assert.assertEquals;

public class JSONProfileTest {

    private String jsonValue;
    private JSONProfile profile;

    public JSONProfileTest() {
        jsonValue = "{\n" +
                "  \"@class\": \"JSONProfile\",\n" +
                "  \"rule\": {\n" +
                "    \"@class\": \"JSONEqual\",\n" +
                "    \"attribute\": \"myAttribute\",\n" +
                "    \"toObject\": 0.5,\n" +
                "    \"ofOperands\": null\n" +
                "  },\n" +
                "  \"notifications\": [\n" +
                "    {\n" +
                "      \"@class\": \"JSONVerboseNotification\",\n" +
                "      \"message\": \"This is a message!\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        profile = new JSONProfile();
        profile.setRule(new JSONEqual("myAttribute", 0.5));
        JSONVerboseNotification notification = new JSONVerboseNotification("This is a message!");
        profile.setNotifications(Collections.singletonList(notification));
    }

    @Test
    public void testSimpleProfile() throws Exception {
        JSONProfile deserialized = OBJECT_MAPPER.readValue(jsonValue, JSONProfile.class);
        assertEquals("Deserialization of profile from JSON failed.", profile, deserialized);
    }

    @Test
    public void testSerialize() throws Exception {
        String serialized = OBJECT_MAPPER.writeValueAsString(profile);
        JsonNode serializedTree = OBJECT_MAPPER.readTree(serialized);
        JsonNode expectedTree = OBJECT_MAPPER.readTree(jsonValue);

        assertEquals("Deserialization of profile from JSON failed.", expectedTree, serializedTree);
    }
}
