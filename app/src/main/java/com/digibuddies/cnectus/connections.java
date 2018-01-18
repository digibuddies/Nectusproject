package com.digibuddies.cnectus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.digibuddies.cnectus.profile.profileclass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class connections extends AppCompatActivity {
    static SQLiteDatabase kdb;
    SQLiteDatabase dbr;
    RecyclerView rv,reqrec;
    Cursor d,c;
    reqadapter rqa;
    data tdata;
    Dialog dnew2,dreq;
    Button imb2,clos;
    List<data> cdata = new ArrayList<data>();
    List<data> kdata = new ArrayList<data>();
    static Adapter adapter;
    TextView ckcon,emp;
    Button newreq;
    SharedPreferences.Editor editor;
    SharedPreferences mPrefs;
    final String firsttime ="firsttime";
    int firstt;
    Intent intent0;
    Button req;
    int flag=0,flag2=0;
    public Typeface custom_font;
    public String kid,usn;
    private int[] ITEM_DRAWABLES = { R.drawable.help, R.drawable.dice,R.drawable.group, R.drawable.home };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        kid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        firstt = mPrefs.getInt(firsttime, 0);
        if (firstt==3){
            flag=1;
            editor = mPrefs.edit();
            editor.putInt(firsttime,4);
            editor.apply();
        }
        dnew2=new Dialog(this);
        dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew2.setContentView(R.layout.dialogque);
        imb2 = (Button)dnew2.findViewById(R.id.close);
        TextView dt = (TextView) dnew2.findViewById(R.id.dtitle);
        TextView ex=(TextView)dnew2.findViewById(R.id.ex);
        TextView dd=(TextView)dnew2.findViewById(R.id.ddet);
        ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
        dback.setImageResource(R.drawable.zzzz);
        dt.setText(getString(R.string.cd1));
        ex.setText(getString(R.string.cd2));
        ex.setVisibility(View.VISIBLE);
        dd.setText(getString(R.string.cd3));
        imb2.setText(getString(R.string.cd4));
        imb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
               startActivity(new Intent(connections.this,group.class));

            }
        });
        if(flag==1){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if(!connections.this.isFinishing()) {
                        dnew2.show();
                    }
                }

            }, 2500);

        }
        rv=(RecyclerView)findViewById(R.id.krv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.attachToRecyclerView(rv);
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
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.dark_blue));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;
            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(position==0){
                        intent0=new Intent(connections.this,questions.class);
                        finish();
                        startActivity(intent0);


                    }
                    if(position==1){
                       intent0=new Intent(connections.this,matches.class);
                        intent0.putExtra("target","random");
                        finish();
                        startActivity(intent0);



                    }
                    if(position==2){
                        intent0=new Intent(connections.this,group.class);
                        intent0.putExtra("target","none");
                        finish();
                        startActivity(intent0);


                    }
                    if(position==3){
                        intent0=new Intent(connections.this,MainActivity.class);
                        intent0.putExtra("target","none");
                        finish();
                        startActivity(intent0);


                    }
                }
            });


        }
        datawork();
        req=(Button)findViewById(R.id.req);
        dreq=new Dialog(connections.this);
        dreq.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dreq.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dreq.setContentView(R.layout.dreq);
        clos=(Button)dreq.findViewById(R.id.close);
        reqrec=(RecyclerView)dreq.findViewById(R.id.recreq);
        reqrec.setLayoutManager(new LinearLayoutManager(this));
        newreq=(Button)findViewById(R.id.newreq);
        newreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req.performClick();
            }
        });
        if (kdata.size()>0){
            newreq.setText(String.valueOf(kdata.size()));
            newreq.setVisibility(View.VISIBLE);
        }
        reqrec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d.getCount()>0){
                rqa = new reqadapter(kdata,view.getContext(),usn);
                reqrec.setAdapter(rqa);
                dreq.show();}
                else {
                    Snackbar.make(view, "No New Requests Available!",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(connections.this,connections.class);
                dreq.hide();
                startActivity(intent);
                finish();
            }
        });

        ckcon=(TextView)findViewById(R.id.kcon);
        emp=(TextView)findViewById(R.id.kemp);
        final TextView detc=(TextView)findViewById(R.id.detc);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
        ckcon.setTypeface(custom_font);
        detc.setTypeface(custom_font);

        adapter = new Adapter(connections.this,cdata,usn,kid,custom_font);
        rv.setAdapter(adapter);
        flag2=1;
        Intent in = getIntent();
        if (in.hasExtra("position")) {
            int pos = 0;
            String target = in.getStringExtra("position");
            for (int i = 0; i < cdata.size(); i++) {
                if (cdata.get(i).getUname().equals(target)) {
                    pos = i;
                    break;
                }
            }
            rv.scrollToPosition(pos);
        }
        if (adapter.getItemCount()>0)
        {
            emp.setVisibility(View.INVISIBLE);
        }

    }
    public static void delete(String x){
        kdb.execSQL("DELETE FROM connect WHERE dvid='"+x+"'");
        adapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    void datawork(){
        {
            dbr = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "reqDB", Context.MODE_PRIVATE, null);
            dbr.execSQL(getString(R.string.reqdb));
            kdb = openOrCreateDatabase(getFilesDir().getAbsolutePath() + "ContDB", Context.MODE_PRIVATE, null);
            String SELECT_SQL = "SELECT aid,time,uname,email,mp,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op01,dvid FROM connect";
            kdb.execSQL(getString(R.string.cdb));
            c = kdb.rawQuery(SELECT_SQL, null);
            if (c.getCount() > 0) {
                c.moveToLast();
                do {

                    cdata.add(new data(
                            c.getInt(0),        // id
                            c.getString(1),     // title
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11),
                            c.getString(12),
                            c.getString(13),
                            c.getString(14),
                            c.getString(15),
                            c.getString(16)
                    ));
                } while (c.moveToPrevious());
                c.close();
            }
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            usn = mPrefs.getString("username", "");
            d = dbr.rawQuery("SELECT uname,aid,mp,devid,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01 FROM matches;", null);
            d.moveToLast();
            if (d.getCount() > 0) {
                do {
                    tdata = new data();
                    tdata.setUname(d.getString(0));
                    tdata.setAid(d.getInt(1));
                    tdata.setMp(d.getString(2));
                    tdata.setDevid(d.getString(3));
                    tdata.setOp1(d.getString(4));
                    tdata.setOp2(d.getString(5));
                    tdata.setOp3(d.getString(6));
                    tdata.setOp4(d.getString(7));
                    tdata.setOp5(d.getString(8));
                    tdata.setOp6(d.getString(9));
                    tdata.setOp7(d.getString(10));
                    tdata.setOp8(d.getString(11));
                    tdata.setOp9(d.getString(12));
                    tdata.setOp10(d.getString(13));
                    tdata.setOp11(d.getString(14));
                    tdata.setOp12(d.getString(15));
                    tdata.setOp01(d.getString(16));
                    kdata.add(tdata);
                    Log.d("kdata", String.valueOf(kdata.size()));
                } while (d.moveToPrevious());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
