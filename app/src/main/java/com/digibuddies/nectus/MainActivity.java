package com.digibuddies.nectus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    ResideMenu resideMenu;
    ResideMenuItem itemHome;
    private SQLiteDatabase dbm;
    private Cursor c;
    private static final String SELECT_SQL = "SELECT uname,aid FROM profile";
    private ProgressDialog working_dialog;
    ResideMenuItem itemHelp;
    ResideMenuItem itemFeed;
    Button b;
    ResideMenuItem itemAbout;
    ConnectivityManager cm;
    TextView tvp;
    public static int call;
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

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

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit();
            Intent intro = new Intent(this,Help.class);
            startActivity(intro);

        }
        b = (Button) findViewById(R.id.settings_button);
        tvp=(TextView)findViewById(R.id.tvp);
        usname=(TextView)findViewById(R.id.usname);
        civ=(CircleImageView)findViewById(R.id.aid);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");
        if(!(isDeviceOnline())){
            final Snackbar snackbar = Snackbar
                    .make(usname, "Nectus needs an active Internet Connection, Please Connect your device to internet!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           snackbar.dismiss();
                        }
                    });

            snackbar.show();
        }
        tvp.setTypeface(custom_font);
        usname.setTypeface(custom_font);
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
                        }

                        // ...
                    }
                });
        final BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
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
                .normalColorRes(R.color.boom1)
                .subNormalText("Tell the world about yourself!")
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
                .normalColorRes(R.color.boom2)
                .subNormalText("Lets understand you better!")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if(usname.getText().equals("Please Head Onto the Profile section to ceate your profile!")){
                            Toast.makeText(MainActivity.this,"Please create your profile first, then visit this section!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        Intent intent = new Intent(MainActivity.this, questions.class);
                        startActivity(intent);}
                    }
                });
        HamButton.Builder builder3 = new HamButton.Builder();
        builder3.normalImageRes(R.drawable.ic_group_add_white_48dp)
                .normalText("Alike")
                .normalColorRes(R.color.boom3)
                .subNormalText("Connect to people with similar personalities!")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if(usname.getText().equals("Please Head Onto the Profile section to ceate your profile!")){
                            Toast.makeText(MainActivity.this,"Please create your profile first...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        Intent intent = new Intent(MainActivity.this, matches.class);
                        startActivity(intent);}
                    }
                });

        HamButton.Builder builder4 = new HamButton.Builder();
        builder4.normalImageRes(R.drawable.ic_settings_applications_white_48dp)
                .normalText("Extras")
                .normalColorRes(R.color.boom4)
                .subNormalText("Some other useful stuff!")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        b.performClick();
                    }
                });

        HamButton.Builder builder5 = new HamButton.Builder();
        builder5.normalImageRes(R.drawable.ic_contact_mail_white_48dp)
                .normalText("Connections")
                .normalColorRes(R.color.boom5)
                .subNormalText("See your connections here!")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(MainActivity.this, connections.class);
                        startActivity(intent);
                    }
                });
        bmb.addBuilder(builder1);
        bmb.addBuilder(builder2);
        bmb.addBuilder(builder3);
        bmb.addBuilder(builder5);
        bmb.addBuilder(builder4);
        bmb.setButtonRadius(Util.dp2px(35));
        bmb.setNormalColor(Color.WHITE);
        setUpMenu();
    }


    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(MainActivity.this);
        resideMenu.setBackground(R.drawable.b3);
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
                Intent intent=new Intent(MainActivity.this,Feed.class);
                startActivity(intent);
            }
        });
        itemAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Aboutus.class);
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

        File storagePath = new File(Environment.getExternalStorageDirectory(), "/android/.data_21");
        if(storagePath.exists()) {
            dbm = openOrCreateDatabase(storagePath+"/"+"prodb.db", Context.MODE_PRIVATE, null);
            dbm.execSQL("CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY, aid INTEGER, email VARCHAR(20), uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(20),op12 VARCHAR(20),p1 integer,p2 integer,p3 integer,p4 integer,p5 integer,p6 integer,p7 integer,p8 integer,p9 integer,p10 integer,p11 integer,p12 integer);");
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
    public boolean isDeviceOnline() {
        Context context = MainActivity.this;
        // test for connection
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }

}