package de.fu_berlin.agdb.crepe.json.push.ionic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

/**
 * @author Simon Kalt
 */
public class PushDataTest {
    @Test
    public void testSerialization() throws Exception {
        Map<String, String> payload = new HashMap<>(2);
        payload.put("key1", "value");
        payload.put("key2", "value");

        PushData data = new PushData(
                Arrays.asList("Token1", "Token3", "Token4"),
                null,
                null,
                new NotificationData(
                        "Hello World!",
                        new IosData(1, "ping.aiff", 1423238641, 10, true, payload),
                        new AndroidData("foo", true, 300, payload)
                )
        );

        String expectedJson = "{\n" +
                "  \"tokens\":[\n" +
                "    \"Token1\", \"Token3\", \"Token4\"\n" +
                "  ],\n" +
                "  \"notification\":{\n" +
                "    \"alert\":\"Hello World!\",\n" +
                "    \"ios\":{\n" +
                "      \"badge\":1,\n" +
                "      \"sound\":\"ping.aiff\",\n" +
                "      \"expiry\": 1423238641,\n" +
                "      \"priority\": 10,\n" +
                "      \"contentAvailable\": true,\n" +
                "      \"payload\":{\n" +
                "        \"key1\":\"value\",\n" +
                "        \"key2\":\"value\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"android\":{\n" +
                "      \"collapseKey\":\"foo\",\n" +
                "      \"delayWhileIdle\":true,\n" +
                "      \"timeToLive\":300,\n" +
                "      \"payload\":{\n" +
                "        \"key1\":\"value\",\n" +
                "        \"key2\":\"value\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String actualJson = new ObjectMapper().writeValueAsString(data);

        assertThat(actualJson, is(sameJSONAs(expectedJson)));
    }
}
