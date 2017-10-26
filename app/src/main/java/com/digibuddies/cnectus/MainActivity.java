package com.digibuddies.cnectus;

import android.app.AlarmManager;
import android.app.Dialog;
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
import android.graphics.drawable.ColorDrawable;
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

import com.google.firebase.messaging.FirebaseMessaging;
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
    private String SELECT_SQL = "SELECT uname,aid FROM profile";
    private ProgressDialog working_dialog;
    ResideMenuItem itemHelp;
    private Dialog dialog;
    ResideMenuItem itemFeed;
    Button b,b1,b2,b3,b4;
    int flag=0;
    ResideMenuItem itemAbout;
    ConnectivityManager cm;
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    String username = "usname";
    public String uname="";
    TextView usname;
    String tar;
    Intent intentb;
    CircleImageView civ;
    Intent bck;
    int aid;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        b = (Button) findViewById(R.id.settings_button);
        usname=(TextView)findViewById(R.id.usname);
        civ=(CircleImageView)findViewById(R.id.aid);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.apply();
            Intent intro = new Intent(this,Help.class);
            startActivity(intro);

        }




        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");
        if(!(isDeviceOnline())){
            final Snackbar snackbar = Snackbar
                    .make(usname, getString(R.string.ma1), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Restart", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           Intent intent=new Intent(MainActivity.this,splashactivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(5);
            snackbar.show();
        }
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
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.alikemenu);
        b1=(Button)dialog.findViewById(R.id.b1);
        b2=(Button)dialog.findViewById(R.id.b2);
        b3=(Button)dialog.findViewById(R.id.b3);
        b4=(Button)dialog.findViewById(R.id.b4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,group.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,matches.class);
                intent.putExtra("target","top");
                startActivity(intent);
                dialog.cancel();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,matches.class);
                intent.putExtra("target","worst");
                startActivity(intent);
                dialog.cancel();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,matches.class);
                intent.putExtra("target","random");
                startActivity(intent);
                dialog.cancel();
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
                        if(!(isDeviceOnline())){
                            Toast.makeText(MainActivity.this,"No Internet Connection Available!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        showWorkingDialog();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                removeWorkingDialog();
                            }

                        }, 2000);
                        Intent intent = new Intent(MainActivity.this, profileclass.class);
                        startActivity(intent);}
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
                        if(usname.getText().equals(getString(R.string.ma3))){
                            Toast.makeText(MainActivity.this,getString(R.string.ma2), Toast.LENGTH_SHORT).show();
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
                        if(usname.getText().equals(getString(R.string.ma3))){
                            Toast.makeText(MainActivity.this,"Please create your profile first...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        dialog.show();}
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
                        if(usname.getText().equals(getString(R.string.ma3))){
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
        itemFeed = new ResideMenuItem(this, R.drawable.feed, "Feed Back");
        itemAbout=new ResideMenuItem(this, R.drawable.about, "About");
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
            working_dialog.hide();
            working_dialog = null;
        }
    }

    public void onBackPressed() {
      this.moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        dowork();
        super.onResume();
    }

       public boolean isDeviceOnline() {
        Context context = MainActivity.this;
        // test for connection
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }


    public  void dowork(){
        if(flag==0){


        dbm = openOrCreateDatabase(getFilesDir().getAbsolutePath()+"prodb.db", Context.MODE_PRIVATE, null);
        dbm.execSQL(getString(R.string.prodb));
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
        c.close();
            dbm.close();


    }}


}