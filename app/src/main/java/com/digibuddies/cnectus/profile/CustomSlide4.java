package com.digibuddies.cnectus.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import com.digibuddies.cnectus.R;
import com.digibuddies.cnectus.questions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gospelware.liquidbutton.LiquidButton;

import java.io.File;

import agency.tango.materialintroscreen.SlideFragment;

public class CustomSlide4 extends SlideFragment {
    private CheckBox checkBox;
    private Spinner spinner;
    private String idd;
    SharedPreferences.Editor editor;
    SharedPreferences mPrefs;
    final String firsttime ="firsttime";
    int firstt;
    public LiquidButton liquidButton;
    Intent i;
    private SQLiteDatabase db;
    Button sv;
    int flag=1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide4, container, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        firstt = mPrefs.getInt(firsttime, 0);
        if (firstt==0){
            flag=0;
            editor = mPrefs.edit();
            editor.putInt(firsttime, 1);
            editor.commit();
        }
        liquidButton = (LiquidButton)view.findViewById(R.id.button);
        idd = Settings.Secure.getString(this.getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sv=(Button) view.findViewById(R.id.save);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liquidButton.startPour();
                sv.setText("Adding You To Family !");
                myRef.child(idd).setValue(profileclass.d);
                createDatabase();
                String query = "INSERT OR REPLACE INTO profile (id,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p01) VALUES(1,'"+profileclass.d.getAid()+"','"+profileclass.d.getEmail()+"','"+profileclass.d.getUname()+"','"+profileclass.d.getOp1()+"','"+profileclass.d.getOp2()+"','"+profileclass.d.getOp3()+"','"+profileclass.d.getOp4()+"','"+profileclass.d.getOp5()+"','"+profileclass.d.getOp6()+"','"+profileclass.d.getOp7()+"','"+profileclass.d.getOp8()+"','"+profileclass.d.getOp9()+"','"+profileclass.d.getOp10()+"','"+profileclass.d.getOp11()+"','"+profileclass.d.getOp12()+"','"+profileclass.d.getOp01()+"','"+profileclass.d.getP1()+"','"+profileclass.d.getP2()+"','"+profileclass.d.getP3()+"','"+profileclass.d.getP4()+"','"+profileclass.d.getP5()+"','"+profileclass.d.getP6()+"','"+profileclass.d.getP7()+"','"+profileclass.d.getP8()+"','"+profileclass.d.getP9()+"','"+profileclass.d.getP10()+"','"+profileclass.d.getP11()+"','"+profileclass.d.getP12()+"','"+profileclass.d.getP01()+"');";
            db.execSQL(query);

            }

        });

        liquidButton.setFillAfter(true);
        liquidButton.setAutoPlay(true);
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                if (flag == 0) {
                    i = new Intent(getActivity(), questions.class);
                    getActivity().finish();
                    startActivity(i);
                } else {
                    getActivity().finish();
                }
            }
            @Override
            public void onProgressUpdate(float progress) {
            }
        });
        checkBox = (CheckBox) view.findViewById(R.id.checkBox89);
        checkBox.setChecked(false);
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.fourth_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.fourth_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        return (true);
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message);
    }

    protected void createDatabase(){
        db=getActivity().openOrCreateDatabase(getActivity().getFilesDir().getAbsolutePath()+"prodb.db", Context.MODE_PRIVATE, null);
        db.execSQL(getString(R.string.prodb));
    }
}