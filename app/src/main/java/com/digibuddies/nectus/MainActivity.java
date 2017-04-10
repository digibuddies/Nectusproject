package com.digibuddies.nectus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


import com.digibuddies.nectus.profile.profileclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.whygraphics.gifview.gif.GIFView;


import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static com.nightonke.boommenu.R.layout.bmb;
import static com.special.ResideMenu.ResideMenu.DIRECTION_LEFT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    ResideMenu resideMenu;
    MainActivity mContext;
    ResideMenuItem itemHome;
    private SQLiteDatabase dbm;
    private Cursor c;
    private static final String SELECT_SQL = "SELECT uname,aid FROM profile";
    private ProgressDialog working_dialog;
    ResideMenuItem itemHelp;
    ResideMenuItem itemFeed;
    Button b;
    ResideMenuItem itemAbout;
    TextView tvp;
    public static int call;
    String uname;
    TextView usname;
    CircleImageView civ;
    int aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        b = (Button) findViewById(R.id.settings_button);
        tvp=(TextView)findViewById(R.id.tvp);
        usname=(TextView)findViewById(R.id.usname);
        civ=(CircleImageView)findViewById(R.id.aid);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");

        usname.setTypeface(custom_font);
        tvp.setTypeface(custom_font);

        GIFView mGifView = (GIFView) findViewById(R.id.main_activity_gif_vie);
        mGifView.setGifResource("asset:c");
        scheduleAlarm();
       /* mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.city);
        */
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        final BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        bmb.setDimColor(Color.parseColor("#8B000000"));

        FloatingTextButton mb = (FloatingTextButton) findViewById(R.id.mb);
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmb.boom();
            }
        });

        HamButton.Builder builder1 = new HamButton.Builder();
        builder1.normalImageRes(R.drawable.ic_face_white_48dp)
                .normalText("Profile")
                .subNormalText("Tell me abt uuuu!")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        showWorkingDialog();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                removeWorkingDialog();
                            }

                        }, 2000);
                        Intent intent = new Intent(MainActivity.this, profileclass.class);
                        startActivity(intent);
                    }
                });

        HamButton.Builder builder2 = new HamButton.Builder();
        builder2.normalImageRes(R.drawable.ic_help_white_48dp)
                .normalText("Questions")
                .subNormalText("Answer something here !")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MainActivity.this, questions.class);
                        startActivity(intent);
                    }
                });
        HamButton.Builder builder3 = new HamButton.Builder();
        builder3.normalImageRes(R.drawable.ic_group_add_white_48dp)
                .normalText("Matches")
                .subNormalText("yayay the ebst part")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MainActivity.this, matches.class);
                        startActivity(intent);
                    }
                });

        HamButton.Builder builder4 = new HamButton.Builder();
        builder4.normalImageRes(R.drawable.ic_settings_applications_white_48dp)
                .normalText("Settings")
                .subNormalText("Play with the app")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        b.performClick();
                    }
                });
        bmb.addBuilder(builder1);
        bmb.addBuilder(builder2);
        bmb.addBuilder(builder3);
        bmb.addBuilder(builder4);
        bmb.setButtonRadius(Util.dp2px(35));
        bmb.setNormalColor(Color.WHITE);
        setUpMenu();
    }

    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), alarmreceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, alarmreceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);

    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(MainActivity.this);
        resideMenu.setBackground(R.drawable.reside);
        resideMenu.attachToActivity(MainActivity.this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemHelp = new ResideMenuItem(this, R.drawable.help1, "Help");
        itemFeed = new ResideMenuItem(this, R.drawable.feed, "Feedback");
        itemAbout=new ResideMenuItem(this, R.drawable.about, "About Us");

        itemHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.closeMenu();
            }
        });
        itemHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Help.class);
                startActivity(intent);
            }
        });
        itemFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,matches.class);
                startActivity(intent);
            }
        });
        itemAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,matches.class);
                startActivity(intent);
            }
        });

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHelp, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFeed, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });


    }


        @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void showWorkingDialog() {
        working_dialog = ProgressDialog.show(MainActivity.this, "<3", "Nectus Loves You...", true);
    }

    private void removeWorkingDialog() {
        if (working_dialog != null) {
            working_dialog.dismiss();
            working_dialog = null;
        }
    }

    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        File storagePath = new File(Environment.getExternalStorageDirectory(), ".data_21");
        if(storagePath.exists()) {
            dbm = openOrCreateDatabase(storagePath+"/"+"prodb.db", Context.MODE_PRIVATE, null);
            dbm.execSQL("CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY, aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(20),op12 VARCHAR(20));");
            c = dbm.rawQuery(SELECT_SQL, null);
            c.moveToFirst();
            if(c.getCount()>0)
            {
                uname=c.getString(0);
                aid=c.getInt(1);
                usname.setText("Hello! "+uname);
                civ.setImageResource(aid);
                civ.setVisibility(View.VISIBLE);
            }

        }
        if(call==11){
            tvp.setText("Great Now Head onto the Questions Section And Answer Some Questions.");
            tvp.setVisibility(View.VISIBLE);
            tvp.postDelayed(new Runnable() {
                public void run() {
                    tvp.setVisibility(View.INVISIBLE);
                    call=0;
                }
            }, 12000);}

    }
}