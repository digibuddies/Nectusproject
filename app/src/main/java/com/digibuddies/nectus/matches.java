package com.digibuddies.nectus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.devs.squaremenu.OnMenuClickListener;
//import com.devs.squaremenu.SquareMenu;
import com.whygraphics.gifview.gif.GIFView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
    final ArrayList<Integer> aid = new ArrayList<>();
    final ArrayList<String> mail = new ArrayList<>();
    final ArrayList<String> userid = new ArrayList<>();
    final ArrayList<String> mp = new ArrayList<>();


    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv,d1;
    AnimationDrawable anim;
    CircleImageView p1,p2,p3;
    Button b1;
    private static final String SELECT_SQL ="SELECT uname,aid,email,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,mp FROM matches";
    private SQLiteDatabase db;
    private Cursor c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        createDatabase();

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        GIFView mGifView = (GIFView) findViewById(R.id.main_activity_gif_vie2);
        mGifView.setGifResource("asset:cv");

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();
        p1=(CircleImageView)findViewById(R.id.p1);
        p2=(CircleImageView)findViewById(R.id.p2);
        p3=(CircleImageView)findViewById(R.id.p3);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        d1=(TextView)findViewById(R.id.detail1);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");

        d1.setTypeface(custom_font);

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
            tv2.setText(mp.get(0)+"% Similar");
            //tv4.setText(mp.get(1)+"% Similar");
            //tv6.setText(mp.get(2)+"% Similar");
            tv1.setText(userid.get(0));
            //tv3.setText(userid.get(1));
            //tv5.setText(userid.get(2));
            p1.setImageResource(aid.get(0));
            //p2.setImageResource(aid.get(1));
            //p3.setImageResource(aid.get(2));
            d1.setText(datadb.get(0)+", you can call me "+userid.get(0)+". I am here since "+datadb2.get(0)+". Currently I am working towards "+datadb3.get(0)+" and i love to practice my "+datadb4.get(0)+" skills. I would like to "+datadb5.get(0)+" someday. My friends say i'm "+datadb6.get(0)+". I just "+datadb7.get(0)+" "+datadb8.get(0)+" I spend most of my day "+datadb9.get(0)+". A person with same mind as mine would be "+datadb10.get(0)+".");

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

            } while (c.moveToNext());
            c.close();
        }

    }


    protected void createDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);

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


}
