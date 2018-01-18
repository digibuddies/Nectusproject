package com.digibuddies.cnectus;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import io.ghyeok.stickyswitch.widget.StickySwitch;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class reqadapter extends RecyclerView.Adapter<reqadapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    Context context;
    String cid,usn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    public reqadapter(List<data> kdata,Context context,String usn) {
        this.kdata = kdata;
        this.context=context;
        this.usn=usn;
    }

    @Override
    public cardadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestcard, parent, false);
        cardadapter pvh = new cardadapter(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final cardadapter holder, final int position) {
        final data temp = kdata.get(position);
        cid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        holder.us.setText(temp.getUname());
        holder.av.setImageResource(temp.getAid());
        if (temp.getMp().equals(" ")){
            holder.mat.setText("");
        }else
        holder.mat.setText(temp.getMp()+"%");
        holder.stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                if(direction.toString().equals("RIGHT")){
                    String currentDateTime = java.text.DateFormat.getDateInstance().format(new Date());
                    myRef.child("contact").child(cid).child(temp.getDevid()).child("request").setValue("ACCEPTED");
                    myRef.child("contact").child(temp.getDevid()).child(cid).child("request").setValue(temp.getDevid().equals("123")?"ACCEPTED2":"ACCEPTED");
                    String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('"+temp.getDevid()+"','"+currentDateTime+"','"+temp.getMp()+"','"+temp.getAid()+"','"+temp.getUname()+"','"+temp.getOp1()+"','"+temp.getOp2()+"','"+temp.getOp3()+"','"+temp.getOp4()+"','"+temp.getOp5()+"','"+temp.getOp6()+"','"+temp.getOp7()+"','"+temp.getOp8()+"','"+temp.getOp9()+"','"+temp.getOp10()+"','"+temp.getOp11()+"','"+temp.getOp12()+"','"+temp.getOp01()+"');";
                    holder.db2.execSQL(queryx);
                    holder.dbr.execSQL("DELETE FROM matches WHERE devid='"+temp.getDevid()+"'");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.stickySwitch.setVisibility(View.INVISIBLE);
                            holder.acc.setVisibility(View.VISIBLE);
                            myRef.child("chat").child(temp.getDevid()).child(cid).push().setValue(new chatmessage(context.getString(R.string.hi),usn,"UNREAD",cid));
                            myRef.child("chat").child(cid).child(temp.getDevid()).push().setValue(new chatmessage(context.getString(R.string.hi),usn,"READ",cid));

                        }
                    },1000);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return kdata.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class cardadapter extends RecyclerView.ViewHolder {

        TextView us,mat,acc;
        SQLiteDatabase db2,dbr;
        CircleImageView av;
        StickySwitch stickySwitch;
        public cardadapter(View itemView) {
            super(itemView);
            us=(TextView)itemView.findViewById(R.id.kuser);
            mat=(TextView)itemView.findViewById(R.id.matchc);
            av=(CircleImageView)itemView.findViewById(R.id.kav);
            stickySwitch=(StickySwitch)itemView.findViewById(R.id.ssc);
            db2=openOrCreateDatabase(context.getFilesDir().getAbsolutePath()+"ContDB", null);
            db2.execSQL(context.getString(R.string.cdb));
            dbr = openOrCreateDatabase(context.getFilesDir().getAbsolutePath() + "reqDB", null);
            acc=(TextView)itemView.findViewById(R.id.acc);


        }

    }

}
