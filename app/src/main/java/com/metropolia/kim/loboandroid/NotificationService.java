package com.metropolia.kim.loboandroid;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Kasper on 3.5.2016.
 */
public class NotificationService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void makeNotification() {
        //Service notification
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setAutoCancel(true)
                .setContentTitle("Notification")
                .setContentText("Yo faggot");
        int NOTIF_ID = 1; //Sth here !

        Intent notifIntent = new Intent(this, AlertNotifierActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
        notifBuilder.setContentIntent(pendingIntent);

        NotificationManager notifMan = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifMan.notify(NOTIF_ID, notifBuilder.build());
    }
}
