package com.digibuddies.cnectus;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.digibuddies.cnectus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class search extends AppCompatActivity {
    String kid;
    Cursor c;
    RecyclerView srv;
    Spinner scr;
    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    DatabaseReference myRef = database.getReference("Users");;
    String fill;
    TextView inf,sh,sd,txt;

    Typeface custom_font;
    data snap;
    sadapter adapter;
    FloatingTextButton find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
           }
            kid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            srv=(RecyclerView)findViewById(R.id.rvs);
            srv.setLayoutManager(new LinearLayoutManager(this));
            srv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            inf=(TextView)findViewById(R.id.infro);
            sh=(TextView)findViewById(R.id.shead);
            sd=(TextView)findViewById(R.id.sdet);

            final Dialog dialogref = new Dialog(this);
            dialogref.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogref.setContentView(R.layout.refresh);
            txt=(TextView)dialogref.findViewById(R.id.txt);
            txt.setText("Searching!");
            custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Light.ttf");
            sh.setTypeface(custom_font);sd.setTypeface(custom_font);
            scr=(Spinner)findViewById(R.id.spinnerserc);
            final List<data> kdata = new ArrayList<data>();

            find=(FloatingTextButton) findViewById(R.id.find);
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kdata.clear();
                    dialogref.show();
                    fill=scr.getSelectedItem().toString();

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()){
                                if(dsp.child("op5").getValue().toString().equals(fill)){

                                    snap=dsp.getValue(data.class);
                                    snap.setDevid(dsp.getKey());
                                    kdata.add(snap);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            adapter = new sadapter(kdata,custom_font,kid);
                            srv.setAdapter(adapter);
                            if (adapter.getItemCount()>0)
                            {
                                inf.setVisibility(View.INVISIBLE);
                            }
                           if (adapter.getItemCount()>0||kdata.size()==0){
                               new Handler().postDelayed(new Runnable() {

                                   @Override
                                   public void run() {
                                       dialogref.hide();
                                       }

                               },1500);
                           }
                        }

                    },8000);


                }
            });



    }
}
