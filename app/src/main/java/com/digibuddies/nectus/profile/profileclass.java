package com.digibuddies.nectus.profile;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import com.digibuddies.nectus.MainActivity;
import com.digibuddies.nectus.R;

import java.io.File;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.animations.IViewTranslation;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileclass extends MaterialIntroActivity {

    CircleImageView v1;
    CircleImageView v2;
    CircleImageView v3;
    public SQLiteDatabase db;
    CircleImageView v4;
    Cursor c;
    public static String o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11,o12,usern,mail;
    public static int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12;
    public static int avid=0;
    static public data d;
    private static final String SELECT_SQL = "SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12 FROM profile";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p1=p2=p3=p4=p5=p6=p7=p8=p9=p10=p11=p12=0;
        enableLastSlideAlphaExitTransition(true);
        o1=o2=o3=o4=o5=o6=o7=o8=o9=o10=mail=usern="";
        d=new data();

        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        if(storagePath.exists()) {
            db = openOrCreateDatabase(storagePath + "/" + "prodb.db", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY, aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30),p1 integer,p2 integer,p3 integer,p4 integer,p5 integer,p6 integer,p7 integer,p8 integer,p9 integer,p10 integer,p11 integer,p12 integer);");
            c = db.rawQuery(SELECT_SQL, null);
            c.moveToFirst();
            if (c.getCount()>0) {
                usern = c.getString(0);
                avid = c.getInt(1);
                mail = c.getString(2);
                o1 = c.getString(3);p1=c.getInt(15);
                o2 = c.getString(4);p2=c.getInt(16);
                o3 = c.getString(5);p3=c.getInt(17);
                o4 = c.getString(6);p4=c.getInt(18);
                o5 = c.getString(7);p5=c.getInt(19);
                o6 = c.getString(8);p6=c.getInt(20);
                o7 = c.getString(9);p7=c.getInt(21);
                o8 = c.getString(10);p8=c.getInt(22);
                o9 = c.getString(11);p9=c.getInt(23);
                o10 = c.getString(12);p10=c.getInt(24);
                o11 = c.getString(13);p11=c.getInt(25);
                o12 = c.getString(14);p12=c.getInt(26);
                c.close();
            }

        }


        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });
            addslides();
    }


    public void OnSelected(Class<?> clazz, int i) {
        v1=(CircleImageView)findViewById(R.id.avatar);
        v2=(CircleImageView)findViewById(R.id.avatar2);
        v3=(CircleImageView)findViewById(R.id.avatar3);

        v1.setImageResource(i);

        v2.setImageResource(i);

        v3.setImageResource(i);
        d.setAid(i);
    }
    @Override
    public void onFinish() {
        super.onFinish();
    }

    public void addslides(){

        addSlide(new CustomSlide());
        addSlide(new CustomSlide2());
        addSlide(new CustomSlide3());
        addSlide(new CustomSlide4());

    }

}

