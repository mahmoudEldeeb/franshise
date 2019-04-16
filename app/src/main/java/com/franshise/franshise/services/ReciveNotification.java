package com.franshise.franshise.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.FranchiseView;
import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.activites.Main;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.support.constraint.Constraints.TAG;

public class ReciveNotification extends FirebaseMessagingService {
    String title;
    int id;
    String details;
    String CHANNEL_ID="com.franshise.franshise";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       // Log.d(TAG, "From: " + remoteMessage.getFrom());



        Log.v("ggggg","ffffffffffffffffffffffffffff");

        Log.v( "gggggg" ,remoteMessage.getData()+"");
       /* Log.v("ggggg",remoteMessage.getNotification().getTitle());
Log.v("ggggggg",remoteMessage.getNotification().getBody());
        Log.v( "gggggg" ,remoteMessage.toString()+"");
*/

//String s=remoteMessage.getNotification().getBody();
       // Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        //sendNotification(remoteMessage.getData()+"");

        Log.v("ggggg","ffffffffffffffffffffffffffff");
        Log.d(TAG, "gggggg" + remoteMessage.getData().get("image"));

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            sendNotification(Integer.parseInt(remoteMessage.getData().get("id")),remoteMessage.getData().get("name"),remoteMessage.getData().get("details"));


        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token) {
        new SharedPrefrenceModel(this).setToken(token);
        Intent i=new Intent(ReciveNotification.this, RegisterToken.class);
        i.putExtra("token",token);

        startService(i);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }
    private void sendNotification(int id,String name, String details) {
        createNotificationChannel();
        Intent intent = new Intent(this, FranchiseView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title",name);
        intent.putExtra("id",id);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name)
                .setContentText(details)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
