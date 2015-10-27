package de.fu_berlin.agdb.crepe.algebra.operators.notifications;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.notifications.Notification;
import de.fu_berlin.agdb.crepe.json.push.ionic.NotificationData;
import de.fu_berlin.agdb.crepe.json.push.ionic.PushData;
import de.fu_berlin.agdb.crepe.outputadapters.JSONOutputAdapter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

/**
 * @author Simon Kalt
 */
public class IonicPushNotification implements Notification {
    private static final String apiUrl = "https://push.ionic.io/api/v1/push";
    private String userId;
    private String message;
    private String appId;
    private String privateApiKey;
    private Operator rule;
    private Algebra algebra;

    public IonicPushNotification() {
    }

    public IonicPushNotification(String userId, String message, String appId, String privateApiKey) {
        this.userId = userId;
        this.message = message;
        this.appId = appId;
        this.privateApiKey = privateApiKey;
    }

    @Override
    public void apply() {
        PushData pushData = new PushData(
                null,
                Collections.singletonList(userId),
                null,
                new NotificationData(message)
        );

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(JSONOutputAdapter.OBJECT_MAPPER));

        String apiAuth = privateApiKey + ":";
        String encodedAuth = Base64.getEncoder().encodeToString(apiAuth.getBytes());

        try {
            RequestEntity<PushData> body = RequestEntity.post(new URI(apiUrl))
                    .header("X-Ionic-Application-Id", appId)
                    .header("Authorization", "Basic " + encodedAuth)
                    .body(pushData);

            ResponseEntity<String> exchange = restTemplate.exchange(body, String.class);
            System.out.println("Result: " + exchange.getBody());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Operator getRule() {
        return rule;
    }

    @Override
    public void setRule(Operator rule) {
        this.rule = rule;
    }

    @Override
    public void setAlgebra(Algebra algebra) {
        this.algebra = algebra;
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
}
