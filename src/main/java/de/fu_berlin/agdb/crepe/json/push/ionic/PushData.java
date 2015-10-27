package de.fu_berlin.agdb.crepe.json.push.ionic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fu_berlin.agdb.crepe.algebra.operators.notifications.IonicPushNotification;

import java.util.List;

/**
 * @author Simon Kalt
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushData {
    private List<String> tokens;
    @JsonProperty(value = "user_ids")
    private List<String> userIds;
    private Long scheduled;
    private NotificationData notification;

    public PushData() {
    }

    public PushData(List<String> tokens, List<String> userIds, Long scheduled, NotificationData notification) {
        this.tokens = tokens;
        this.userIds = userIds;
        this.scheduled = scheduled;
        this.notification = notification;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Long getScheduled() {
        return scheduled;
    }

    public void setScheduled(Long scheduled) {
        this.scheduled = scheduled;
    }

    public NotificationData getNotification() {
        return notification;
    }

    public void setNotification(NotificationData notification) {
        this.notification = notification;
    }
}
