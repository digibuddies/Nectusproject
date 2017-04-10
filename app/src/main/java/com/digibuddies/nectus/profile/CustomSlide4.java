package com.digibuddies.nectus.profile;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.digibuddies.nectus.MainActivity;
import com.digibuddies.nectus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gospelware.liquidbutton.LiquidButton;

import java.io.File;

import agency.tango.materialintroscreen.SlideFragment;

public class CustomSlide4 extends SlideFragment {
    private CheckBox checkBox;
    private Spinner spinner;
    private String idd;
    public LiquidButton liquidButton;
    private SQLiteDatabase db;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide4, container, false);
        liquidButton = (LiquidButton)view.findViewById(R.id.button);

        idd = Settings.Secure.getString(this.getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        final TextView tv3=(TextView)view.findViewById(R.id.textView3);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liquidButton.startPour();
                tv3.setText("Adding You To Family !");
                myRef.child(idd).setValue(profileclass.d);
                createDatabase();
                String query = "INSERT OR REPLACE INTO profile (id,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES(1,'"+profileclass.d.getAid()+"','"+profileclass.d.getEmail()+"','"+profileclass.d.getUname()+"','"+profileclass.d.getOp1()+"','"+profileclass.d.getOp2()+"','"+profileclass.d.getOp3()+"','"+profileclass.d.getOp4()+"','"+profileclass.d.getOp5()+"','"+profileclass.d.getOp6()+"','"+profileclass.d.getOp7()+"','"+profileclass.d.getOp8()+"','"+profileclass.d.getOp9()+"','"+profileclass.d.getOp10()+"','"+profileclass.d.getOp11()+"','"+profileclass.d.getOp12()+"');";
            db.execSQL(query);

            }

        });

        liquidButton.setFillAfter(true);
        liquidButton.setAutoPlay(true);
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                MainActivity.call=11;
               getActivity().finish();
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
        File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }
        db=getActivity().openOrCreateDatabase(storagePath+"/"+"prodb.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY, aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(20),op12 VARCHAR(20));");
    }
}