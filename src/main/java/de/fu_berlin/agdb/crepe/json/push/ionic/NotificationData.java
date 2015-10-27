package de.fu_berlin.agdb.crepe.json.push.ionic;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Simon Kalt
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationData {
    private String alert;
    private IosData ios;
    private AndroidData android;

    public NotificationData() {
    }

    public NotificationData(String alert) {
        this.alert = alert;
    }

    public NotificationData(String alert, IosData ios, AndroidData android) {
        this.alert = alert;
        this.ios = ios;
        this.android = android;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public IosData getIos() {
        return ios;
    }

    public void setIos(IosData ios) {
        this.ios = ios;
    }

    public AndroidData getAndroid() {
        return android;
    }

    public void setAndroid(AndroidData android) {
        this.android = android;
    }
}
