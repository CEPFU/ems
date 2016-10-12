package de.fu_berlin.agdb.crepe.algebra.notifications;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.json.algebra.push.android.AndroidPushData;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class AndroidPushNotification implements Notification
{
    private Algebra algebra;
    private Operator rule;
    private final AndroidPushData pushData;

    private boolean triggered;

    public AndroidPushNotification(String message, String firebaseToken, long profilePrimaryKey)
    {
        this.pushData = new AndroidPushData(message, firebaseToken, profilePrimaryKey);
    }

    @Override
    public void apply()
    {
        if (!triggered)
        {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().addAll(
                    Arrays.asList(new MappingJackson2HttpMessageConverter(), new StringHttpMessageConverter())
            );

            restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", pushData, String.class);
            triggered = true;
            getRule().reset();
        }
        else
        {
            getRule().reset();
        }


    }

    @Override
    public void setRule(Operator rule)
    {
        this.rule = rule;
    }

    @Override
    public Operator getRule()
    {
        return rule;
    }

    @Override
    public void setAlgebra(Algebra algebra)
    {
        this.algebra = algebra;
    }

    @Override
    public String toString() {
        return "androidPushNotification()";
    }
}
