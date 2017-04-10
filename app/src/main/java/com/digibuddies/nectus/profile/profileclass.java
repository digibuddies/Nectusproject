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
    public static int avid=0;
    static public data d;
    private static final String SELECT_SQL = "SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10 FROM profile";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableLastSlideAlphaExitTransition(true);
        o1=o2=o3=o4=o5=o6=o7=o8=o9=o10=mail=usern="";
        d=new data();
        v1=(CircleImageView)findViewById(R.id.avatar);
        v2=(CircleImageView)findViewById(R.id.avatar2);
        v3=(CircleImageView)findViewById(R.id.avatar3);
        File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
        if(storagePath.exists()) {
            db = openOrCreateDatabase(storagePath + "/" + "prodb.db", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY, aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");
            c = db.rawQuery(SELECT_SQL, null);
            c.moveToFirst();
            if (c.getCount()>=14) {
                usern = c.getString(0);
                avid = c.getInt(1);
                mail = c.getString(2);
                o1 = c.getString(3);
                o2 = c.getString(4);
                o3 = c.getString(5);
                o4 = c.getString(6);
                o5 = c.getString(7);
                o6 = c.getString(8);
                o7 = c.getString(9);
                o8 = c.getString(10);
                o9 = c.getString(11);
                o10 = c.getString(12);
                o11 = c.getString(13);
                o12 = c.getString(14);
                c.close();
            }
            if(avid!=0){
                v1.setImageResource(avid);v2.setImageResource(avid);v3.setImageResource(avid);

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

