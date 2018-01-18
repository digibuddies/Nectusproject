package com.digibuddies.cnectus;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

public class notifyservice extends Service {
    FirebaseDatabase database;
    DatabaseReference myRef;
    String idd,st;
    int cnt=2;
    ArrayList<String> rea = new ArrayList<>();
    SQLiteDatabase dbr;
    public notifyservice() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseApp.initializeApp(notifyservice.this);
        Log.d("service222","start");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        dbr = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "reqDB", Context.MODE_PRIVATE, null);
        dbr.execSQL(getString(R.string.reqdb));
        idd = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        gnoti();
        chatn();
        contreq();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void gnoti() {
        myRef.child("groups").child("status").child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                st = "";
                if (dataSnapshot != null) {
                    for (final DataSnapshot contact : dataSnapshot.getChildren()) {
                        if (contact.getValue().equals("Allowed")) {
                            myRef.child("groups").child("readstatus").child(contact.getKey()).child(idd).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot2) {
                                    if (dataSnapshot2.getValue() != null) {
                                        if (dataSnapshot2.getValue().equals("unread")) {
                                            gnoticheck(contact.getKey());
                                            myRef.child("groups").child("readstatus").child(contact.getKey()).child(idd).setValue("read");

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void gnoticheck(final String addName) {
        Intent intent = new Intent(notifyservice.this, group.class);
        intent.putExtra("groupn",1);
        intent.putExtra("grp",addName);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(notifyservice.this,addName.charAt(0) + addName.charAt(1), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(notifyservice.this);
        builder.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= 20) {
            builder.setGroup("grp");
        }
        builder.setTicker(addName + " Notification!");
        builder.setContentTitle(addName + " Notification!");
        builder.setContentText("New Message in " + addName + " Group");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(addName.charAt(0) + addName.charAt(1), builder.build());
    }

    @Override public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    public void contreq() {
        final DatabaseReference myReff = database.getReference("contact");
        myReff.child(idd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Iterable<DataSnapshot> contactChildrens = dataSnapshot.getChildren();
                    if (contactChildrens != null) {
                        for (DataSnapshot contact : contactChildrens) {
                            if (contact.child("request").getValue().equals("RIGHT")) {
                                final String s = contact.getKey();
                                final String mp = contact.child("mp").getValue().toString();
                                myRef.child("Users").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        data snap = dataSnapshot.getValue(data.class);
                                        if (snap != null) {
                                            int aid;
                                            String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s01, mail, uname;
                                            mail = snap.getEmail();
                                            uname = snap.getUname();
                                            aid = snap.getAid();
                                            s1 = snap.getOp1();
                                            s2 = snap.getOp2();
                                            s3 = snap.getOp3();
                                            s4 = snap.getOp4();
                                            s5 = snap.getOp5();
                                            s6 = snap.getOp6();
                                            s7 = snap.getOp7();
                                            s8 = snap.getOp8();
                                            s9 = snap.getOp9();
                                            s10 = snap.getOp10();
                                            s11 = snap.getOp11();
                                            s12 = snap.getOp12();
                                            s01 = snap.getOp01();
                                            String query4 = "INSERT OR REPLACE INTO matches (devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('" + s + "','" + mp + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                            dbr.execSQL(query4);
                                            myReff.child(idd).child(s).child("request").setValue("SENT");
                                            notifyUser2(s);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void notifyUser2(String s) {
        Intent intent = new Intent(notifyservice.this, contact.class);
        intent.putExtra("id", s);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(notifyservice.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(notifyservice.this);
        builder.setAutoCancel(true);
        builder.setTicker("New Request to Connect...");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle("Connect notification!");
        builder.setContentText("You have a new request to connect");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Click to see...");   //API level 16

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(s.charAt(0)+s.charAt(1), builder.build());

    }

    public void chatn(){
        myRef.child("chat").child(idd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myRef.child("chat").child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    {
                                        for (final DataSnapshot dsp2 : dsp.getChildren()) {
                                            if (dsp2.child("read").getValue() != null) {
                                                if (dsp2.child("read").getValue().equals("UNREAD")) {
                                                    String s = dsp2.child("user").getValue().toString();
                                                        notifyUser3(s);
                                                        myRef.child("chat").child(idd).child(dsp.getKey()).child(dsp2.getKey()).child("read").setValue("READ");
                                                    }

                                            }


                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }, 3000);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void notifyUser3(String s) {
        Intent intent = new Intent(notifyservice.this, connections.class);
        intent.putExtra("target", "connections");
        intent.putExtra("position", s);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(notifyservice.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(notifyservice.this);

        builder.setAutoCancel(true);
        builder.setTicker("New Message From " + s);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle("Message Notification!");
        builder.setContentText("You have a new message from " + s);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(s.charAt(0)+s.charAt(1), builder.build());


    }

}
