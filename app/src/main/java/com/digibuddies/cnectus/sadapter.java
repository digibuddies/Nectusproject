package com.digibuddies.cnectus;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.digibuddies.cnectus.matches.flag;

public class sadapter extends RecyclerView.Adapter<sadapter.cardadapter> {

    List<data> kdata = new ArrayList<data>();
    List<data> ndata = new ArrayList<data>();
    String mp=" ";
    Context context;
    int allow = 0;
    private int lastPosition = -1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("contact");

    String usn,kid;
    Typeface custom_font;

    sadapter(List<data> kdata,List<data> ndata, Typeface custom_font, String kid,Context context) {
        this.kdata = kdata;
        this.ndata=ndata;
        this.custom_font=custom_font;
        this.kid=kid;
        this.context=context;
    }
    @Override
    public cardadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == R.layout.scard){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scard, parent, false);
        }

        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
        }

        return new cardadapter(v);
    }

    @Override
    public void onBindViewHolder(final cardadapter holder, final int position) {

        if(position == kdata.size()&&ndata.size()>1) {
            holder.last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kdata.addAll(ndata);
                    notifyDataSetChanged();
                    holder.last.setVisibility(View.GONE);

                    }
            });
        }
        else {

            holder.tv3.setTypeface(custom_font);

            String s = " a ";
            final data temp = kdata.get(position);
            if (temp.getOp01().equals("")) {
                s = "";
            } else {
                s = s + temp.getOp01();
            }
            holder.tv1.setText(temp.getUname());
            holder.tv3.setText(temp.getOp1() + ", you can call me " + temp.getUname() + ". I\'m" + s + " born in " + temp.getOp2() + " and currently I live in " + temp.getOp3() + ". I love to practice my " + temp.getOp4() + " skills. I would like to " + temp.getOp5() + " someday. My friends say I'm " + temp.getOp6() + ". I just love " + temp.getOp7() + " and i hate " + temp.getOp8() + " I spend most of my day " + temp.getOp9() + ". A person with same mind as mine would be " + temp.getOp10() + ".");
            mp = holder.calcmp(temp.getDevid());
            temp.setMp(mp);
            if (!temp.getMp().equals(" ")) {
                holder.tv4.setText(temp.getMp() + "% similar");
            } else {
                holder.tv4.setText(" ");
            }
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
                    if (holder.ssw.getDirection().name().equals("RIGHT")) {
                        myRef.child(temp.getDevid()).child(kid).child("request").setValue("RIGHT");
                        myRef.child(temp.getDevid()).child(kid).child("mp").setValue(temp.getMp());
                        holder.save(temp);
                    }

                    if (holder.ssw.getDirection().name().equals("RIGHT")) {
                        Snackbar.make(holder.itemView, "Request Sent!",
                                Snackbar.LENGTH_SHORT).show();
                        if (context.getClass().toString().equals("class com.digibuddies.cnectus.matches")) {
                            matches.showd();
                        } else if (context.getClass().toString().equals("class com.digibuddies.cnectus.search")) {
                            search.showd();
                        }
                    }
                    holder.dialog.dismiss();

                }
            });

        }

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        if (ndata.size()>1)return kdata.size()+1;
        else return kdata.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemViewType(int position) {
        return (position == kdata.size()&&ndata.size()>1) ? R.layout.button : R.layout.scard;
    }

    @Override
    public void onViewDetachedFromWindow(cardadapter holder) {
        holder.clearAnimation();
    }

    class cardadapter extends RecyclerView.ViewHolder {

        TextView tv1,tv3, tv4,csy,csn;
        CircleImageView kcv;
        Button cn;
        CardView card;
        Dialog dialog;
        SQLiteDatabase dbs,dbm;
        Button back,last;
        StickySwitch ssw;
        String currentDateTime;

        cardadapter(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.kuser);
            tv3 = (TextView) itemView.findViewById(R.id.kdetail);
            tv4 = (TextView) itemView.findViewById(R.id.smat);
            card = (CardView)itemView.findViewById(R.id.scd);
            kcv=(CircleImageView)itemView.findViewById(R.id.kav);
            cn=(Button)itemView.findViewById(R.id.b1);
            last=(Button)itemView.findViewById(R.id.lastb);
            dialog=new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            back=(Button)dialog.findViewById(R.id.back);
            ssw=(StickySwitch)dialog.findViewById(R.id.dss);
            csy=(TextView)dialog.findViewById(R.id.cy);
            csn=(TextView)dialog.findViewById(R.id.cn);
            currentDateTime = java.text.DateFormat.getDateInstance().format(new Date());
            dbs=openOrCreateDatabase(context.getFilesDir().getAbsolutePath()+"ContDB", null);
            dbm=openOrCreateDatabase(context.getFilesDir().getAbsolutePath()+"matrec", null);
            dbm.execSQL("CREATE TABLE IF NOT EXISTS matches(usid VARCHAR(20) PRIMARY KEY,mp VARCHAR(20));");
            dbs.execSQL(context.getString(R.string.condb));
        }

        void save(data k){

            String queryx = "INSERT OR REPLACE INTO connect (dvid,time,mp,aid,email,uname,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op01) VALUES('"+k.getDevid()+"','"+currentDateTime+"','"+k.getMp()+"','"+k.getAid()+"','"+k.getEmail()+"','"+k.getUname()+"','"+k.getOp1()+"','"+k.getOp2()+"','"+k.getOp3()+"','"+k.getOp4()+"','"+k.getOp5()+"','"+k.getOp6()+"','"+k.getOp7()+"','"+k.getOp8()+"','"+k.getOp9()+"','"+k.getOp10()+"','"+k.getOp11()+"','"+k.getOp12()+"','"+k.getOp01()+"');";
            dbs.execSQL(queryx);
        }
        String calcmp(String uu){
            Cursor c;
            String s=" ";
            c=dbm.rawQuery("SELECT mp FROM matches WHERE usid='"+uu+"'",null);
            c.moveToFirst();
            if (c.getCount()>0){
            s= c.getString(0);}
            c.close();
            return s;
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }

}
