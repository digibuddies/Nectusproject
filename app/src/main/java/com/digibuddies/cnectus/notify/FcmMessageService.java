package com.digibuddies.cnectus.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.digibuddies.cnectus.MainActivity;
import com.digibuddies.cnectus.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Vikram on 31-05-2017.
 */
public class FcmMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       if(remoteMessage.getData().size()>0){
           String title,message,img_url;
           title=remoteMessage.getData().get("title");
           message=remoteMessage.getData().get("message");
           img_url=remoteMessage.getData().get("img_url");
           Intent intent=new Intent(this,MainActivity.class);
           intent.putExtra("target","none");
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
           final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
           builder.setDefaults(Notification.DEFAULT_VIBRATE);
           builder.setContentTitle("Cnectus Notification!");
           builder.setContentText("Expand to See");
           builder.setContentIntent(pendingIntent);
           builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
           builder.setSmallIcon(R.mipmap.ic_launcher);
           builder.setContentIntent(pendingIntent);
           ImageRequest imageRequest=new ImageRequest(img_url, new Response.Listener<Bitmap>() {
               @Override
               public void onResponse(Bitmap response) {

                   builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                    NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                   notificationManager.notify(0,builder.build());
               }
           }, 0,0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           });
            mysingleton.getInstance(this).addToRequestQueue(imageRequest);
       }

    }
}