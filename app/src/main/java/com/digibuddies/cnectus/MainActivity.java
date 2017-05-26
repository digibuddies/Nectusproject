package com.digibuddies.cnectus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digibuddies.cnectus.profile.profileclass;
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
import com.tomer.fadingtextview.FadingTextView;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    ResideMenu resideMenu;
    ResideMenuItem itemHome,itemShare;
    private SQLiteDatabase dbm;
    private Cursor c;
    private static final String SELECT_SQL = "SELECT uname,aid FROM profile";
    private ProgressDialog working_dialog;
    ResideMenuItem itemHelp;
    ResideMenuItem itemFeed;
    Button b;
    int flag=0;
    ResideMenuItem itemAbout;
    ConnectivityManager cm;
    TextView tvp;
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    String username = "usname";
    int ff=0;
    public static String uname="";
    TextView usname;
    String tar;
    Intent intent;
    CircleImageView civ;
    int aid;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        intent = new Intent(this, thebackservice.class);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            editor = mPrefs.edit();
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
                    .make(usname, "Cnectus needs an active Internet Connection, Please Connect your device to internet!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           snackbar.dismiss();
                        }
                    });
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(5);
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
                        if(usname.getText().equals("Please Head Onto the Profile section to ceate your profile!")){
                            Toast.makeText(MainActivity.this,"Oops.. You need to create a profile first!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, connections.class);
                            startActivity(intent);}
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
        itemHome = new ResideMenuItem(this, R.drawable.home, "Home");
        itemHelp = new ResideMenuItem(this, R.drawable.help1, "Help");
        itemFeed = new ResideMenuItem(this, R.drawable.feed, "Feedback");
        itemAbout=new ResideMenuItem(this, R.drawable.about, "About Us");
        itemShare=new ResideMenuItem(this, R.drawable.share,"Share Cnectus");

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
        itemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {   final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Cnectus");
                    String sAux = "\nHey, try this new social networking app made by dit students\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.digibuddies.nectus \n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sAux);
                    final PackageManager pm = getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches)
                        if (info.activityInfo.packageName.endsWith(".whatsapp") ||
                                info.activityInfo.name.toLowerCase().contains("whatsapp")) best = info;
                    if (best != null)
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    startActivity(intent);
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHelp, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFeed, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemShare, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
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
        working_dialog = ProgressDialog.show(MainActivity.this, "<3", "Cnectus Loves You...", true);
    }

    private void removeWorkingDialog() {
        if (working_dialog != null) {
            working_dialog.dismiss();
            working_dialog = null;
        }
    }

    public void onBackPressed() {
      this.moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent disp=getIntent();
        tar=disp.getStringExtra("target");
        if(tar.equals("matches")){
            Intent i2=new Intent(MainActivity.this,matches.class);
            startActivity(i2);
        }
        else if(tar.equals("contact")){
            Intent i2=new Intent(MainActivity.this,contact.class);
            startActivity(i2);
        }
        else if(tar.equals("connections")){
            Intent i2=new Intent(MainActivity.this,connections.class);
            startActivity(i2);
        }


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        else {
        dowork();}




    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dowork();
                     } else {
                    flag=1;
                    final Snackbar snackbar = Snackbar
                            .make(usname, "Cnectus requires the R/W permissions to work! Please restart the app and grant the requested permissions...", Snackbar.LENGTH_INDEFINITE);
                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    snackbar.show();
                       }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public boolean isDeviceOnline() {
        Context context = MainActivity.this;
        // test for connection
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }

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
                AlarmManager.INTERVAL_HALF_HOUR, pIntent);

    }

    public  void dowork(){
        if(flag==0){
        File storagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/android/.data_21");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

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


    }}

}