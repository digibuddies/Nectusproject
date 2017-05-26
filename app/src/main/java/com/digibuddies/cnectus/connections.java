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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.digibuddies.cnectus.profile.profileclass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class connections extends AppCompatActivity {
    static SQLiteDatabase kdb,kdm;
    RecyclerView rv;
    Dialog dnew2;
    Button imb2;
    List<data> cdata = new ArrayList<data>();
    static Adapter adapter;
    TextView ckcon,emp;
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences mPrefs;
    final String firsttime ="firsttime";
    int firstt;
    int flag=0;
    static Typeface custom_font;
    static String kid,usn;
    private static final int[] ITEM_DRAWABLES = { R.drawable.face,
            R.drawable.help, R.drawable.add, R.drawable.home };



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
            editor.commit();
        }
        context = this;
        dnew2=new Dialog(this);
        dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew2.setContentView(R.layout.dialogque);
        imb2 = (Button)dnew2.findViewById(R.id.close);
        TextView dt = (TextView) dnew2.findViewById(R.id.dtitle);
        TextView ex=(TextView)dnew2.findViewById(R.id.ex);
        TextView dd=(TextView)dnew2.findViewById(R.id.ddet);
        ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
        dback.setImageResource(R.drawable.zzzz);
        dt.setText("All Set!!!");
        ex.setText("(That's all you need to get started :)");
        ex.setVisibility(View.VISIBLE);
        dd.setText("Once Your Request Is Accepted, You'll Be Able To Chat With The Other Person!");
        imb2.setText("Let's Explore!!");
        imb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dnew2.dismiss();
                Intent intent=new Intent(connections.this,MainActivity.class);
                intent.putExtra("target","none");
                startActivity(intent);
                finish();


            }
        });
        if(flag==1){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    dnew2.show();
                }

            }, 7000);

        }
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
                        Intent intent=new Intent(context,profileclass.class);
                        startActivity(intent);
                        finish();


                    }
                    if(position==1){
                        Intent intent=new Intent(context,questions.class);
                        startActivity(intent);
                        finish();


                    }
                    if(position==2){
                        Intent intent=new Intent(context,matches.class);
                        startActivity(intent);
                        finish();


                    }
                    if(position==3){
                        Intent intent=new Intent(context,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            });


        }



        File storagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        kdb=openOrCreateDatabase(storagePath+"/"+"ContDB", Context.MODE_PRIVATE, null);
        String SELECT_SQL ="SELECT aid,time,uname,email,mp,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,dvid FROM connect";
        kdb.execSQL("CREATE TABLE IF NOT EXISTS connect(dvid VARCHAR(20) PRIMARY KEY,time VARCHAR(20),mp varchar(20), aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");
        c = kdb.rawQuery(SELECT_SQL, null);
        if(c.getCount()>0){
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
                    c.getString(15)
            ));
        } while (c.moveToPrevious());
        c.close();
        }
        kdm = openOrCreateDatabase(storagePath+"/"+"prodb.db", Context.MODE_PRIVATE, null);
        c = kdm.rawQuery("SELECT uname FROM profile", null);
        c.moveToFirst();
        usn=c.getString(0);
        c.close();
        ckcon=(TextView)findViewById(R.id.kcon);
        emp=(TextView)findViewById(R.id.kemp);
        final TextView detc=(TextView)findViewById(R.id.detc);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
        ckcon.setTypeface(custom_font);
        detc.setTypeface(custom_font);

        adapter = new Adapter(cdata);
        rv.setAdapter(adapter);
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




}
