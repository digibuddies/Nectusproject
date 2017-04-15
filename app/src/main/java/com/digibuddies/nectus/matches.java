package com.digibuddies.nectus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import com.devs.squaremenu.OnMenuClickListener;
//import com.devs.squaremenu.SquareMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whygraphics.gifview.gif.GIFView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class matches extends AppCompatActivity {
    final ArrayList<String> datadb = new ArrayList<>();
    final ArrayList<String> datadb2 = new ArrayList<>();
    final ArrayList<String> datadb3 = new ArrayList<>();
    final ArrayList<String> datadb4 = new ArrayList<>();
    final ArrayList<String> datadb5 = new ArrayList<>();
    final ArrayList<String> datadb6 = new ArrayList<>();
    final ArrayList<String> datadb7 = new ArrayList<>();
    final ArrayList<String> datadb8 = new ArrayList<>();
    final ArrayList<String> datadb9 = new ArrayList<>();
    final ArrayList<String> datadb10 = new ArrayList<>();
    final ArrayList<String> datadb11= new ArrayList<>();
    final ArrayList<String> datadb12= new ArrayList<>();
    final ArrayList<String> devid= new ArrayList<>();
    final ArrayList<Integer> aid = new ArrayList<>();
    final ArrayList<String> mail = new ArrayList<>();
    final ArrayList<String> userid = new ArrayList<>();
    final ArrayList<String> mp = new ArrayList<>();
    final ArrayList<String> allow = new ArrayList<>();
    StickySwitch ssw;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView und;
    int flaga=0;
    TextView tv6,contmail;
    TextView tv,d1,d2,d3,cy,cn;
    ScrollView sv;
    AnimationDrawable anim;
    CircleImageView p1,p2,p3;
    Button b1;
    private static final String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,mp,devid,allow FROM matches";
    private SQLiteDatabase db;
    private Cursor c,d;
    String id;
    int flag1=0,flag2=0,flag3=0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        id=thebackservice.idd;
        b1=(Button)findViewById(R.id.b1);
        sv=(ScrollView)findViewById(R.id.sv);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        Button back=(Button)dialog.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        und=(TextView)dialog.findViewById(R.id.und);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cy.setText(datadb11.get(0));
                cn.setText(datadb12.get(0));
                dialog.show();
            }
        });
        contmail=(TextView)dialog.findViewById(R.id.cont);
        cy=(TextView)dialog.findViewById(R.id.cy);
        cn=(TextView)dialog.findViewById(R.id.cn);
        createDatabase();
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();

        if (devid.size()>0){
        myRef.child(devid.get(0)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().equals("RIGHT")){
                    flaga=1;
                    if(ssw.getDirection().name().equals("RIGHT")){
                    contmail.setText(mail.get(0));}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}

        ssw=(StickySwitch)findViewById(R.id.ss);


        ssw.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                        if(direction.name().equals("RIGHT")){
                            if(flaga==1){
                                contmail.setText(mail.get(0));
                            }
                            String qer = "UPDATE matches SET allow='RIGHT' WHERE id=1;";
                            db.execSQL(qer);
                            myRef.child(id).child(devid.get(0)).setValue("RIGHT");
                        }
                else if(direction.name().equals("LEFT")){
                            String qer = "UPDATE matches SET allow='LEFT' WHERE id=1;";
                            db.execSQL(qer);
                            myRef.child(id).child(devid.get(0)).setValue("LEFT");
                        }

                 }
        });

        p1=(CircleImageView)findViewById(R.id.p1);
        p2=(CircleImageView)findViewById(R.id.p2);
        p3=(CircleImageView)findViewById(R.id.p3);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv11);
        tv5 = (TextView) findViewById(R.id.tv22);
        tv6 = (TextView) findViewById(R.id.tv33);
        d1=(TextView)findViewById(R.id.detail1);
        d2=(TextView)findViewById(R.id.detail2);
        d3=(TextView)findViewById(R.id.detail3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");

        d1.setTypeface(custom_font);
        d2.setTypeface(custom_font);
        d3.setTypeface(custom_font);

        callme2();
        /*
        SquareMenu mSquareMenu = (SquareMenu) findViewById(R.id.square_menu);
        mSquareMenu.setOnMenuClickListener(new OnMenuClickListener(){
            @Override
            public void onMenuOpen() {  }
            @Override
            public void onMenuClose() { }
            @Override
            public void onClickMenu1() {Intent intent = new Intent(matches.this,profileclass.class);
                startActivity(intent); }
            @Override
            public void onClickMenu2() {Intent intent = new Intent(matches.this,questions.class);
                startActivity(intent); }
            @Override
            public void onClickMenu3() {Intent intent = new Intent(matches.this,profileclass.class);
                startActivity(intent); }
        });
        */
    }

    public void callme2() {
        if(datadb.size()>0) {
            tv4.setText(mp.get(0)+"% Similar");
            tv5.setText(mp.get(1)+"% Similar");
            tv6.setText(mp.get(2)+"% Similar");
            tv1.setText(userid.get(0));und.setText(userid.get(0));
            tv2.setText(userid.get(1));
            tv3.setText(userid.get(2));
            p1.setImageResource(aid.get(0));
            p2.setImageResource(aid.get(1));
            p3.setImageResource(aid.get(2));
            d1.setText(datadb.get(0)+", you can call me "+userid.get(0)+". I am here since "+datadb2.get(0)+". Currently I am working towards "+datadb3.get(0)+" and i love to practice my "+datadb4.get(0)+" skills. I would like to "+datadb5.get(0)+" someday. My friends say i'm "+datadb6.get(0)+". I just "+datadb7.get(0)+" "+datadb8.get(0)+" I spend most of my day "+datadb9.get(0)+". A person with same mind as mine would be "+datadb10.get(0)+".");
            d2.setText(datadb.get(1)+", you can call me "+userid.get(1)+". I am here since "+datadb2.get(1)+". Currently I am working towards "+datadb3.get(1)+" and i love to practice my "+datadb4.get(1)+" skills. I would like to "+datadb5.get(1)+" someday. My friends say i'm "+datadb6.get(1)+". I just "+datadb7.get(1)+" "+datadb8.get(1)+" I spend most of my day "+datadb9.get(1)+". A person with same mind as mine would be "+datadb10.get(1)+".");
            d3.setText(datadb.get(2)+", you can call me "+userid.get(2)+". I am here since "+datadb2.get(2)+". Currently I am working towards "+datadb3.get(2)+" and i love to practice my "+datadb4.get(2)+" skills. I would like to "+datadb5.get(2)+" someday. My friends say i'm "+datadb6.get(2)+". I just "+datadb7.get(2)+" "+datadb8.get(2)+" I spend most of my day "+datadb9.get(2)+". A person with same mind as mine would be "+datadb10.get(2)+".");

        }
    }


    protected void showRecords() {
        if(c.getCount()>0) {
            do {
                userid.add(c.getString(0));
                aid.add(c.getInt(1));
                mail.add(c.getString(2));
                datadb.add(c.getString(3));
                datadb2.add(c.getString(4));
                datadb3.add(c.getString(5));
                datadb4.add(c.getString(6));
                datadb5.add(c.getString(7));
                datadb6.add(c.getString(8));
                datadb7.add(c.getString(9));
                datadb8.add(c.getString(10));
                datadb9.add(c.getString(11));
                datadb10.add(c.getString(12));
                datadb11.add(c.getString(13));
                datadb12.add(c.getString(14));
                mp.add(c.getString(15));
                devid.add(c.getString(16));
                allow.add(c.getString(17));

            } while (c.moveToNext());
            c.close();
        }

    }


    protected void createDatabase() {
        File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
        if(storagePath.exists()) {
            db = openOrCreateDatabase(storagePath + "/" + "PersonDB", Context.MODE_PRIVATE, null);
        }
    }


    // Starting animation:- start the animation on onResume.
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    @Override
    protected void onResumeFragments() {
        if (allow.size()>0){
            if(allow.get(0).equals("RIGHT")){
                ssw.setDirection(StickySwitch.Direction.RIGHT);}
            else if(allow.get(0).equals("LEFT")){
                ssw.setDirection(StickySwitch.Direction.LEFT);
            }}
        super.onResumeFragments();
    }
}
