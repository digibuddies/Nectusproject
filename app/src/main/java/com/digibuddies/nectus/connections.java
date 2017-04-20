package com.digibuddies.nectus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Cursor c;
        rv=(RecyclerView)findViewById(R.id.krv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        kdb=openOrCreateDatabase(storagePath+"/"+"ContDB", Context.MODE_PRIVATE, null);
        String SELECT_SQL ="SELECT aid,time,uname,email,mp,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,id FROM connect";
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
                    c.getInt(15)
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
