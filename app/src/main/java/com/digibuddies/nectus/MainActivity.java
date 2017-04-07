package com.digibuddies.nectus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.whygraphics.gifview.gif.GIFView;


import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static com.nightonke.boommenu.R.layout.bmb;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }



        GIFView mGifView = (GIFView) findViewById(R.id.main_activity_gif_vie);
        mGifView.setGifResource("asset:bb");
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

        FloatingTextButton mb = (FloatingTextButton)findViewById(R.id.mb);
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
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
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
                        Intent intent = new Intent(MainActivity.this,questions.class);
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
                        Intent intent = new Intent(MainActivity.this,matches.class);
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
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
        bmb.addBuilder(builder1);
        bmb.addBuilder(builder2);
        bmb.addBuilder(builder3);
        bmb.addBuilder(builder4);
        bmb.setButtonRadius(Util.dp2px(35));
        bmb.setNormalColor(Color.WHITE);

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


}
