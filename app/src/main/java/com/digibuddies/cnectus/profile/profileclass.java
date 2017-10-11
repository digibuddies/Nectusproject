package com.digibuddies.cnectus.profile;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.digibuddies.cnectus.R;

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
    public static String o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11,o12,o01,usern,mail;
    public static int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p01;
    public static int avid=0;
    static public data d;
    private String SELECT_SQL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p1=p2=p3=p4=p5=p6=p7=p8=p9=p10=p11=p12=p01=0;
        o1=o2=o3=o4=o5=o6=o7=o8=o9=o10=o01=mail=usern="";
        d=new data();

            db = openOrCreateDatabase(getFilesDir().getAbsolutePath()+"prodb.db", Context.MODE_PRIVATE, null);
            db.execSQL(getString(R.string.prodb));
            SELECT_SQL = getString(R.string.prosel);
            c = db.rawQuery(SELECT_SQL, null);
            c.moveToFirst();
            if (c.getCount()>0) {
                usern = c.getString(0);
                avid = c.getInt(1);
                mail = c.getString(2);
                o1 = c.getString(3);p1=c.getInt(16);
                o2 = c.getString(4);p2=c.getInt(17);
                o3 = c.getString(5);p3=c.getInt(18);
                o4 = c.getString(6);p4=c.getInt(19);
                o5 = c.getString(7);p5=c.getInt(20);
                o6 = c.getString(8);p6=c.getInt(21);
                o7 = c.getString(9);p7=c.getInt(22);
                o8 = c.getString(10);p8=c.getInt(23);
                o9 = c.getString(11);p9=c.getInt(24);
                o10 = c.getString(12);p10=c.getInt(25);
                o11 = c.getString(13);p11=c.getInt(26);
                o12 = c.getString(14);p12=c.getInt(27);
                o01 = c.getString(15);p01=c.getInt(28);
                c.close();
            }
                db.close();


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

    @Override
    protected void onResume() {
        super.onResume();

    }
}

