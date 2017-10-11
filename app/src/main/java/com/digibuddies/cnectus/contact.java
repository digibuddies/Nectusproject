package com.digibuddies.cnectus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import io.ghyeok.stickyswitch.widget.StickySwitch;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class contact extends AppCompatActivity {

    SQLiteDatabase db,db2;
    Cursor c;
    FloatingTextButton sv;
    String mp;
    TextView tv1,tv2,tv3,ccr;
    CircleImageView av;
    String x;
    StickySwitch ssc;
    String cid;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s01,mail,uname,dvid,u2,u3;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii=getIntent();
        x=ii.getStringExtra("id");
        Log.d("iddd",x);
        setContentView(R.layout.activity_contact);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        cid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        createDatabase();
        tv1 = (TextView) findViewById(R.id.matchc);
        tv2=(TextView)findViewById(R.id.kuser);
        tv3=(TextView)findViewById(R.id.kdetail);
        sv=(FloatingTextButton) findViewById(R.id.save);
        ssc=(StickySwitch)findViewById(R.id.ssc);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ssc.getDirection().name().equals("RIGHT")){
                    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                    myRef.child("contact").child(cid).child(dvid).child("request").setValue("ACCEPTED");
                    myRef.child("contact").child(dvid).child(cid).child("request").setValue("ACCEPTED");
                    String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('"+dvid+"','"+currentDateTime+"','"+mp+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"','"+s01+"');";
                    db2.execSQL(queryx);
                    db.execSQL("DELETE FROM matches WHERE devid='"+dvid+"'");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SQLiteDatabase kdm = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "prodb.db", Context.MODE_PRIVATE, null);
                            c = kdm.rawQuery("SELECT uname FROM profile", null);
                            c.moveToFirst();
                            String usn = c.getString(0);
                            c.close();
                            kdm.close();
                            myRef.child("chat").child(dvid).child(cid).push().setValue(new chatmessage(getString(R.string.hi),usn,"UNREAD"));
                            myRef.child("chat").child(cid).child(dvid).push().setValue(new chatmessage(getString(R.string.hi),usn,"READ"));

                        }
                    },5000);
                }
                db.close();
                db2.close();
                Intent i=new Intent(contact.this,connections.class);
                startActivity(i);
                finish();
            }
        });
        av=(CircleImageView)findViewById(R.id.kav);
        av.setImageResource(aid);
        if(!mp.equals(" ")){
            tv1.setText(mp+"% similar");}
        else {
            tv1.setText(" ");}
        tv2.setText(uname);
        ccr=(TextView)findViewById(R.id.ccr);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
        tv3.setTypeface(custom_font);
        ccr.setTypeface(custom_font);


        tv3.setText(s1+", you can call me "+uname+". I\'m a "+s01+" born in "+s2+" and currently I live in "+s3+". I love to practice my "+s4+" skills. I would like to "+s5+" someday. My friends say I'm "+s6+". I just love "+s7+" and i hate "+s8+" I spend most of my day "+s9+". A person with same mind as mine would be "+s10+".");



    }

    protected void createDatabase(){

        db=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"reqDB", Context.MODE_PRIVATE, null);
        db2=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"ContDB", Context.MODE_PRIVATE, null);
        db2.execSQL(getString(R.string.condb));
        String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01,mp,devid FROM matches where devid='"+x+"'";
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        if(c.getCount()>0){
            uname=c.getString(0);
            aid=c.getInt(1);
            mail = c.getString(2);
            s1=c.getString(3);
            s2=c.getString(4);
            s3=c.getString(5);
            s4=c.getString(6);
            s5=c.getString(7);
            s6=c.getString(8);
            s7=c.getString(9);
            s8=c.getString(10);
            s9=c.getString(11);
            s10=c.getString(12);
            s11=c.getString(13);
            s12=c.getString(14);
            s01=c.getString(15);
            mp=c.getString(16);
            dvid=c.getString(17);
        }
        c.close();
    }
    public void onBackPressed() {
        db.close();
        db2.close();
        finish();


    }

}
