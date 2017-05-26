package com.digibuddies.cnectus;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.digibuddies.cnectus.connections.custom_font;

public class Adapter extends RecyclerView.Adapter<Adapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");
    DatabaseReference myRefcht = database.getReference("chat");
    Query myRefcht2;
    ArrayList<ArrayList<String>> kk = new ArrayList<ArrayList<String>>();
    ArrayList<String>[] kk2=(ArrayList<String>[]) new ArrayList[20];
    List<List<String>> getm = new ArrayList<List<String>>(4);
    int i=0,counter=0,j=0;
      private FirebaseListAdapter<chatmessage> adapt;

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
        for (int i =0 ; i< 20 ;i++) {
            kk2[i] = new ArrayList<>();
        }
        final data temp = kdata.get(position);
        myRefcht.child(connections.kid).child(kdata.get(position).getDevid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               kk2[position].add(dataSnapshot.getKey());
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
        holder.tv1.setText(temp.getUname());
        holder.tv2.setText("Checking Status...");
        myRef.child(temp.getDevid()).child(connections.kid).child("request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("ACCEPTED")){
                    //holder.tv2.setText(temp.getEmail());
                    holder.tv2.setVisibility(View.INVISIBLE);
                    holder.cht.setVisibility(View.VISIBLE);
                }
                else if (dataSnapshot.getValue().toString().equals("REMOVED")){
                    holder.tv2.setText("Connection no longer exists!");
                }
                else holder.tv2.setText("Request Pending!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.tv3.setText(temp.getOp1()+", you can call me "+temp.getUname()+". I was born in "+temp.getOp2()+". Currently I am living in "+temp.getOp3()+" and I love to practice my "+temp.getOp4()+" skills. I would like to "+temp.getOp5()+" someday. My friends say I'm "+temp.getOp6()+". I just "+temp.getOp7()+" "+temp.getOp8()+" I spend most of my day "+temp.getOp9()+". A person with same mind as mine would be "+temp.getOp10()+".");

        holder.tv4.setText(temp.getMp()+"% similar");

        holder.tv5.setText(temp.getTime());

        holder.kcv.setImageResource(temp.getAid());

        holder.rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kdata.remove(temp);
                myRef.child(temp.getDevid()).child(connections.kid).child("request").setValue("REMOVED");
                myRef.child(connections.kid).child(temp.getDevid()).child("request").setValue("REMOVED");
                connections.delete(temp.getDevid());
                myRefcht.child(connections.kid).child(temp.getDevid()).removeValue();
                myRefcht.child(temp.getDevid()).child(connections.kid).removeValue();
                Snackbar.make(view, "Connection Removed!",
                        Snackbar.LENGTH_LONG).show();

            }
        });

        holder.cht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dialog.show();
            }
        });

        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefcht.child(temp.getDevid()).child(connections.kid).push().setValue(new chatmessage(holder.input.getText().toString(),connections.usn,"UNREAD"));
                myRefcht.child(connections.kid).child(temp.getDevid()).push().setValue(new chatmessage(holder.input.getText().toString(),connections.usn,"READ"));

                holder.input.setText("");
            }
        });
        adapt = new FirebaseListAdapter<chatmessage>((Activity) holder.itemView.getContext(), chatmessage.class,
                R.layout.message, myRefcht.child(connections.kid).child(temp.getDevid())) {
            @Override
            protected void populateView(View v, chatmessage model, int position2) {
                myRefcht.child(connections.kid).child(temp.getDevid()).child(kk2[position].get(position2)).child("read").setValue("READ");
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                // Set their text
                messageText.setText(model.getMessage());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getTime()));
                messageUser.setText(model.getUser());
                if(model.getUser().equals(connections.usn))
                {
                messageUser.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.buttoncol));
            }
            else messageUser.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.first_slide_background));
                if(position2>10) {
                                        if(kk2[position].size()>0){
                                            myRefcht.child(connections.kid).child(temp.getDevid()).child(kk2[position].get(0)).removeValue();
                                            kk2[position].remove(0);

                                        }

                                        }

            }
        };
        holder.listOfMessages.setAdapter(adapt);


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
        Button rm,cht;
        Dialog dialog;
        FloatingActionButton fab;
        EditText input;
        ListView listOfMessages;


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
            cht=(Button)itemView.findViewById(R.id.cht);
            dialog=new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.chat);
            fab =(FloatingActionButton)dialog.findViewById(R.id.fab);
            input = (EditText)dialog.findViewById(R.id.input);
            listOfMessages = (ListView)dialog.findViewById(R.id.list_of_messages);

        }

    }
}
