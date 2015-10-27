package de.fu_berlin.agdb.crepe.json.push.ionic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Kalt
 */
public class AndroidData {
    private String collapseKey;
    private boolean delayWhileIdle;
    private int timeToLive;
    private Map<String, String> payload;

    public AndroidData() {
    }

    public AndroidData(String collapseKey, boolean delayWhileIdle, int timeToLive, Map<String, String> payload) {
        this.collapseKey = collapseKey;
        this.delayWhileIdle = delayWhileIdle;
        this.timeToLive = timeToLive;
        this.payload = payload;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public boolean isDelayWhileIdle() {
        return delayWhileIdle;
    }

    public void setDelayWhileIdle(boolean delayWhileIdle) {
        this.delayWhileIdle = delayWhileIdle;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public void addPayloadItem(String key, String value) {
        if (payload == null)
            payload = new HashMap<>();
        payload.put(key, value);
    }
}
