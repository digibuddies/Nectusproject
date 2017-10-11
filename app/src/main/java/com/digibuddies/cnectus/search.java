package com.digibuddies.cnectus;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static com.digibuddies.cnectus.matches.flag;

public class search extends AppCompatActivity {
    String kid;
    Cursor c;
    RecyclerView srv;
    Spinner scr;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ;
    DatabaseReference myRef = database.getReference("Users");
    ;
    String fill;
    static Dialog dnew2;
    EditText mail;
    Intent intent0;
    TextView inf, sh, sd, txt;
    Typeface custom_font;
    data snap;
    sadapter adapter;
    FloatingTextButton find;
    private int[] ITEM_DRAWABLES = {R.drawable.help, R.drawable.talk, R.drawable.dice, R.drawable.home};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        kid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        srv = (RecyclerView) findViewById(R.id.rvs);
        mail=(EditText)findViewById(R.id.email);
        srv.setLayoutManager(new LinearLayoutManager(this));
        srv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        inf = (TextView) findViewById(R.id.infro);
        sh = (TextView) findViewById(R.id.shead);
        sd = (TextView) findViewById(R.id.sdet);

        final Dialog dialogref = new Dialog(this);
        dialogref.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogref.setContentView(R.layout.refresh);
        dnew2 = new Dialog(this);
        dnew2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dnew2.setContentView(R.layout.dialogque);
        Button imb2 = (Button) dnew2.findViewById(R.id.close);
        TextView dt = (TextView) dnew2.findViewById(R.id.dtitle);
        TextView dd = (TextView) dnew2.findViewById(R.id.ddet);
        TextView ex = (TextView) dnew2.findViewById(R.id.ex);
        ImageView dback = (ImageView) dnew2.findViewById(R.id.dback);
        dback.setImageResource(R.drawable.zzz);
        ex.setVisibility(View.VISIBLE);
        ex.setText(getString(R.string.md1));
        dt.setText(getString(R.string.md2));
        dd.setText(getString(R.string.md3));
        imb2.setText(getString(R.string.md4));
        imb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dnew2.dismiss();
                Intent intent = new Intent(search.this, connections.class);
                finish();
                startActivity(intent);


            }
        });




        txt = (TextView) dialogref.findViewById(R.id.txt);
        txt.setText("Searching!");
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        sh.setTypeface(custom_font);
        sd.setTypeface(custom_font);
        scr = (Spinner) findViewById(R.id.spinnerserc);
        final List<data> kdata = new ArrayList<data>();

        find = (FloatingTextButton) findViewById(R.id.find);
        scr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mail.setText("");
                mail.clearFocus();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                kdata.clear();
                dialogref.show();
                if(mail.length()<=0) {
                    fill = scr.getSelectedItem().toString();
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                if (dsp.child("op5").getValue().toString().equals(fill)) {

                                    snap = dsp.getValue(data.class);
                                    snap.setDevid(dsp.getKey());
                                    kdata.add(snap);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    final String ml=mail.getText().toString();
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                if (dsp.child("email").getValue().toString().equals(ml)) {

                                    snap = dsp.getValue(data.class);
                                    snap.setDevid(dsp.getKey());
                                    kdata.add(snap);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        adapter = new sadapter(kdata, custom_font, kid,view.getContext());
                        srv.setAdapter(adapter);
                        if (adapter.getItemCount() > 0) {
                            inf.setVisibility(View.INVISIBLE);
                        }
                        if (adapter.getItemCount() > 0 || kdata.size() == 0) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    dialogref.hide();
                                }

                            }, 1500);
                        }
                    }

                }, 8000);


            }
        });
        ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
        menu.attachToRecyclerView(srv);
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
                        intent0 = new Intent(search.this, questions.class);
                        startActivity(intent0);
                        finish();


                    }
                    if (position == 2) {
                        intent0 = new Intent(search.this, matches.class);
                        intent0.putExtra("target","random");
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 1) {
                        intent0 = new Intent(search.this, connections.class);
                        finish();
                        startActivity(intent0);


                    }
                    if (position == 3) {
                        intent0 = new Intent(search.this, MainActivity.class);
                        intent0.putExtra("target", "none");
                        finish();
                        startActivity(intent0);


                    }
                }
            });


        }
    }

    public static void showd(){
        if(flag==1){
            dnew2.show();
            flag=0;
        }
    }
}
