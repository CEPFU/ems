package de.fu_berlin.agdb.crepe.algebra.operators.notifications;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 * @author Simon Kalt
 */
public class IonicPushNotificationTest {

    @Test
    @Ignore // ionic backend domain does not exist anymore
    public void testApply() throws Exception {
        IonicPushNotification notification = new IonicPushNotification(
                "0dec6bd8-6611-4213-9a18-7ce0ae11fa61",
                "This is a push test!",
                "4d480ee7",
                "28d0b0b7dc8820058112a8f12c9879f0bede2e5b3047a7ad"
        );

        notification.apply();
    }
}
