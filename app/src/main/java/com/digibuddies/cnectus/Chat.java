package com.digibuddies.cnectus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    ListView listOfMessages;
    ChildEventListener listener;
    FirebaseListAdapter<chatmessage> adapt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefcht = database.getReference("chat");
    Intent intent;
    String kid,usn="";
    public ArrayList<String>[] kk2;
    CircleImageView civ;
    ImageButton im,back;
    data temp;
    TextView head;
    String oname,oid,act;
    int oaid;
    AnimationDrawable anim;
    RelativeLayout rel;
    FloatingActionButton fab;
    EditText input;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        head = (TextView)findViewById(R.id.head);
        civ = (CircleImageView)findViewById(R.id.avatar);
        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        fab =(FloatingActionButton)findViewById(R.id.fab);
        rel = (RelativeLayout)findViewById(R.id.rel);
        input = (EditText)findViewById(R.id.input);
        intent = getIntent();
        im = (ImageButton) findViewById(R.id.imageButton);
        back = (ImageButton) findViewById(R.id.back);
        usn = MainActivity.uname;
        kid = MainActivity.id;
        act = intent.getStringExtra("activity");

        if (act.equals("con")){
            temp = (data) intent.getSerializableExtra("temp");
            pos = intent.getIntExtra("position",0);
            oaid = temp.getAid();
            oid = temp.getDevid();
            oname = temp.getUname();
        }
        else if (act.equals("grp")){
           oname=oid=intent.getStringExtra("gname");
            oaid = intent.getIntExtra("aid",0);
        }

        anim = (AnimationDrawable) rel.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        head.setText(oname);
        civ.setImageResource(oaid);

        kk2=(ArrayList<String>[]) new ArrayList[60];
        for (int i =0 ; i< 60 ;i++) {
            kk2[i] = new ArrayList<String>();
        }

        adapt = new FirebaseListAdapter<chatmessage>(this, chatmessage.class,
                R.layout.message, myRefcht.child(oid)) {
            @Override
            protected void onDataChanged() {
                notifyDataSetChanged();
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                chatmessage model = getItem(position);
                if(model.getUser()!=null) {
                    if (!model.getUser().equals(usn)) {
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayout, viewGroup, false);
                    } else {
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message2, viewGroup, false);
                    }
                }
                else {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayout, viewGroup, false);
                }

                populateView(view, model, position);
                return view;
            }
            @Override
            protected void populateView(View v, chatmessage model, int position2) {
                View view=v;
                if (model.getUser() != null) {

                    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.layoutl);
                    TextView messageText = (TextView) view.findViewById(R.id.message_text);
                    TextView messageUser = (TextView) view.findViewById(R.id.message_user);
                    TextView messageTime = (TextView) view.findViewById(R.id.message_time);
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getTime()));
                    messageUser.setText(model.getUser());
                    messageText.setText(model.getMessage());
                    if (!(model.getUser().equals(usn))) {
                        messageUser.setTextColor(ContextCompat.getColor(v.getContext(), R.color.light_cyan));
                        linearLayout.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.shape1));

                    } else {
                        messageUser.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                        linearLayout.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.shape2));

                    }
                    if (act.equals("grp")){
                        messageUser.setVisibility(View.VISIBLE);
                    }
                }
                if (act.equals("con")){
                if (position2 > 10) {
                    if (kk2[pos].size() > 0) {
                        myRefcht.child(kid).child(oid).child(kk2[pos].get(0)).removeValue();
                        kk2[pos].remove(0);

                    }
                    }
                }
                else if (act.equals("grp")){
                    if (position2 > 50) {
                        if (kk2[pos].size() > 0) {
                            myRefcht.child(oid).child(kk2[pos].get(0)).removeValue();
                            kk2[pos].remove(0);

                        }
                    }
                }
            }
        };
       listOfMessages.setAdapter(adapt);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inp = input.getText().toString();
                if(!(inp.equals("")||inp.equals(" "))){
                    if (act.equals("con")){
                myRefcht.child(kid).child(oid).push().setValue(new chatmessage(inp,usn,"READ"));
                myRefcht.child(oid).child(kid).push().setValue(new chatmessage(inp,usn,"UNREAD"));}
                else
                        myRefcht.child(oid).push().setValue(new chatmessage(inp,usn,"READ"));
                }
                input.setText("");
                InputMethodManager imm = (InputMethodManager)Chat.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                input.clearFocus();

            }
        });


        listener = myRefcht.child(oid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kk2[pos].add(dataSnapshot.getKey());
                if (act.equals("con")){
                myRefcht.child(kid).child(oid).child(dataSnapshot.getKey()).child("read").setValue("READ");}
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    public void onBackPressed() {
        finish();
        }

    @Override
    protected void onPause() {
        myRefcht.child(kid).child(oid).removeEventListener(listener);
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
}
