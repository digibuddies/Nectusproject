package com.digibuddies.cnectus;

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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.digibuddies.cnectus.connections.kk2;

public class Chat extends AppCompatActivity {
    ListView listOfMessages;
    ChildEventListener listener;
    FirebaseListAdapter<chatmessage> adapt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefcht = database.getReference("chat");
    Intent intent;
    String kid,usn="";
    CircleImageView civ;
    ImageButton im,back;
    data temp;
    TextView head;
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
        usn = intent.getStringExtra("usn");
        kid = intent.getStringExtra("kid");
        temp = (data) intent.getSerializableExtra("temp");
        pos = intent.getIntExtra("position",0);

        anim = (AnimationDrawable) rel.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        head.setText(temp.getUname());
        civ.setImageResource(temp.getAid());

        adapt = new FirebaseListAdapter<chatmessage>(this, chatmessage.class,
                R.layout.message, myRefcht.child(kid).child(temp.getDevid())) {
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
                        messageUser.setTextColor(ContextCompat.getColor(v.getContext(), R.color.buttoncol));
                        linearLayout.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.shape1));

                    } else {
                        messageUser.setTextColor(ContextCompat.getColor(v.getContext(), R.color.first_slide_background));
                        linearLayout.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.shape2));

                    }
                }

                if (position2 > 10) {
                    if (kk2[pos].size() > 0) {
                        myRefcht.child(kid).child(temp.getDevid()).child(kk2[pos].get(0)).removeValue();
                        kk2[pos].remove(0);


                    }
                }
            }
        };
       listOfMessages.setAdapter(adapt);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefcht.child(kid).child(temp.getDevid()).push().setValue(new chatmessage(input.getText().toString(),usn,"READ"));
                myRefcht.child(temp.getDevid()).child(kid).push().setValue(new chatmessage(input.getText().toString(),usn,"UNREAD"));
                input.setText("");

            }
        });


        listener = myRefcht.child(kid).child(temp.getDevid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kk2[pos].add(dataSnapshot.getKey());
                myRefcht.child(kid).child(temp.getDevid()).child(dataSnapshot.getKey()).child("read").setValue("READ");
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
        myRefcht.child(kid).child(temp.getDevid()).removeEventListener(listener);
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
}
