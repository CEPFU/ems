package de.fu_berlin.agdb.crepe.json.algebra.notifications;

import de.fu_berlin.agdb.crepe.algebra.operators.notifications.IonicPushNotification;

import java.util.Objects;

/**
 * @author Simon Kalt
 */
public class JSONIonicPushNotification extends JSONNotification<IonicPushNotification> {
    private String userId;
    private String message;
    private String appId;
    private String privateApiKey;

    public JSONIonicPushNotification() {
    }

    public JSONIonicPushNotification(String userId, String message, String appId, String privateApiKey) {
        this.userId = userId;
        this.message = message;
        this.appId = appId;
        this.privateApiKey = privateApiKey;
    }

    @Override
    public IonicPushNotification getAlgebraElement() {
        IonicPushNotification notification = new IonicPushNotification(userId, message, appId, privateApiKey);

        if (rule != null)
            notification.setRule(rule.getAlgebraElement());

        return notification;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateApiKey() {
        return privateApiKey;
    }

    public void setPrivateApiKey(String privateApiKey) {
        this.privateApiKey = privateApiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONIonicPushNotification that = (JSONIonicPushNotification) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (appId != null ? !appId.equals(that.appId) : that.appId != null) return false;
        return !(privateApiKey != null ? !privateApiKey.equals(that.privateApiKey) : that.privateApiKey != null);

    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, message, appId, privateApiKey);
    }
}
