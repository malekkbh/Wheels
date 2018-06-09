package com.malekk.newdriver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by malekkbh on 21/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private  static final String TAG = "MyFirebaseMessagingService" ;

    public MyFirebaseMessagingService() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if( remoteMessage.getData().size() > 0 ) {

            Log.d(TAG, "From: " + remoteMessage.getFrom());

        }

        if(remoteMessage.getNotification() !=null ) {
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            showNotifications(remoteMessage.getNotification().getBody() , remoteMessage.getNotification().getTitle()) ;

        }
    }

    private void showNotifications(String body , String title) {

        Intent intent = new Intent(this , MainActivity.class) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;



        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this) ;
        stackBuilder.addNextIntent(intent) ;

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0 , PendingIntent.FLAG_UPDATE_CURRENT) ;


        NotificationCompat.Builder notificationBouilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent) ;





        NotificationManager notificationManager = ( NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE) ;
        notificationManager.notify(0 , notificationBouilder.build());

        }

}
