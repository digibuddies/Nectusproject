package com.digibuddies.cnectus;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class group extends AppCompatActivity {
    Dialog dnew2;
    SharedPreferences.Editor editor;
    SharedPreferences mPrefs;
    Intent intent0;

    final String firsttime ="firsttime";
    private int[] ITEM_DRAWABLES = {R.drawable.help, R.drawable.talk, R.drawable.dice, R.drawable.home};
    static int firstt;
    public static SecondFragment secondFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            firstt = mPrefs.getInt(firsttime, 0);
            dnew2=new Dialog(this);
            dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dnew2.setContentView(R.layout.dialogque);
            Button imb2 = (Button)dnew2.findViewById(R.id.close);
            TextView dt = (TextView) dnew2.findViewById(R.id.dtitle);
            TextView ex=(TextView)dnew2.findViewById(R.id.ex);
            TextView dd=(TextView)dnew2.findViewById(R.id.ddet);
            ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
            dback.setImageResource(R.drawable.zzz);
            dt.setText(getString(R.string.gd1));
            ex.setText(getString(R.string.gd2));
            ex.setVisibility(View.VISIBLE);
            dd.setText(getString(R.string.gd3));
            imb2.setText(getString(R.string.gd4));
            imb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dnew2.dismiss();
                }
            });

            if (firstt==4){

                dnew2.show();
                editor = mPrefs.edit();
                editor.putInt(firsttime,5);
                editor.apply();
            }

            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainl, top.newInstance(top.NODIR));
        ft.commit();

       secondFragment =new SecondFragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondfrag,secondFragment,secondFragment.getTag())
                .commit();

        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.setMinRadius(60);
        menu.showTooltip(false);
        menu.setAnim(300, 300, ArcMenu.ANIM_MIDDLE_TO_RIGHT, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            com.bvapp.arcmenulibrary.widget.FloatingActionButton item = new com.bvapp.arcmenulibrary.widget.FloatingActionButton(this);  //Use internal fab as a child
            item.setSize(com.bvapp.arcmenulibrary.widget.FloatingActionButton.SIZE_MINI);  //set minimum size for fab 42dp
            item.setShadow(true); //enable to draw shadow
            item.setIcon(ITEM_DRAWABLES[i]); //add icon for fab
            item.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));  //set menu button normal color programmatically
            menu.setChildSize(item.getIntrinsicHeight());
            final int position = i;

            menu.addItem(item, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        intent0 = new Intent(group.this, questions.class);
                        startActivity(intent0);
                        finish();


                    }
                    if (position == 2) {
                        intent0 = new Intent(group.this, matches.class);
                        intent0.putExtra("target","random");
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 1) {
                        intent0 = new Intent(group.this, connections.class);
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 3) {
                        intent0 = new Intent(group.this, MainActivity.class);
                        intent0.putExtra("target", "none");
                        finish();
                        startActivity(intent0);


                    }
                }
            });


        }
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
