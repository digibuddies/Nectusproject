package com.digibuddies.nectus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import com.devs.squaremenu.OnMenuClickListener;
//import com.devs.squaremenu.SquareMenu;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.bvapp.arcmenulibrary.widget.ObservableScrollView;
import com.digibuddies.nectus.profile.profileclass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
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
    StickySwitch ssw,ssw2,ssw3;
    int qcount;

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
    private static final int[] ITEM_DRAWABLES = { R.drawable.icon_home,
            R.drawable.face, R.drawable.help };

    private static final String[] STR = {"Home","Profile","Questions"};

    CircleImageView p1,p2,p3;
    Button b1,b2,b3;
    private static final String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,mp,devid,allow FROM matches";
    private SQLiteDatabase db;
    private Cursor c,d;
    String id;
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

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog);
        final Dialog dialog3 = new Dialog(this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(R.layout.dialog);


        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        cy=(TextView)dialog.findViewById(R.id.cy);
        cn=(TextView)dialog.findViewById(R.id.cn);

        cy2=(TextView)dialog2.findViewById(R.id.cy);
        cn2=(TextView)dialog2.findViewById(R.id.cn);

        cy3=(TextView)dialog3.findViewById(R.id.cy);
        cn3=(TextView)dialog3.findViewById(R.id.cn);
        TextView front=(TextView)findViewById(R.id.front);
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
            menu.addItem(item, STR[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        Intent intent=new Intent(matches.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==1){
                        Intent intent=new Intent(matches.this,profileclass.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==2){
                        Intent intent=new Intent(matches.this,questions.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        createDatabase00();
        if(qcount>60){
            sv.setVisibility(View.VISIBLE);
            front.setVisibility(View.INVISIBLE);

        Button back=(Button)dialog.findViewById(R.id.back);
        Button back2=(Button)dialog2.findViewById(R.id.back);
        Button back3=(Button)dialog3.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
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

        contmail=(TextView)dialog.findViewById(R.id.cont);
        contmail2=(TextView)dialog2.findViewById(R.id.cont);
        contmail3=(TextView)dialog3.findViewById(R.id.cont);


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
        });
            myRef.child(devid.get(1)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().equals("RIGHT")){
                        flagb=1;
                        if(ssw2.getDirection().name().equals("RIGHT")){
                            contmail2.setText(mail.get(1));}
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
                myRef.child(devid.get(2)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().equals("RIGHT")) {
                                flagc = 1;
                                if (ssw3.getDirection().name().equals("RIGHT")) {
                                    contmail.setText(mail.get(2));
                                }
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


        ssw=(StickySwitch)findViewById(R.id.ss);
        ssw2=(StickySwitch)findViewById(R.id.ss2);
        ssw3=(StickySwitch)findViewById(R.id.ss3);


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
        ssw2.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if(direction.name().equals("RIGHT")){
                    if(flagb==1){
                        contmail2.setText(mail.get(1));
                    }
                    String qer = "UPDATE matches SET allow='RIGHT' WHERE id=2;";
                    db.execSQL(qer);
                    myRef.child(id).child(devid.get(1)).setValue("RIGHT");
                }
                else if(direction.name().equals("LEFT")){
                    String qer = "UPDATE matches SET allow='LEFT' WHERE id=2;";
                    db.execSQL(qer);
                    myRef.child(id).child(devid.get(1)).setValue("LEFT");
                }

            }
        });
        ssw3.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if(direction.name().equals("RIGHT")){
                    if(flagc==1){
                        contmail3.setText(mail.get(2));
                    }
                    String qer = "UPDATE matches SET allow='RIGHT' WHERE id=3;";
                    db.execSQL(qer);
                    myRef.child(id).child(devid.get(2)).setValue("RIGHT");
                }
                else if(direction.name().equals("LEFT")){
                    String qer = "UPDATE matches SET allow='LEFT' WHERE id=3;";
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
        File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
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
