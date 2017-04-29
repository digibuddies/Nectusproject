package com.digibuddies.cnectus;

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
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
//import com.devs.squaremenu.OnMenuClickListener;
//import com.devs.squaremenu.SquareMenu;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.bvapp.arcmenulibrary.widget.ObservableScrollView;
import com.digibuddies.cnectus.profile.profileclass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    StickySwitch ssw,ssw2,ssw3;
    int qcount;
    SQLiteDatabase dbc;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView und,und2,und3;
    int flaga=0,flagb=0,flagc=0;
    TextView tv6,contmail,contmail2,contmail3,front;
    TextView tv,d1,d2,d3,cy,cn,cy2,cn2,cy3,cn3;
    ObservableScrollView sv;
    AnimationDrawable anim;
    private static final int[] ITEM_DRAWABLES = { R.drawable.face,
            R.drawable.help, R.drawable.connect,R.drawable.home };

    private static final String[] STR = {"Home","Profile","Questions"};

    CircleImageView p1,p2,p3;
    Button b1,b2,b3;
    private static final String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,mp,devid,allow FROM matches";
    private SQLiteDatabase db;
    private Cursor c,d;
    String id;
    String currentDateTime;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog);
        final Dialog dialog3 = new Dialog(this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(R.layout.dialog);
        final TextView kdate=(TextView)dialog.findViewById(R.id.kdate);
        final TextView kdate2=(TextView)dialog2.findViewById(R.id.kdate);
        final TextView kdate3=(TextView)dialog3.findViewById(R.id.kdate);


        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);

        currentDateTime = DateFormat.getDateTimeInstance().format(new Date());



        ssw=(StickySwitch)dialog.findViewById(R.id.dss);
        ssw2=(StickySwitch)dialog2.findViewById(R.id.dss);
        ssw3=(StickySwitch)dialog3.findViewById(R.id.dss);


        cy=(TextView)dialog.findViewById(R.id.cy);
        cn=(TextView)dialog.findViewById(R.id.cn);

        cy2=(TextView)dialog2.findViewById(R.id.cy);
        cn2=(TextView)dialog2.findViewById(R.id.cn);

        cy3=(TextView)dialog3.findViewById(R.id.cy);
        cn3=(TextView)dialog3.findViewById(R.id.cn);
        final TextView front=(TextView)findViewById(R.id.front);
        sv=(ObservableScrollView) findViewById(R.id.sv);
        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.attachToScrollView(sv);
        menu.setMinRadius(60);
        menu.showTooltip(false);
        menu.setAnim(300,300,ArcMenu.ANIM_MIDDLE_TO_RIGHT,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE,ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            FloatingActionButton item = new FloatingActionButton(this);  //Use internal fab as a child
            item.setSize(FloatingActionButton.SIZE_MINI);  //set minimum size for fab 42dp
            item.setShadow(true); //enable to draw shadow
            item.setIcon(ITEM_DRAWABLES[i]); //add icon for fab
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.first_slide_background));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;
            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        Intent intent=new Intent(matches.this,profileclass.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==1){
                        Intent intent=new Intent(matches.this,questions.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==2){
                        Intent intent=new Intent(matches.this,connections.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==3){
                        Intent intent=new Intent(matches.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
        final TextView hd=(TextView)findViewById(R.id.alike);
        final TextView detp=(TextView)findViewById(R.id.detp);
        hd.setTypeface(custom_font);
        detp.setTypeface(custom_font);


        createDatabase00();

        createDatabase();
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();
        if(qcount>70&&userid.size()>0){
            sv.setVisibility(View.VISIBLE);
            front.setVisibility(View.INVISIBLE);

        Button back=(Button)dialog.findViewById(R.id.back);
        Button back2=(Button)dialog2.findViewById(R.id.back);
        Button back3=(Button)dialog3.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ssw.getDirection().name().equals("RIGHT")){
                    myRef.child(devid.get(0)).child(id).child("request").setValue("RIGHT");
                    myRef.child(devid.get(0)).child(id).child("mp").setValue(mp.get(0));
                    String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+devid.get(0)+"','"+currentDateTime+"','"+mp.get(0)+"','"+aid.get(0)+"','"+mail.get(0)+"','"+userid.get(0)+"','"+datadb.get(0)+"','"+datadb2.get(0)+"','"+datadb3.get(0)+"','"+datadb4.get(0)+"','"+datadb5.get(0)+"','"+datadb6.get(0)+"','"+datadb7.get(0)+"','"+datadb8.get(0)+"','"+datadb9.get(0)+"','"+datadb10.get(0)+"','"+datadb11.get(0)+"','"+datadb12.get(0)+"');";
                    dbc.execSQL(queryx);


                }
                else if(ssw.getDirection().name().equals("LEFT")){
                    myRef.child(devid.get(0)).child(id).child("request").setValue("LEFT");
                    myRef.child(devid.get(0)).child(id).child("mp").setValue(mp.get(0));
                }
                dialog.dismiss();
                if(ssw.getDirection().name().equals("RIGHT")){
                    Snackbar.make(sv, "Request Sent!",
                            Snackbar.LENGTH_SHORT).show();} }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ssw2.getDirection().name().equals("RIGHT")){
                    myRef.child(devid.get(1)).child(id).child("request").setValue("RIGHT");
                    myRef.child(devid.get(1)).child(id).child("mp").setValue(mp.get(1));
                    String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+devid.get(1)+"','"+currentDateTime+"','"+mp.get(1)+"','"+aid.get(1)+"','"+mail.get(1)+"','"+userid.get(1)+"','"+datadb.get(1)+"','"+datadb2.get(1)+"','"+datadb3.get(1)+"','"+datadb4.get(1)+"','"+datadb5.get(1)+"','"+datadb6.get(1)+"','"+datadb7.get(1)+"','"+datadb8.get(1)+"','"+datadb9.get(1)+"','"+datadb10.get(1)+"','"+datadb11.get(1)+"','"+datadb12.get(1)+"');";
                    dbc.execSQL(queryx);
                }
                else if(ssw2.getDirection().name().equals("LEFT")){
                    myRef.child(devid.get(1)).child(id).child("request").setValue("LEFT");
                    myRef.child(devid.get(1)).child(id).child("mp").setValue(mp.get(1));  }

                dialog2.dismiss();
                if(ssw2.getDirection().name().equals("RIGHT")){
                    Snackbar.make(sv, "Request Sent!",
                            Snackbar.LENGTH_SHORT).show();}
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ssw3.getDirection().name().equals("RIGHT")){
                    myRef.child(devid.get(2)).child(id).child("request").setValue("RIGHT");
                    myRef.child(devid.get(2)).child(id).child("mp").setValue(mp.get(2));
                    String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+devid.get(2)+"','"+currentDateTime+"','"+mp.get(2)+"','"+aid.get(2)+"','"+mail.get(2)+"','"+userid.get(2)+"','"+datadb.get(2)+"','"+datadb2.get(2)+"','"+datadb3.get(2)+"','"+datadb4.get(2)+"','"+datadb5.get(2)+"','"+datadb6.get(2)+"','"+datadb7.get(2)+"','"+datadb8.get(2)+"','"+datadb9.get(2)+"','"+datadb10.get(2)+"','"+datadb11.get(2)+"','"+datadb12.get(2)+"');";
                    dbc.execSQL(queryx);

                }
                else if(ssw3.getDirection().name().equals("LEFT")){
                    myRef.child(devid.get(2)).child(id).child("request").setValue("LEFT");
                    myRef.child(devid.get(2)).child(id).child("mp").setValue(mp.get(2)); }
                dialog3.dismiss();
                if(ssw3.getDirection().name().equals("RIGHT")){
                Snackbar.make(sv, "Request Sent!",
                        Snackbar.LENGTH_SHORT).show();}
            }
        });

        und=(TextView)dialog.findViewById(R.id.und);
        und2=(TextView)dialog2.findViewById(R.id.und);
        und3=(TextView)dialog3.findViewById(R.id.und);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cy.setText(datadb11.get(0));
                cn.setText(datadb12.get(0));
                dialog.show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cy2.setText(datadb11.get(1));
                cn2.setText(datadb12.get(1));
                dialog2.show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cy3.setText(datadb11.get(2));
                cn3.setText(datadb12.get(2));
                dialog3.show();
            }
        });

        ssw.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if(direction.name().equals("RIGHT")){
                    String qer = "UPDATE matches SET allow='RIGHT' WHERE id=1;";
                    db.execSQL(qer);
                }
                else if(direction.name().equals("LEFT")){
                    String qer = "UPDATE matches SET allow='LEFT' WHERE id=1;";
                    db.execSQL(qer);
            }

                 }
        });
        ssw2.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if(direction.name().equals("RIGHT")){
                    String qer = "UPDATE matches SET allow='RIGHT' WHERE id=2;";
                    db.execSQL(qer);
                }
                else if(direction.name().equals("LEFT")){
                    String qer = "UPDATE matches SET allow='LEFT' WHERE id=2;";
                    db.execSQL(qer);}
            }
        });
        ssw3.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if(direction.name().equals("RIGHT")){
                    String qer = "UPDATE matches SET allow='RIGHT' WHERE id=3;";
                    db.execSQL(qer);

                }
                else if(direction.name().equals("LEFT")) {
                    String qer = "UPDATE matches SET allow='LEFT' WHERE id=3;";
                    db.execSQL(qer);
                }
            }
        });

        p1=(CircleImageView)findViewById(R.id.kav);
        p2=(CircleImageView)findViewById(R.id.p2);
        p3=(CircleImageView)findViewById(R.id.p3);
        tv1 = (TextView) findViewById(R.id.kuser);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv11);
        tv5 = (TextView) findViewById(R.id.tv22);
        tv6 = (TextView) findViewById(R.id.tv33);
        d1=(TextView)findViewById(R.id.kdetail);
        d2=(TextView)findViewById(R.id.detail2);
        d3=(TextView)findViewById(R.id.detail3);
        d1.setTypeface(custom_font);
        d2.setTypeface(custom_font);
        d3.setTypeface(custom_font);
            kdate.setTypeface(custom_font);
            kdate2.setTypeface(custom_font);
            kdate3.setTypeface(custom_font);


        callme2();

    }}



    public void callme2() {
        if(datadb.size()>0) {
            tv4.setText(mp.get(0)+"% Similar");
            tv5.setText(mp.get(1)+"% Similar");
            tv6.setText(mp.get(2)+"% Similar");
            tv1.setText(userid.get(0));und.setText(userid.get(0));
            tv2.setText(userid.get(1));und2.setText(userid.get(1));
            tv3.setText(userid.get(2));und3.setText(userid.get(2));
            p1.setImageResource(aid.get(0));
            p2.setImageResource(aid.get(1));
            p3.setImageResource(aid.get(2));
            d1.setText(datadb.get(0)+", you can call me "+userid.get(0)+". I was born in "+datadb2.get(0)+". Currently I am living in "+datadb3.get(0)+" and I love to practice my "+datadb4.get(0)+" skills. I would like to "+datadb5.get(0)+" someday. My friends say I'm "+datadb6.get(0)+". I just "+datadb7.get(0)+" "+datadb8.get(0)+" I spend most of my day "+datadb9.get(0)+". A person with same mind as mine would be "+datadb10.get(0)+".");
            d2.setText(datadb.get(1)+", you can call me "+userid.get(1)+". I was born in "+datadb2.get(1)+". Currently I am living in "+datadb3.get(1)+" and I love to practice my "+datadb4.get(1)+" skills. I would like to "+datadb5.get(1)+" someday. My friends say I'm "+datadb6.get(1)+". I just "+datadb7.get(1)+" "+datadb8.get(1)+" I spend most of my day "+datadb9.get(1)+". A person with same mind as mine would be "+datadb10.get(1)+".");
            d3.setText(datadb.get(2)+", you can call me "+userid.get(2)+". I was born in "+datadb2.get(2)+". Currently I am living in "+datadb3.get(2)+" and I love to practice my "+datadb4.get(2)+" skills. I would like to "+datadb5.get(2)+" someday. My friends say I'm "+datadb6.get(2)+". I just "+datadb7.get(2)+" "+datadb8.get(2)+" I spend most of my day "+datadb9.get(2)+". A person with same mind as mine would be "+datadb10.get(2)+".");

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
        File storagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }
            db = openOrCreateDatabase(storagePath+"/"+"PerDB", Context.MODE_PRIVATE, null);
            dbc=openOrCreateDatabase(storagePath+"/"+"ContDB", Context.MODE_PRIVATE, null);
            dbc.execSQL("CREATE TABLE IF NOT EXISTS connect(dvid VARCHAR(20) PRIMARY KEY,time VARCHAR(20),mp varchar(20), aid INTEGER, email VARCHAR(20),uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");

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
            }
            if(allow.get(1).equals("RIGHT")){
                ssw2.setDirection(StickySwitch.Direction.RIGHT);}
            else if(allow.get(1).equals("LEFT")){
                ssw2.setDirection(StickySwitch.Direction.LEFT);
            }
            if(allow.get(2).equals("RIGHT")){
                ssw3.setDirection(StickySwitch.Direction.RIGHT);}
            else if(allow.get(2).equals("LEFT")){
                ssw3.setDirection(StickySwitch.Direction.LEFT);
            }}
        createDatabase00();
        super.onResumeFragments();
    }

    public void createDatabase00(){
        File storagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        SQLiteDatabase db2;
        db2=openOrCreateDatabase(storagePath+"/"+"counters", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS counter1(id integer primary key, count INTEGER);");
        c = db2.rawQuery("SELECT count FROM counter1;", null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.moveToFirst();
            qcount=c.getInt(0);
            Log.d("countmatch", String.valueOf(qcount));
        }
        c.close();
        db2.close();
    }

}
