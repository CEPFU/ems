package de.fu_berlin.agdb.crepe.json.algebra.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.notifications.VerboseNotification;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"rule"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
public class JSONVerboseNotification extends JSONNotification<VerboseNotification> {
    private String message;

    public JSONVerboseNotification() {

    }

    public JSONVerboseNotification(String message) {
        this.message = message;
    }

    @Override
    public VerboseNotification getAlgebraElement() {
        VerboseNotification notification = new VerboseNotification(message);
        if (rule != null)
            notification.setRule(rule.getAlgebraElement());

        return notification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONVerboseNotification that = (JSONVerboseNotification) o;

        return !(message != null ? !message.equals(that.message) : that.message != null);
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
