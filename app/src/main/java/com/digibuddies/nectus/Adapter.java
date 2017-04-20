package com.digibuddies.nectus;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.digibuddies.nectus.connections.custom_font;

public class Adapter extends RecyclerView.Adapter<Adapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");


    public Adapter(List<data> kdata) {
        this.kdata = kdata;
    }

    @Override
    public cardadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        cardadapter pvh = new cardadapter(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final cardadapter holder, final int position) {
        final data temp = kdata.get(position);
        holder.tv1.setText(temp.getUname());
        holder.tv2.setText("Request Pending...");
        myRef.child(temp.getDevid()).child(connections.kid).child("request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("msm",dataSnapshot.getValue().toString());
                if (dataSnapshot.getValue().toString().equals("ACCEPTED")){
                    holder.tv2.setText(temp.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        holder.tv3.setText(temp.getOp1()+", you can call me "+temp.getUname()+". I am here since "+temp.getOp2()+". Currently I am a student of "+temp.getOp3()+" and I love to practice my "+temp.getOp4()+" skills. I would like to "+temp.getOp5()+" someday. My friends say I'm "+temp.getOp6()+". I just "+temp.getOp7()+" "+temp.getOp8()+" I spend most of my day "+temp.getOp9()+". A person with same mind as mine would be "+temp.getOp10()+".");

        holder.tv4.setText(temp.getMp()+"% similar");

        holder.tv5.setText(temp.getTime());

        holder.kcv.setImageResource(temp.getAid());

        holder.rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kdata.remove(temp);
                connections.delete(temp.getDevid());
                Snackbar.make(view, "Connection Removed!",
                        Snackbar.LENGTH_LONG).show();

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
        TextView tv1, tv2, tv3, tv4, tv5;
        CircleImageView kcv;
        Button rm;


        public cardadapter(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.kuser);
            tv2 = (TextView) itemView.findViewById(R.id.kmail);
            tv3 = (TextView) itemView.findViewById(R.id.kdetail);
            tv4 = (TextView) itemView.findViewById(R.id.kmatch);
            tv5 = (TextView) itemView.findViewById(R.id.kdate);
            kcv=(CircleImageView)itemView.findViewById(R.id.kav);
            rm=(Button)itemView.findViewById(R.id.remove);
            tv3.setTypeface(custom_font);


        }
    }
}
