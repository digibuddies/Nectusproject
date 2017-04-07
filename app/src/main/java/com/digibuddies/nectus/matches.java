package com.digibuddies.nectus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.devs.squaremenu.OnMenuClickListener;
import com.devs.squaremenu.SquareMenu;
import com.whygraphics.gifview.gif.GIFView;

import java.util.ArrayList;
public class matches extends AppCompatActivity {
    final ArrayList<String> datadb = new ArrayList<>();
    final ArrayList<String> datadb2 = new ArrayList<>();
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv;
    AnimationDrawable anim;
    Button b1;
    private static final String SELECT_SQL = "SELECT user,name FROM persons1";
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
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);

        callme2();
        SquareMenu mSquareMenu = (SquareMenu) findViewById(R.id.square_menu);
        mSquareMenu.setOnMenuClickListener(new OnMenuClickListener(){
            @Override
            public void onMenuOpen() {  }
            @Override
            public void onMenuClose() { }
            @Override
            public void onClickMenu1() {Intent intent = new Intent(matches.this,profile.class);
                startActivity(intent); }
            @Override
            public void onClickMenu2() {Intent intent = new Intent(matches.this,questions.class);
                startActivity(intent); }
            @Override
            public void onClickMenu3() {Intent intent = new Intent(matches.this,profile.class);
                startActivity(intent); }
        });
    }

    public void callme2() {
        if(datadb.size()>0) {
            tv2.setText(datadb.get(0)+"% Similar");
            tv4.setText(datadb.get(1)+"% Similar");
            tv6.setText(datadb.get(2)+"% Similar");
            tv1.setText(datadb2.get(0));
            tv3.setText(datadb2.get(1));
            tv5.setText(datadb2.get(2));
        }
    }


    protected void showRecords() {
        if(c.getCount()>0) {
            do {
                datadb.add(c.getString(0));
                datadb2.add(c.getString(1));

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
