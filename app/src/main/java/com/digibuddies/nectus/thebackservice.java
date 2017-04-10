package com.digibuddies.nectus;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class thebackservice extends IntentService {

    final ArrayList<String> result = new ArrayList<>();
    final ArrayList<String> result2 = new ArrayList<>();
    final ArrayList<String> datadb = new ArrayList<>();
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,mail,uname,u1,u2,u3;
    int aid=0;
    int p1,p2,p3;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Ques");
    data snap;
    //notification
    NotificationManager manager;
    Notification myNotication;
    private SQLiteDatabase db0;
    private StorageReference mStorageRef;
    //database
    private static final String SELECT_SQL = "SELECT uname FROM matches";
    private SQLiteDatabase db;
    int co=0;
    private Cursor c,c1;
    String per="";
    String qsend="";

    public thebackservice() {
        super("thebackservice");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service","BAck started");
        createDatabase();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        s1=s2=s3=s4=s5=s6=s7=s8=s9=s10=s11=s12=uname=mail=u1=u2=u3="";
        final String idd = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        download();
        String quer="SELECT ans FROM ques";
        c1=db0.rawQuery(quer,null);
        c1.moveToFirst();
        if(c1.getCount()>0) {
            do {
                qsend = qsend + (c1.getString(0));

            } while (c1.moveToNext());

            myRef2.child(idd).child("data").setValue(qsend);
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot = dataSnapshot.child("Ques");
                Iterable<DataSnapshot> contactChildren = contactSnapshot.getChildren();
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

                if (loc != 999){
                    for (i = 0; i < contacts.size(); i++) {
                        if (k.get(i).equals(idd)) {
                            continue;
                        }
                        String A = contacts.get(loc).getData();
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
                    result2.add(String.valueOf(mMap.get(key)));
                }

                int pointer = result.size() - 1;
                p1 = pointer;
                p2 = pointer - 1;
                p3 = pointer - 2;

                myRef.child("Users").child(result.get(p1)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        snap = dataSnapshot.getValue(data.class);
                        if (snap != null) {
                            mail = snap.getEmail();
                            uname = snap.getUname();
                            u1=uname;
                            aid = snap.getAid();
                            s1=snap.getOp1();
                            s2=snap.getOp2();
                            s3=snap.getOp3();
                            s4=snap.getOp4();
                            s5=snap.getOp5();
                            s6=snap.getOp6();
                            s7=snap.getOp7();
                            s8=snap.getOp8();
                            s9=snap.getOp9();
                            s10=snap.getOp10();
                            s11=snap.getOp11();
                            s12=snap.getOp12();
                            co=1;
                            per = String.valueOf(result2.get(p1));
                            String query1 = "INSERT OR REPLACE INTO matches (id,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+co+"','"+per+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"');";

                            db.execSQL(query1);

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                myRef.child("Users").child(result.get(p2)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        snap = dataSnapshot.getValue(data.class);
                        if (snap != null) {
                            mail = snap.getEmail();
                            uname = snap.getUname();
                            u2=uname;
                            aid = snap.getAid();
                            s1=snap.getOp1();
                            s2=snap.getOp2();
                            s3=snap.getOp3();
                            s4=snap.getOp4();
                            s5=snap.getOp5();
                            s6=snap.getOp6();
                            s7=snap.getOp7();
                            s8=snap.getOp8();
                            s9=snap.getOp9();
                            s10=snap.getOp10();
                            s11=snap.getOp11();
                            s12=snap.getOp12();
                            co=2;
                            per = String.valueOf(result2.get(p2));
                            String query2 = "INSERT OR REPLACE INTO matches (id,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+co+"','"+per+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"');";

                            db.execSQL(query2);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                myRef.child("Users").child(result.get(p3)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        snap = dataSnapshot.getValue(data.class);
                        if (snap != null) {
                            mail = snap.getEmail();
                            uname = snap.getUname();
                            u3=uname;
                            aid = snap.getAid();
                            s1=snap.getOp1();
                            s2=snap.getOp2();
                            s3=snap.getOp3();
                            s4=snap.getOp4();
                            s5=snap.getOp5();
                            s6=snap.getOp6();
                            s7=snap.getOp7();
                            s8=snap.getOp8();
                            s9=snap.getOp9();
                            s10=snap.getOp10();
                            s11=snap.getOp11();
                            s12=snap.getOp12();
                            co=3;
                            per = String.valueOf(result2.get(p3));
                            String query3 = "INSERT OR REPLACE INTO matches (id,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+co+"','"+per+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"');";

                            db.execSQL(query3);
                            showRecords();
                            if(datadb.size()>0){
                            if ((!(u1.equals(datadb.get(0)))) || (!(u2.equals(datadb.get(1)))) || (!(u3.equals(datadb.get(2))))) {
                                notifyUser();
                            }
                            }

                        }
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

    public LinkedHashMap<String,Float> sortHashMapByValues(
            HashMap<String,Float> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String,Float> sortedMap =
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

        PendingIntent pendingIntent = PendingIntent.getActivity(thebackservice.this, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(thebackservice.this);

        builder.setAutoCancel(true);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Nectus Notification !");
        builder.setContentText("You have a new match");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.zaa));
        builder.setSmallIcon(R.drawable.zaa);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("Click to see...");   //API level 16
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);

    }

    protected void showRecords() {
        if (c.getCount() > 0) {

            do {
                datadb.add(c.getString(0));

            } while (c.moveToNext());
            c.close();

        }
    }

    protected void createDatabase(){
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db0=openOrCreateDatabase("questions", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS matches(id INTEGER PRIMARY KEY,mp varchar(20), aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");
        db0.execSQL("CREATE TABLE IF NOT EXISTS ques(ans integer);");
    }

public void download(){

    File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
// Create direcorty if not exists
    if(!storagePath.exists()) {
        storagePath.mkdirs();
    }

    File localFile = new File(storagePath, "fiel.txt");

    mStorageRef.child("data").child("strings"+"."+"txt").getFile(localFile)
            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("firebase","downloaded");
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle failed download
            // ...
        }
    });

}
}



