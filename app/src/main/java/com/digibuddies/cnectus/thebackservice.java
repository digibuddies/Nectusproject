package com.digibuddies.cnectus;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

public class thebackservice extends IntentService {

    final ArrayList<String> result = new ArrayList<>();
    final ArrayList<String> result2 = new ArrayList<>();
    ArrayList<Integer> rand = new ArrayList<>();
    ArrayList<String> grp = new ArrayList<String>();
    final ArrayList<String> datadb = new ArrayList<>();
    String u1, u2, u3;
    int aid = 0;
    int[] p1;
    FirebaseDatabase database;
    DatabaseReference myRef2;
    DatabaseReference myRef;
    data[] da;
    //notification
    NotificationManager manager;
    Notification myNotication;
    private SQLiteDatabase db0, mdb, dbr;
    public int cnt = 2, cnt2 = 2;
    private StorageReference mStorageRef;
    //database
    private static final String SELECT_SQL = "SELECT uname FROM matches";
    private SQLiteDatabase db;
    int co = 0, count = 0;
    String st = "";
    int finalI;
    Cursor c, c1;
    String per = "";
    String qsend = "";
    public static String idd;
    String[] x;

    public thebackservice() {
        super("thebackservice");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        FirebaseApp.initializeApp(thebackservice.this);
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference("Ques");
        myRef = database.getReference();

        Log.d("service", "BAck started");
        createDatabase();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        u1 = u2 = u3 = "";
        idd = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        da = new data[16];
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        download();
        String quer = "SELECT ans FROM ques";
        c1 = db0.rawQuery(quer, null);
        c1.moveToFirst();
        if (c1.getCount() > 0) {
            do {
                qsend = qsend + (c1.getString(0));

            } while (c1.moveToNext());
            if (qsend.length() >6) {
                myRef2.child(idd).child("data").setValue(qsend);
            }
        }

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //DataSnapshot contactSnapshot = dataSnapshot.child("Ques");
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                ArrayList<Post> contacts = new ArrayList<>();
                int i = 0, loc = 999, min, match = 0;
                float matchpercen;
                HashMap<String, Float> mMap = new HashMap<String, Float>();
                ArrayList<Float> matches = new ArrayList<>();
                ArrayList<String> k = new ArrayList<>();

                for (DataSnapshot contact : contactChildren) {

                    Post c = contact.getValue(Post.class);
                    contacts.add(c);
                    k.add(contact.getKey());
                    if (k.get(i).equals(idd)) {
                        loc = i;
                    }
                    i++;

                }

                if (loc != 999) {
                    for (i = 0; i < contacts.size(); i++) {
                        if (k.get(i).equals(idd)) {
                            continue;
                        }
                        String A = qsend;
                        String B = contacts.get(i).getData();
                        if (A.length() >= B.length()) {
                            min = B.length();
                        } else min = A.length();

                        for (int j = 0; j < min; j++) {

                            if (A.charAt(j) == B.charAt(j)) {

                                match++;
                            }
                        }

                        matchpercen = ((float) match / (float) min) * 100;
                        match = 0;

                        mMap.put(k.get(i), matchpercen);
                    }
                    mMap = sortHashMapByValues(mMap);
                    for (String key : mMap.keySet()) {
                        result.add(key);
                        String mp = String.format(Locale.US, "%.2f", mMap.get(key));
                        result2.add(mp);
                    }
                    Log.d("cooco", String.valueOf(result2));
                    Log.d("cooco", String.valueOf(result));
                    matchdb();
                    int pointer = result.size() - 1;
                    int r;
                    Random random = new Random();
                    p1 = new int[15];
                    p1[0] = pointer;
                    p1[1] = pointer - 1;
                    p1[2] = pointer - 2;
                    p1[3] = pointer - 3;
                    p1[4] = pointer - 4;
                    p1[5] = pointer - 5;
                    p1[6] = pointer - 6;
                    p1[7] = 2;
                    p1[8] = 1;
                    p1[9] = 0;
                    for (int j = 6; j < 11; j++) {
                        do {
                            r = random.nextInt(pointer);
                        } while (rand.contains(r));
                        rand.add(r);
                    }
                    p1[10] = rand.get(0);
                    p1[11] = rand.get(1);
                    p1[12] = rand.get(2);
                    p1[13] = rand.get(3);
                    p1[14] = rand.get(4);
                    x = new String[20];
                    co = 0;
                    x[0] = result.get(p1[0]);
                    x[1] = result.get(p1[1]);
                    x[2] = result.get(p1[2]);
                    x[3] = result.get(p1[3]);
                    x[4] = result.get(p1[4]);
                    x[5] = result.get(p1[5]);
                    x[6] = result.get(p1[6]);
                    x[7] = result.get(p1[7]);
                    x[8] = result.get(p1[8]);
                    x[9] = result.get(p1[9]);
                    x[10] = result.get(p1[10]);
                    x[11] = result.get(p1[11]);
                    x[12] = result.get(p1[12]);
                    x[13] = result.get(p1[13]);
                    x[14] = result.get(p1[14]);

                    rand.clear();
                    myRef.child("Users").child(x[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            da[1] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[1]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[2] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[2]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[3] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[3]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[4] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[4]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[5] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[5]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[6] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[6]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[7] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[7]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[8] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[8]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[9] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[9]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[10] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[10]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[11] = dataSnapshot.getValue(data.class);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[11]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[12] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[12]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[13] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[13]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            da[14] = dataSnapshot.getValue(data.class);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[14]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("x444", String.valueOf(x[14]));
                            da[15] = dataSnapshot.getValue(data.class);
                            showRecords();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public LinkedHashMap<String, Float> sortHashMapByValues(
            HashMap<String, Float> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Float> sortedMap =
                new LinkedHashMap<>();

        Iterator<Float> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Float val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Float comp1 = passedMap.get(key);
                Float comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    public void notifyUser() {
        Intent intent = new Intent(thebackservice.this, matches.class);
        intent.putExtra("target", "top");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(thebackservice.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(thebackservice.this);
        builder.setAutoCancel(true);
        builder.setTicker("New Match on Cnectus...");
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle("Match Notification!");
        builder.setContentText("You have a new top match");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Click to see...");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(109, builder.build());

    }



    protected void showRecords() {
        String query1;
        for (int i = 1; i <= 15; i++) {
            if (da[i] == null) {
                Log.d("nullddd", String.valueOf(i));
                Log.d("nullddd", String.valueOf(x[i - 1]));
            } else {
                query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('" + i + "','" + x[i - 1] + "','" + String.valueOf(result2.get(p1[i - 1])) + "','" + da[i].getAid() + "','" + da[i].getEmail() + "','" + da[i].getUname() + "','" + da[i].getOp1() + "','" + da[i].getOp2() + "','" + da[i].getOp3() + "','" + da[i].getOp4() + "','" + da[i].getOp5() + "','" + da[i].getOp6() + "','" + da[i].getOp7() + "','" + da[i].getOp8() + "','" + da[i].getOp9() + "','" + da[i].getOp10() + "','" + da[i].getOp11() + "','" + da[i].getOp12() + "','" + da[i].getOp01() + "');";
                db.execSQL(query1);
            }
        }


        datadb.clear();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                datadb.add(c.getString(0));

            } while (c.moveToNext());
            c.close();

        }

        if (datadb.size() >= 3 && da[1]!=null) {
            if (!((da[1].getUname().equals(datadb.get(0))) && (da[2].getUname().equals(datadb.get(1))) && (da[3].getUname().equals(datadb.get(2))))) {
                notifyUser();
                datadb.clear();
            }
        }
    }

    protected void createDatabase() {

        db = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "PerDB", Context.MODE_PRIVATE, null);
        dbr = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "reqDB", Context.MODE_PRIVATE, null);
        db0 = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "QueDB", Context.MODE_PRIVATE, null);
        db.execSQL(getString(R.string.perdb));
        dbr.execSQL(getString(R.string.reqdb));
        db0.execSQL("CREATE TABLE IF NOT EXISTS ques(ans integer);");

    }

    public void download() {

        File localFile = null;
        localFile = new File(getFilesDir(), "fiel.txt");
        mStorageRef.child("data").child("strings" + "." + "txt").getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("firebase", "downloaded");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

    }


    void matchdb() {
        mdb = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "matrec", Context.MODE_PRIVATE, null);
        mdb.execSQL("CREATE TABLE IF NOT EXISTS matches(usid VARCHAR(20) PRIMARY KEY,mp VARCHAR(20));");
        int i;
        for (i = 0; i < result.size(); i++) {
            mdb.execSQL("INSERT OR REPLACE INTO matches(usid,mp) VALUES('" + result.get(i) + "','" + String.valueOf(result2.get(i)) + "')");
        }
    }


}

