package com.digibuddies.nectus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class contact extends AppCompatActivity {

    SQLiteDatabase db,db2;
    Cursor c;
    Button sv;
    String usid,mp,idc;
    data snap;
    TextView tv1,tv2,tv3;
    CircleImageView av;
    int x=102;
    StickySwitch ssc;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,mail,uname,u1,u2,u3;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        createDatabase();
        tv1 = (TextView) findViewById(R.id.matchc);
        tv2=(TextView)findViewById(R.id.userc);
        tv3=(TextView)findViewById(R.id.detailc);
        sv=(Button)findViewById(R.id.save);
        ssc=(StickySwitch)findViewById(R.id.ssc);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ssc.getDirection().name().equals("RIGHT")){
                    String queryx = "INSERT OR REPLACE INTO connect (id,devid,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+1+"','"+usid+"','"+mp+"','"+aid+"','"+mail+"','"+uname+"','"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"');";
                    db2.execSQL(queryx);
                }
                db.execSQL("DELETE FROM matches WHERE id='"+x+"'");
                db.execSQL("UPDATE matches set id='"+(x)+"' WHERE id='"+(x+1)+"'");
                db.close();
                db2.close();
                finish();
            }
        });
        av=(CircleImageView)findViewById(R.id.avc);
        av.setImageResource(aid);
        tv1.setText(mp+"% similar");
        tv2.setText(uname);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");
        tv3.setTypeface(custom_font);


        tv3.setText(s1+", you can call me "+uname+". I am here since "+s2+". Currently I am working towards "+s3+" and i love to practice my "+s4+" skills. I would like to "+s5+" someday. My friends say i'm "+s6+". I just "+s7+" "+s8+" I spend most of my day "+s9+". A person with same mind as mine would be "+s10+".");



    }

    protected void createDatabase(){
        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }
        db=openOrCreateDatabase(storagePath+"/"+"PerDB", Context.MODE_PRIVATE, null);
        db2=openOrCreateDatabase(storagePath+"/"+"ContDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS connect(id INTEGER PRIMARY KEY,devid VARCHAR(20),mp varchar(20), aid INTEGER, email VARCHAR(20),allow varchar(20) default 'LEFT', uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");
        String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,mp,devid,allow FROM matches where id='"+x+"'";
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
            mp=c.getString(15);

        }
        c.close();
    }
}
