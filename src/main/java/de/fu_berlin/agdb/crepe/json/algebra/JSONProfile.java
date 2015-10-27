package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.Profile;
import de.fu_berlin.agdb.crepe.algebra.notifications.Notification;
import de.fu_berlin.agdb.crepe.algebra.windows.EndlessWindow;
import de.fu_berlin.agdb.crepe.algebra.windows.IWindow;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Same data as {@link Profile} but
 * without any functionality. Used for JSON (de-)serialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class", defaultImpl = JSONProfile.class)
public class JSONProfile extends JSONAlgebraElement<Profile> {
    private JSONOperator<? extends Operator> rule;
    private List<JSONNotification<?>> notifications;
    // TODO: Add window?

    @Override
    public Profile getAlgebraElement() {
        return getProfile(new EndlessWindow());
    }

    @JsonIgnore
    public Profile getProfile(IWindow window) {
        Operator ruleAlgebraElement = rule.getAlgebraElement();

        // Currently gives us an error with errorprone
        /*
        Notification[] notificationArray1 = notifications
                .stream()
                .map(JSONNotification::getAlgebraElement)
                .map((notification -> {
                    notification.setRule(ruleAlgebraElement);
                    return notification;
                }))
                .toArray(Notification[]::new);
                */

        // This is ugly...
        List<Notification> elements = new ArrayList<>(notifications.size());
        for (JSONNotification not : notifications) {
            Notification notificationElement = (Notification) not.getAlgebraElement();
            notificationElement.setRule(ruleAlgebraElement);
            elements.add(notificationElement);
        }
        Notification[] notificationArray = elements.toArray(new Notification[elements.size()]);

        return new Profile(ruleAlgebraElement, window, notificationArray);
    }

    public JSONOperator<? extends Operator> getRule() {
        return rule;
    }

    public void setRule(JSONOperator<? extends Operator> rule) {
        this.rule = rule;
    }

    public List<JSONNotification<?>> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<JSONNotification<?>> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONProfile that = (JSONProfile) o;

        if (rule != null ? !rule.equals(that.rule) : that.rule != null) return false;
        return !(notifications != null ? !notifications.equals(that.notifications) : that.notifications != null);
    }

    @Override
    public int hashCode() {
        int result = rule != null ? rule.hashCode() : 0;
        result = 31 * result + (notifications != null ? notifications.hashCode() : 0);
        return result;
    }
}
