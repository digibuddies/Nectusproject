package com.digibuddies.cnectus;


import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import tyrantgit.explosionfield.ExplosionField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class questions extends AppCompatActivity {
    private ArrayList<String> testData;
    ArrayList<String> list = new ArrayList<>();
    private CheckBox dragCheckbox;
    private ExplosionField explosionField;
    static int count=0;
    int count2=0,count3=0,flag3=0;
    AnimationDrawable anim;
private SwipeDeckAdapter adapter;
    Dialog dnew2;
    RadioGroup radioGroup;
    private SQLiteDatabase db2,db3;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    Intent intent0;
    SharedPreferences.Editor editor;
    SharedPreferences mPrefs;
    final String firsttime ="firsttime";
    int firstt;
    Button imb,imb2;
    int x,flag=0,flag2=0,flagrun=0;
    RadioButton r4;
    private int[] ITEM_DRAWABLES = {R.drawable.dice,R.drawable.find,R.drawable.talk,R.drawable.home};
    TextView h,l,dt,dd,ex;
SwipeDeck cardStack;
    private Cursor c;
    String query1 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,0);";
    private String SELECT_SQL = "SELECT count FROM counter1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
                 }
        setContentView(R.layout.acticity_questions);
        createDatabase();
        dostuff();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        firstt = mPrefs.getInt(firsttime, 0);
        if (firstt==1){
            flag=1;
        }
        final Dialog dnew=new Dialog(this);
        dnew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew.setContentView(R.layout.dialogque);
        imb = (Button)dnew.findViewById(R.id.close);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dnew.dismiss();
            }
        });
        if (flag==1){
            dnew.show();
            flag2=1;
            String tok=mPrefs.getString("cntoken","nnn");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef2 = database.getReference("tokens");
            String id = MainActivity.id;
            myRef2.child(id).child("token").setValue(tok);
            FirebaseMessaging.getInstance().subscribeToTopic("notification");
        }

        dnew2=new Dialog(this);
        dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew2.setContentView(R.layout.dialogque);
        imb2 = (Button)dnew2.findViewById(R.id.close);
        dt=(TextView)dnew2.findViewById(R.id.dtitle);
        dd=(TextView)dnew2.findViewById(R.id.ddet);
        ex=(TextView)dnew2.findViewById(R.id.ex);
        ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
        dback.setImageResource(R.drawable.zz);
        dt.setText("Superb!!!");
        dd.setText(getString(R.string.qu));
        imb2.setText("Oky Doky!");
        imb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dnew2.dismiss();
                Intent intent=new Intent(questions.this,matches.class);
                intent.putExtra("target","top");
                finish();
                startActivity(intent);

            }
        });



        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
        final TextView hdq=(TextView)findViewById(R.id.questions);
        final TextView detq=(TextView)findViewById(R.id.detailq);
        hdq.setTypeface(custom_font);
        detq.setTypeface(custom_font);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        dragCheckbox = (CheckBox) findViewById(R.id.checkbox_drag);
        h=(TextView)findViewById(R.id.help);
        h.setTypeface(custom_font);
        l=(TextView)findViewById(R.id.limit);
        testData = new ArrayList<>();
        explosionField = ExplosionField.attach2Window(this);
        Cursor c4 = db2.rawQuery(SELECT_SQL, null);
        if(c4.getCount()>0)
        {
            c4.moveToFirst();
            count3 = c4.getInt(0);
            if(count3>15&&count3!=list.size()+15)
            {

                count3=count3-15;
                String query3 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,'" + count3 + "');";
                db2.execSQL(query3);
            }
            else if(count3==15){
                count3=0;
                String query3 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,'" + count3 + "');";
                db2.execSQL(query3);
            }
        }
        c4.close();
        Cursor c3 = db2.rawQuery(SELECT_SQL, null);
        if(c3.getCount()>0)
        {
            c3.moveToFirst();
            count2 = Integer.parseInt(c3.getString(0));}
        c3.close();

        x=list.size()-count2;
        for (int i = 1; i <= x; i+=5) {
            testData.add(String.valueOf(i));
        }
        adapter = new SwipeDeckAdapter(testData, this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        if(adapter.getCount()>0){
            h.setText("Select And Swipe!");
            adapter.notifyDataSetChanged();
        }
        if(adapter.getCount()==0){
            l.setVisibility(View.VISIBLE);
        }
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                setfalse();
                Log.i("MainActivity", "card was swiped left, position in adapter: " + stableId);
                if(stableId==(x/5)-1||stableId==(x/5)-2||stableId==(x/5)-3){
                    count = count + 5;
                    Log.d("count", String.valueOf(count));
                    String query2 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,'" + count + "');";
                    db2.execSQL(query2);
                }




            }

            @Override
            public void cardSwipedRight(long stableId) {
                setfalse();
                Log.i("MainActivity", "card was swiped right, position in adapter: " + stableId);
                if(stableId==(x/5)-1||stableId==(x/5)-2||stableId==(x/5)-3){
                    count = count + 5;
                    Log.d("count", String.valueOf(count));
                    String query2 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,'" + count + "');";
                    db2.execSQL(query2);
                }
            }

            @Override
            public boolean isDragEnabled(long itemId) {
                return dragCheckbox.isChecked();
            }
        });


        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.showTooltip(false);
        menu.setMinRadius(60);
        menu.setAnim(300,300,ArcMenu.ANIM_MIDDLE_TO_RIGHT,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE,ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            FloatingActionButton item = new FloatingActionButton(this);  //Use internal fab as a child
            item.setSize(FloatingActionButton.SIZE_MINI);  //set minimum size for fab 42dp
            item.setShadow(true); //enable to draw shadow
            item.setIcon(ITEM_DRAWABLES[i]); //add icon for fab
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttoncol));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;
            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position==2){
                        intent0=new Intent(questions.this,connections.class);
                        finish();
                        startActivity(intent0);
                    }

                    if(position==0){
                        intent0=new Intent(questions.this,matches.class);
                        intent0.putExtra("target","random");
                        finish();
                        startActivity(intent0);

                    }
                    if(position==1){
                        intent0=new Intent(questions.this,search.class);
                        intent0.putExtra("target","search");
                        finish();
                        startActivity(intent0);

                    }
                    if(position==3){
                        intent0=new Intent(questions.this,MainActivity.class);
                        intent0.putExtra("target","none");
                        finish();
                        startActivity(intent0);

                    }

                }
            });}
        }


    public class SwipeDeckAdapter extends BaseAdapter {


        private List<String> data;
        private Context context;

        public SwipeDeckAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(count==70){
                int sa = mPrefs.getInt("flag2",5);
                if (sa==5){
                Intent intent=new Intent(questions.this,thebackservice.class);
                startService(intent);
                flagrun=1;
                h.postDelayed(new Runnable() {
                    public void run() {
                        ex.setVisibility(View.VISIBLE);
                        editor = mPrefs.edit();
                        editor.putInt("flag2",3);
                        editor.putInt(firsttime, 2);
                        editor.commit();
                        dnew2.show();
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.cancel(109);

                    }
                }, 2500);}
            }
            Cursor c2 = db2.rawQuery(SELECT_SQL, null);
            c2.moveToFirst();
            count = Integer.parseInt(c2.getString(0));
            c2.close();
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card2, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            TextView textView = (TextView) v.findViewById(R.id.textView);
            h.postDelayed(new Runnable() {
                public void run() {
                    l.setVisibility(View.VISIBLE);
                }
            }, 3000);
            radioGroup = (RadioGroup)v.findViewById(R.id.grp);
            r1=(RadioButton)v.findViewById(R.id.radioButton5);
            r2=(RadioButton)v.findViewById(R.id.radioButton6);
            r3=(RadioButton)v.findViewById(R.id.radioButton7);
            r4=(RadioButton)v.findViewById(R.id.radioButton8);
            final View v1=r1;
            final View v2=r2;
            final View v3=r3;
            final View v4=r4;

            r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    explosionField.explode(v2);
                    explosionField.explode(v3);
                    explosionField.explode(v4);
                    v2.setClickable(false);
                    v3.setClickable(false);
                    v4.setClickable(false);
                    dragCheckbox.setChecked(true);
                    db3.execSQL("INSERT INTO ques VALUES(1);");

                }
            });
            r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    explosionField.explode(v1);
                    explosionField.explode(v3);
                    explosionField.explode(v4);
                    v1.setClickable(false);
                    v3.setClickable(false);
                    v4.setClickable(false);
                    dragCheckbox.setChecked(true);
                    db3.execSQL("INSERT INTO ques VALUES(2);");

                }
            });
            r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    explosionField.explode(v1);
                    explosionField.explode(v2);
                    explosionField.explode(v4);
                    dragCheckbox.setChecked(true);
                    v1.setClickable(false);
                    v2.setClickable(false);
                    v4.setClickable(false);
                    db3.execSQL("INSERT INTO ques VALUES(3);");

                }
            });
            r4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    explosionField.explode(v1);
                    explosionField.explode(v2);
                    explosionField.explode(v3);
                    v1.setClickable(false);
                    v2.setClickable(false);
                    v3.setClickable(false);
                    dragCheckbox.setChecked(true);
                    db3.execSQL("INSERT INTO ques VALUES(4);");

                }
            });

            setfalse();
            textView.setText(list.get(count));
            r1.setText(" "+list.get(count+1));
            r2.setText(" "+list.get(count+2));
            r3.setText(" "+list.get(count+3));
            r4.setText(" "+list.get(count+4));
            count = count + 5;
            flag=flag+1;
            if(((list.size()-count2)/5)==2&&flag==2){
                count = count+5;
            }
            if(((list.size()-count2)/5)==1&&flag==1){
                count = count+10;
            }
            Log.d("count", String.valueOf(count));
            String query2 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,'" + count + "');";
            db2.execSQL(query2);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                }
            });
            return v;
        }
    }
    protected void createDatabase(){
        db2=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"counters", Context.MODE_PRIVATE, null);
        db3=openOrCreateDatabase(getFilesDir().getAbsolutePath()+"QueDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS counter1(id integer primary key, count INTEGER);");
        db3.execSQL("CREATE TABLE IF NOT EXISTS ques(ans integer);");
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        if(c.getCount()<=0){
            db2.execSQL(query1);
        }
    }



    public void dostuff(){
        File file = null;
        try {
            file = new File(getFilesDir(), "fiel.txt");
            FileInputStream is;
            is = new FileInputStream(file);
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while(line != null){
                list.add(line);
                line = reader.readLine();

            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setfalse(){
        dragCheckbox.setChecked(false);
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
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        if(flagrun==0){
        Intent i=new Intent(this,thebackservice.class);
        startService(i);}
        c.close();
        db2.close();
        db3.close();
        super.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

