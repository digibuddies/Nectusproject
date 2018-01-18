package com.digibuddies.cnectus;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.digibuddies.cnectus.profile.profileclass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.digibuddies.cnectus.R.attr.layoutManager;

public class matches extends AppCompatActivity {

    RecyclerView srv;
    List<data> mdata,ndata;
    data tdata;
    sadapter adapter;
    Button sea;
    SharedPreferences.Editor editor;
    Intent intent0;
    SharedPreferences mPrefs;
    final String firsttime ="firsttime";
    int firstt;
    SQLiteDatabase dbc;
    private static Dialog dnew2;
    Button imb2;
    static int flag=0;
    private int[] ITEM_DRAWABLES = { R.drawable.help, R.drawable.talk,R.drawable.dice,R.drawable.group, R.drawable.home };
    private SQLiteDatabase db;
    private Cursor c,b;
    TextView det,dt,dd,ex;
    String id,tar;
    LinearLayoutManager linearLayoutManager;
    int flagm=0;
    int qcount;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        dnew2 = new Dialog(this);
        dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew2.setContentView(R.layout.dialogque);
        imb2 = (Button) dnew2.findViewById(R.id.close);
        dt = (TextView) dnew2.findViewById(R.id.dtitle);
        dd = (TextView) dnew2.findViewById(R.id.ddet);
        ex = (TextView) dnew2.findViewById(R.id.ex);
        ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
        dback.setImageResource(R.drawable.zzz);
        ex.setVisibility(View.VISIBLE);
        id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        firstt = mPrefs.getInt(firsttime, 0);

        if (firstt == 2) {
            flag = 1;
            ex.setText(getString(R.string.md11));
            dt.setText(getString(R.string.md22));
            dd.setText(getString(R.string.md33));
            imb2.setText(getString(R.string.md44));
            imb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dnew2.dismiss();
                    ex.setText(getString(R.string.md1));
                    dt.setText(getString(R.string.md2));
                    dd.setText(getString(R.string.md3));
                    imb2.setText(getString(R.string.md4));
                    imb2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dnew2.dismiss();
                            Intent intent = new Intent(matches.this, connections.class);
                            finish();
                            startActivity(intent);


                        }
                    });
                }
            });
            dnew2.show();
            startService(new Intent(matches.this,notifyservice.class));
            Map<String, String> data= new HashMap<String, String>();
            data.put("mp"," ");
            data.put("request","RIGHT");
            myRef.child("123").child(id).setValue(data);
            myRef.child(id).child("123").setValue(data);
            editor = mPrefs.edit();
            editor.putInt(firsttime, 3);
            editor.apply();
        }
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        det=(TextView)findViewById(R.id.detp);
        det.setTypeface(custom_font);
        Intent disp=getIntent();
        tar=disp.getStringExtra("target");
        if (tar.equals("worst")){
            flagm=1;
            det.setText(R.string.ma6);
        }
        if (tar.equals("random")){
            flagm=2;
            det.setText(R.string.ma7);
            Intent in=new Intent(matches.this,thebackservice.class);
            startService(in);
        }



        final Dialog dialogref = new Dialog(this);
        dialogref.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogref.setContentView(R.layout.refresh);

        final TextView front = (TextView) findViewById(R.id.front);
        srv = (RecyclerView) findViewById(R.id.recm);
        linearLayoutManager = new LinearLayoutManager(this);
        srv.setLayoutManager(linearLayoutManager);
        srv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        srv.setItemAnimator(new DefaultItemAnimator());
        createDatabase00();
        createDatabase();
        String SELECT_SQL;
        SELECT_SQL = getString(R.string.mselectsql);
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();
        adapter = new sadapter(mdata,ndata, custom_font, id, this,0);
        srv.setAdapter(adapter);
        if(qcount>70&&adapter.getItemCount()>0) {
            front.setVisibility(View.INVISIBLE);
            srv.setVisibility(View.VISIBLE);
        }
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.attachToRecyclerView(srv);
        menu.setMinRadius(60);
        menu.showTooltip(false);
        menu.setAnim(300, 300, ArcMenu.ANIM_MIDDLE_TO_RIGHT, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            FloatingActionButton item = new FloatingActionButton(this);  //Use internal fab as a child
            item.setSize(FloatingActionButton.SIZE_MINI);  //set minimum size for fab 42dp
            item.setShadow(true); //enable to draw shadow
            item.setIcon(ITEM_DRAWABLES[i]); //add icon for fab
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.first_slide_background));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;
            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        intent0 = new Intent(matches.this, questions.class);
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 1) {
                        intent0 = new Intent(matches.this, connections.class);
                        finish();
                        startActivity(intent0);


                    }

                    if (position == 2) {
                        intent0 = new Intent(matches.this, matches.class);
                        intent0.putExtra("target", "random");
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 3) {
                        intent0 = new Intent(matches.this, group.class);
                        intent0.putExtra("target", "none");
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 4) {
                        intent0 = new Intent(matches.this, MainActivity.class);
                        intent0.putExtra("target", "none");
                        finish();
                        startActivity(intent0);


                    }
                }
            });
        }
    }


    protected void showRecords() {
        int i=0;
        mdata = new ArrayList<data>();
        ndata = new ArrayList<data>();
        if(c.getCount()>0) {
            if(flagm==1){
                c.moveToPosition(9);
            }
            else if(flagm==2){
                c.moveToPosition(10);
            }
            else c.moveToFirst();
            do {
                tdata=new data();
                tdata.setUname(c.getString(0));
                tdata.setAid(c.getInt(1));
                tdata.setEmail(c.getString(2));
                tdata.setOp1(c.getString(3));
                tdata.setOp2(c.getString(4));
                tdata.setOp3(c.getString(5));
                tdata.setOp4(c.getString(6));
                tdata.setOp5(c.getString(7));
                tdata.setOp6(c.getString(8));
                tdata.setOp7(c.getString(9));
                tdata.setOp8(c.getString(10));
                tdata.setOp9(c.getString(11));
                tdata.setOp10(c.getString(12));
                tdata.setOp11(c.getString(13));
                tdata.setOp12(c.getString(14));
                tdata.setOp01(c.getString(15));
                tdata.setDevid(c.getString(17));
                tdata.setAllow(c.getString(18));
                if (flagm==0){
                    if(i<3){
                        mdata.add(tdata);
                    }
                    else ndata.add(tdata);
                }else mdata.add(tdata);
                Log.d("cooid",c.getString(19));
                i++;

            } while ((flagm!=2)?(((flagm==0)?c.moveToNext()&&i<7:c.moveToPrevious()&&i<3)):(c.moveToNext()&&i<5));
            c.close();
        }

    }


    protected void createDatabase() {
            db = openOrCreateDatabase(getFilesDir().getAbsolutePath()+"PerDB", Context.MODE_PRIVATE, null);
            dbc=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"ContDB", Context.MODE_PRIVATE, null);
            dbc.execSQL(getString(R.string.condb));

        }


    public void createDatabase00(){

        SQLiteDatabase db2;
        db2=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"counters", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS counter1(id integer primary key, count INTEGER);");
        c = db2.rawQuery("SELECT count FROM counter1;", null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.moveToFirst();
            qcount=c.getInt(0);
        }
        db2.close();
        c.close();
    }


    // Starting animation:- start the animation on onResume.
    @Override
    protected void onResume() {
        super.onResume();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
    }

    public static void showd(){
        if(flag==1){
            dnew2.show();
            flag=0;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        db.close();
        if (flagm==2){
            Intent intent=new Intent(this,thebackservice.class);
            startService(intent);
        }
        super.finish();

    }
}
