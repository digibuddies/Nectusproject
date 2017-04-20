package com.digibuddies.nectus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
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
import android.widget.TextView;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.digibuddies.nectus.profile.profileclass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class connections extends AppCompatActivity {
    static SQLiteDatabase kdb;
    RecyclerView rv;
    List<data> cdata = new ArrayList<data>();
    static Adapter adapter;
    TextView ckcon,emp;
    static Typeface custom_font;
    static String kid;
    private static final int[] ITEM_DRAWABLES = { R.drawable.face,
            R.drawable.help, R.drawable.add };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        kid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Cursor c;
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
                        Intent intent=new Intent(connections.this,profileclass.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==1){
                        Intent intent=new Intent(connections.this,questions.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==2){
                        Intent intent=new Intent(connections.this,matches.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        kdb=openOrCreateDatabase(storagePath+"/"+"ContDB", Context.MODE_PRIVATE, null);
        String SELECT_SQL ="SELECT aid,time,uname,email,mp,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,id,dvid FROM connect";
        kdb.execSQL("CREATE TABLE IF NOT EXISTS connect(id INTEGER NOT NULL PRIMARY KEY,dvid VARCHAR(20),time VARCHAR(20),mp varchar(20), aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");
        c = kdb.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        if(c.getCount()>0){
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
                    c.getInt(15),
                    c.getString(16)
            ));

        } while (c.moveToNext());}
        ckcon=(TextView)findViewById(R.id.kcon);
        emp=(TextView)findViewById(R.id.kemp);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");
        ckcon.setTypeface(custom_font);
        adapter = new Adapter(cdata);
        rv.setAdapter(adapter);
        if (adapter.getItemCount()>0)
        {
            emp.setVisibility(View.INVISIBLE);
        }

    }

    public static void delete(int x){
        Log.d("conn", String.valueOf(x));
        kdb.execSQL("DELETE FROM connect WHERE id='"+x+"'");
        adapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        //  super.onBackPressed();
        finish();

    }



}
