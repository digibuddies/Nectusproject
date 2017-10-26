package com.digibuddies.cnectus;

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
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class thebackservice extends IntentService {

    final ArrayList<String> result = new ArrayList<>();
    final ArrayList<String> result2 = new ArrayList<>();
    ArrayList<Integer> rand = new ArrayList<>();
    final ArrayList<String> datadb = new ArrayList<>();
    static ArrayList<String> rea = new ArrayList<>();
    String u1,u2,u3;
    int aid=0;
    int[] p1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Ques");
    DatabaseReference myRef = database.getReference();

    //notification
    NotificationManager manager;
    Notification myNotication;
    private SQLiteDatabase db0,mdb,dbr;
    public int cnt=2,cnt2=2;
    private StorageReference mStorageRef;
    //database
    private static final String SELECT_SQL = "SELECT uname FROM matches";
    private SQLiteDatabase db;
    int co=0;
    int finalI;
    Cursor c,c1;
    String per="";
    String qsend="";
    public static String idd;
    String[] x;

    public thebackservice() {
        super("thebackservice");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service","BAck started");
        createDatabase();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       u1=u2=u3="";
       idd = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        myRef.child("chat").child(idd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                myRef.child("chat").child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    {   for (final DataSnapshot dsp2 : dsp.getChildren()){
                        if(dsp2.child("read").getValue()!=null) {
                            if (dsp2.child("read").getValue().equals("UNREAD")) {
                                Log.d("nottt", "nrrd to send");
                                String s = dsp2.child("user").getValue().toString();
                                if (!(rea.contains(s))) {
                                    rea.add(s);
                                    Log.d("nottt", s);
                                    cnt2++;
                                    notifyUser3(s);
                                }
                            }
                        }


                         }
                }}
            }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }});}}, 3000);}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
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
            if (qsend.length()>8){
                myRef2.child(idd).child("data").setValue(qsend);
            }
        }
        contreq();
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //DataSnapshot contactSnapshot = dataSnapshot.child("Ques");
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                ArrayList<Post> contacts = new ArrayList<>();
                int i=0, loc = 999, min, match = 0;
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
                    String mp = String.format(Locale.US, "%.2f", mMap.get(key));
                    result2.add(mp);
                }
                Log.d("cooco", String.valueOf(result2));
                    Log.d("cooco", String.valueOf(result));
                matchdb();
                int pointer = result.size() - 1;
                    int r;
                    Random random=new Random();
                    p1=new int[15];
                    p1[0] = pointer;
                    p1[1] = pointer - 1;
                    p1[2] = pointer - 2;
                    p1[3] = 2;
                    p1[4] = 1;
                    p1[5] = 0;
                    for(int j=6;j<11;j++){
                        do {
                            r = random.nextInt(pointer);
                        }while (rand.contains(r));
                        rand.add(r);
                    }
                    p1[6] = rand.get(0);
                    p1[7] = rand.get(1);
                    p1[8] = rand.get(2);
                    p1[9] = rand.get(3);
                    p1[10] = rand.get(4);
                    Log.d("rand", String.valueOf(p1[6]));
                    Log.d("rand", String.valueOf(p1[7]));
                    Log.d("rand", String.valueOf(p1[8]));
                    Log.d("rand", String.valueOf(p1[9]));
                    x=new String[15];
                    co=0;
                    x[0]=result.get(p1[0]);
                    x[1]=result.get(p1[1]);
                    x[2]=result.get(p1[2]);
                    x[3]=result.get(p1[3]);
                    x[4]=result.get(p1[4]);
                    x[5]=result.get(p1[5]);
                    x[6]=result.get(p1[6]);
                    x[7]=result.get(p1[7]);
                    x[8]=result.get(p1[8]);
                    x[9]=result.get(p1[9]);
                    x[10]=result.get(p1[10]);
                    rand.clear();
                          myRef.child("Users").child(x[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    data snap = dataSnapshot.getValue(data.class);
                                    if (snap != null) {
                                        String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
                                        mail = snap.getEmail();
                                        uname = snap.getUname();
                                        u1 = uname;
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
                                        per = String.valueOf(result2.get(p1[0]));
                                        Log.d("coocc", uname+"--"+String.valueOf(per)+"1");
                                        String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(1,'" + x[0] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                        db.execSQL(query1);
                                    }
                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    myRef.child("Users").child(x[1]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
                                mail = snap.getEmail();
                                uname = snap.getUname();
                                    u2 = uname;
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
                                per = String.valueOf(result2.get(p1[1]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"2");
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(2,'" + x[1] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[2]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
                                mail = snap.getEmail();
                                uname = snap.getUname();
                                    u3 = uname;
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
                                per = String.valueOf(result2.get(p1[2]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"3");
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(3,'" + x[2] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[3]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[3]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+""+String.valueOf(co));
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(4,'" + x[3] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef.child("Users").child(x[4]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[4]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"--"+String.valueOf(co));
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(5,'" + x[4] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[5]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[5]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+""+String.valueOf(co));
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(6,'" + x[5] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";

                                db.execSQL(query1); }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[6]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[6]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"--"+String.valueOf(co));
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(7,'" + x[6] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);}
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[7]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[7]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+""+String.valueOf(co));
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(8,'" + x[7] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);}
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[8]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[8]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"9");
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(9,'" + x[8] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);}
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[9]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[9]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"10");
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(10,'" + x[9] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);}
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef.child("Users").child(x[10]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data snap = dataSnapshot.getValue(data.class);
                            if (snap != null) {
                                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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

                                per = String.valueOf(result2.get(p1[10]));
                                Log.d("coocc", uname+"--"+String.valueOf(per)+"11");
                                String query1 = "INSERT OR REPLACE INTO matches (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES(11,'" + x[10] + "','" + per + "','" + aid + "','" + mail + "','" + uname + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "','" + s11 + "','" + s12 + "','" + s01 + "');";
                                db.execSQL(query1);
                                showRecords(); }
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
        intent.putExtra("target","top");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(thebackservice.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(thebackservice.this);
        builder.setAutoCancel(true);
        builder.setTicker("New Match on Cnectus...");
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle("Match Notification!");
        builder.setContentText("You have a new top 3 match");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Click to see...");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(109, builder.build());

    }
    public void notifyUser2(String s) {
        Intent intent = new Intent(thebackservice.this, contact.class);
        intent.putExtra("id",s);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(thebackservice.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(thebackservice.this);

        builder.setAutoCancel(true);
        builder.setTicker("New Request to Connect...");
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle("Connect notification!");
        builder.setContentText("You have a new request to connect");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Click to see...");   //API level 16

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10+cnt, builder.build());
        cnt++;

    }

    public void notifyUser3(String s) {
        Intent intent = new Intent(thebackservice.this, connections.class);
        intent.putExtra("target","connections");
        intent.putExtra("position",s);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(thebackservice.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(thebackservice.this);

        builder.setAutoCancel(true);
        builder.setTicker("New Message From "+s);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle("Message Notification!");
        builder.setContentText("You have a new message from "+s);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.m);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(92+cnt2, builder.build());



    }

    protected void showRecords() {
        datadb.clear();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                datadb.add(c.getString(0));

            } while (c.moveToNext());
            c.close();

        }

        if(datadb.size()>0){
            if (!((u1.equals(datadb.get(0)))&&(u2.equals(datadb.get(1)))&&(u3.equals(datadb.get(2))))) {
                notifyUser();
                Log.d("dataaa",u2+" "+datadb.get(1));
                datadb.clear();
            }
        }
    }

    protected void createDatabase(){

        db=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"PerDB", Context.MODE_PRIVATE, null);
        dbr=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"reqDB", Context.MODE_PRIVATE, null);
        db0=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"QueDB", Context.MODE_PRIVATE, null);
        db.execSQL(getString(R.string.perdb));
        dbr.execSQL(getString(R.string.reqdb));
        db0.execSQL("CREATE TABLE IF NOT EXISTS ques(ans integer);");

        }

public void download(){

    File localFile = null;
    localFile = new File(getFilesDir(), "fiel.txt");
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
    public void contreq(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myReff = database.getReference("contact");
        myReff.child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Iterable<DataSnapshot> contactChildrens = dataSnapshot.getChildren();
                    if(contactChildrens!=null){
                    for (DataSnapshot contact : contactChildrens) {
                        if (contact.child("request").getValue().equals("RIGHT")){
                            final String s = contact.getKey();
                            final String mp = contact.child("mp").getValue().toString();
                            myRef.child("Users").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    data snap = dataSnapshot.getValue(data.class);
                                    if (snap != null) {
                                        String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname;
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
                                        s01=snap.getOp01();
                                        String query4 = "INSERT OR REPLACE INTO matches (devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('"+s+"','"+mp+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"','"+s01+"');";
                                        dbr.execSQL(query4);
                                        cnt2++;
                                        myReff.child(idd).child(s).child("request").setValue("SENT");
                                        notifyUser2(s);


                                    }}

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }}
            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void matchdb(){
        mdb=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"matrec", Context.MODE_PRIVATE, null);
        mdb.execSQL("CREATE TABLE IF NOT EXISTS matches(usid VARCHAR(20) PRIMARY KEY,mp VARCHAR(20));");
        int i;
        for(i=0;i<result.size();i++){
        mdb.execSQL("INSERT OR REPLACE INTO matches(usid,mp) VALUES('"+result.get(i)+"','"+String.valueOf(result2.get(i))+"')");
    }}
}

