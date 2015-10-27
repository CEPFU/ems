package de.fu_berlin.agdb.crepe.json.push.ionic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Kalt
 */
public class IosData {
    private int badge;
    private String sound;
    private long expiry;
    private int priority;
    private boolean contentAvailable;
    private Map<String, String> payload;

    public IosData() {
    }

    public IosData(int badge, String sound, long expiry, int priority, boolean contentAvailable, Map<String, String> payload) {
        this.badge = badge;
        this.sound = sound;
        this.expiry = expiry;
        this.priority = priority;
        this.contentAvailable = contentAvailable;
        this.payload = payload;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
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
