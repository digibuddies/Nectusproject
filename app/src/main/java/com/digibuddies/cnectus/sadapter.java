package com.digibuddies.cnectus;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class sadapter extends RecyclerView.Adapter<sadapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    String mp=" ";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");

    String usn,kid;
    Typeface custom_font;

    public sadapter(List<data> kdata, Typeface custom_font, String kid) {
        this.kdata = kdata;
        this.custom_font=custom_font;
        this.kid=kid;
    }
    @Override
    public cardadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scard, parent, false);
        cardadapter pvh = new cardadapter(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final cardadapter holder, final int position) {
        final data temp = kdata.get(position);
        holder.tv1.setText(temp.getUname());
        holder.tv3.setText(temp.getOp1()+", you can call me "+temp.getUname()+". I was born in "+temp.getOp2()+". Currently I am living in "+temp.getOp3()+" and I love to practice my "+temp.getOp4()+" skills. I would like to "+temp.getOp5()+" someday. My friends say I'm "+temp.getOp6()+". I just "+temp.getOp7()+" "+temp.getOp8()+" I spend most of my day "+temp.getOp9()+". A person with same mind as mine would be "+temp.getOp10()+".");
        mp=holder.calcmp(temp.getDevid());
        if(!mp.equals(" ")){
        holder.tv4.setText(mp+"% similar");}
        else {
            holder.tv4.setText(" ");}
        holder.kcv.setImageResource(temp.getAid());
        holder.cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.csy.setText(temp.getOp11());
                holder.csn.setText(temp.getOp12());
                holder.dialog.show();
            }
        });
        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ssw.getDirection().name().equals("RIGHT")){
                    myRef.child(temp.getDevid()).child(kid).child("request").setValue("RIGHT");
                    myRef.child(temp.getDevid()).child(kid).child("mp").setValue(mp);
                    temp.setMp(mp);
                    holder.save(temp);
                }

                if(holder.ssw.getDirection().name().equals("RIGHT")){
                    Snackbar.make(holder.itemView, "Request Sent!",
                            Snackbar.LENGTH_SHORT).show();
                }
                holder.dialog.dismiss();

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

        TextView tv1,tv3, tv4,csy,csn;
        CircleImageView kcv;
        Button cn;
        Dialog dialog;
        SQLiteDatabase dbs,dbm;
        Button back;
        StickySwitch ssw;
        String currentDateTime;

        public cardadapter(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.kuser);
            tv3 = (TextView) itemView.findViewById(R.id.kdetail);
            tv4 = (TextView) itemView.findViewById(R.id.smat);
            kcv=(CircleImageView)itemView.findViewById(R.id.kav);
            cn=(Button)itemView.findViewById(R.id.b1);
            tv3.setTypeface(custom_font);
            dialog=new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            back=(Button)dialog.findViewById(R.id.back);
            ssw=(StickySwitch)dialog.findViewById(R.id.dss);
            csy=(TextView)dialog.findViewById(R.id.cy);
            csn=(TextView)dialog.findViewById(R.id.cn);
            currentDateTime = java.text.DateFormat.getDateTimeInstance().format(new Date());
            File storagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/android/.data_21");
            dbs= SQLiteDatabase.openOrCreateDatabase(storagePath+"/"+"ContDB", null);
            dbm= SQLiteDatabase.openOrCreateDatabase(storagePath+"/"+"matrec", null);
            dbm.execSQL("CREATE TABLE IF NOT EXISTS matches(usid VARCHAR(20) PRIMARY KEY,mp VARCHAR(20));");
            dbs.execSQL("CREATE TABLE IF NOT EXISTS connect(dvid VARCHAR(20) PRIMARY KEY,time VARCHAR(20),mp varchar(20), aid INTEGER, email VARCHAR(20),uname VARCHAR(20), op1 VARCHAR(20),op2 VARCHAR(20),op3 VARCHAR(20),op4 VARCHAR(20),op5 VARCHAR(20),op6 VARCHAR(20),op7 VARCHAR(20),op8 VARCHAR(20),op9 VARCHAR(20),op10 VARCHAR(20),op11 VARCHAR(30),op12 VARCHAR(30));");

        }

        public void save(data k){

            String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12) VALUES('"+k.getDevid()+"','"+currentDateTime+"','"+k.getMp()+"','"+k.getAid()+"','"+k.getEmail()+"','"+k.getUname()+"','"+k.getOp1()+"','"+k.getOp2()+"','"+k.getOp3()+"','"+k.getOp4()+"','"+k.getOp5()+"','"+k.getOp6()+"','"+k.getOp7()+"','"+k.getOp8()+"','"+k.getOp9()+"','"+k.getOp10()+"','"+k.getOp11()+"','"+k.getOp12()+"');";
            dbs.execSQL(queryx);
        }
        public String calcmp(String uu){
            Cursor c;
            String s=" ";
            c=dbm.rawQuery("SELECT mp FROM matches WHERE usid='"+uu+"'",null);
            c.moveToFirst();
            if (c.getCount()>0){
            s= c.getString(0);}
            c.close();
            return s;
        }


    }

}
