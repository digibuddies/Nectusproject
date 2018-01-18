package com.digibuddies.cnectus;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.digibuddies.cnectus.connections.adapter;

class Adapter extends RecyclerView.Adapter<Adapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");
    DatabaseReference myRefcht = database.getReference("chat");
      private FirebaseListAdapter<chatmessage> adapt;
    String usn,kid;
    Context context;
   ChildEventListener listener;

    Typeface custom_font;

    Adapter(Context context,List<data> kdata, String usn, String kid, Typeface custom_font) {
        this.context=context;
        this.kdata = kdata;
        this.usn=usn;
        this.kid=kid;
        this.custom_font=custom_font;
    }

    @Override
    public cardadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        cardadapter pvh = new cardadapter(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder( final cardadapter holder,int position) {

            Log.d("positionn", String.valueOf(holder.getAdapterPosition()));
        final data temp = kdata.get(holder.getAdapterPosition());
        holder.tv1.setText(temp.getUname());
        holder.tv2.setText("Checking Status...");
        holder.tv2.setVisibility(View.VISIBLE);
        holder.cht.setVisibility(View.INVISIBLE);
        myRef.child(temp.getDevid()).child(kid).child("request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                if (temp.getDevid().equals("123")?dataSnapshot.getValue().toString().equalsIgnoreCase("ACCEPTED2"):dataSnapshot.getValue().toString().equals("ACCEPTED")){
                    holder.tv2.setVisibility(View.INVISIBLE);
                    holder.cht.setVisibility(View.VISIBLE);
                }
                else if (dataSnapshot.getValue().toString().equals("REMOVED")){
                    holder.tv2.setText("Connection no longer exists!");
                }
                else if (dataSnapshot.getValue().toString().equals("DECLINED")){
                    holder.tv2.setText("Request Declined!");
                }
                else holder.tv2.setText("Request Pending!");
            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(!temp.getMp().equals(" ")){
            holder.tv4.setText(temp.getMp()+"% similar");}
        else {
            holder.tv4.setText(" ");}

        holder.tv5.setText(temp.getTime());

        holder.kcv.setImageResource(temp.getAid());

        holder.rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kdata.remove(temp);
                myRef.child(temp.getDevid()).child(kid).removeValue();
                myRef.child(kid).child(temp.getDevid()).child("request").setValue("REMOVED");
                connections.delete(temp.getDevid());
                myRefcht.child(kid).child(temp.getDevid()).removeValue();
                myRefcht.child(temp.getDevid()).child(kid).removeValue();
                Snackbar.make(view, "Connection Removed!",
                        Snackbar.LENGTH_LONG).show();

            }
        });

        if (temp.getUname().equals("CNECTUS BOT")){
            holder.rm.setVisibility(View.GONE);
        }

        holder.tv3.setText(temp.getOp1() + ", you can call me " + temp.getUname() + ". I\'m a " + temp.getOp01() + " born in " + temp.getOp2() + " and currently I live in " + temp.getOp3() + ". I love to practice my " + temp.getOp4() + " skills. I would like to " + temp.getOp5() + " someday. My friends say I'm " + temp.getOp6() + ". I just love " + temp.getOp7() + " and i hate " + temp.getOp8() + " I spend most of my day " + temp.getOp9() + ". A person with same mind as mine would be " + temp.getOp10());

        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true) {
                    holder.sw.setText("Less Info");
                    holder.tv3.setVisibility(View.VISIBLE);
                }
                else{
                    holder.tv3.setVisibility(View.GONE);
                    holder.sw.setText("More Info");
                    Adapter.this.notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });

        holder.cht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Chat.class);
                intent.putExtra("activity","con");
                intent.putExtra("temp",temp);
                intent.putExtra("pos",holder.getAdapterPosition());
                context.startActivity(intent);


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


    class cardadapter extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3, tv4, tv5;
        CircleImageView kcv;
        Button rm,cht;
        Switch sw;


        cardadapter(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.kuser);
            tv2 = (TextView) itemView.findViewById(R.id.kmail);
            tv3 = (TextView) itemView.findViewById(R.id.kdetail);
            tv4 = (TextView) itemView.findViewById(R.id.kmatch);
            tv5 = (TextView) itemView.findViewById(R.id.kdate);
            sw = (Switch)itemView.findViewById(R.id.sw);
            kcv=(CircleImageView)itemView.findViewById(R.id.kav);
            rm=(Button)itemView.findViewById(R.id.remove);
            tv3.setTypeface(custom_font);
            cht=(Button)itemView.findViewById(R.id.cht);
        }

    }


}
