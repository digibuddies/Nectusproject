package com.digibuddies.cnectus.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.digibuddies.cnectus.BuildConfig;
import com.digibuddies.cnectus.MainActivity;
import com.digibuddies.cnectus.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Vikram on 31-05-2017.
 */
public class FcmMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       if(remoteMessage.getData().size()>0) {
           String title, message = "", img_url = "", uri = "";
           int ver = 0;
           Intent intent;
           title = remoteMessage.getData().get("title");
           message = remoteMessage.getData().get("message");
           uri = remoteMessage.getData().get("uri");
           ver = Integer.parseInt(remoteMessage.getData().get("version"));
           img_url = remoteMessage.getData().get("img_url");
           if (uri.equals("none")) {
               intent = new Intent(this, MainActivity.class);
               intent.putExtra("target", "none");
           } else {
               intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(uri));
           }
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
           final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
           builder.setDefaults(Notification.DEFAULT_ALL);
           builder.setAutoCancel(true);
           builder.setContentTitle(title);
           builder.setContentText(message);
           builder.setContentIntent(pendingIntent);
           builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
           builder.setSmallIcon(R.mipmap.m);
           builder.setContentIntent(pendingIntent);
           final int finalVer = ver;
           if (uri.equals("check")) {
               FirebaseDatabase db = FirebaseDatabase.getInstance();
               DatabaseReference dbr = db.getReference();
               dbr.child("tokens").child(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)).child("status").setValue("Alive"+message);
           } else {
               ImageRequest imageRequest = new ImageRequest(img_url, new Response.Listener<Bitmap>() {
                   @Override
                   public void onResponse(Bitmap response) {
                       builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                       NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                       if (BuildConfig.VERSION_CODE == finalVer||finalVer==0) {
                           notificationManager.notify(0, builder.build());
                       }
                   }
               }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
               mysingleton.getInstance(this).addToRequestQueue(imageRequest);
           }
       }
    }
}