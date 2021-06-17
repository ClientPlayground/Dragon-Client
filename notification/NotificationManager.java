package Benz.notification;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {

    private static LinkedBlockingQueue pendingNotifications = new LinkedBlockingQueue();
    private static Notification currentNotification = null;

    public static void show(Notification notification) {
        NotificationManager.pendingNotifications.add(notification);
    }

    public static void update() {
        if (NotificationManager.currentNotification != null && !NotificationManager.currentNotification.isShown()) {
            NotificationManager.currentNotification = null;
        }

        if (NotificationManager.currentNotification == null && !NotificationManager.pendingNotifications.isEmpty()) {
            NotificationManager.currentNotification = (Notification) NotificationManager.pendingNotifications.poll();
            NotificationManager.currentNotification.show();
        }

    }

    public static void render() {
        update();
        if (NotificationManager.currentNotification != null) {
            NotificationManager.currentNotification.render();
        }

    }
}
