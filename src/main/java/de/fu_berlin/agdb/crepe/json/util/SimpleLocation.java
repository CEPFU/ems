package de.fu_berlin.agdb.crepe.json.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.fu_berlin.agdb.crepe.json.util.SimplePoint;

/**
 * @author Simon Kalt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleLocation {
    private int accuracy;
    private SimplePoint position;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public SimplePoint getPosition() {
        return position;
    }

    public void setPosition(SimplePoint position) {
        this.position = position;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
