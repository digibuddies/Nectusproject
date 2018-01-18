package com.digibuddies.cnectus;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    public View view;
    public ArrayList<String> arrayList;
    public ArrayList<Integer> arrayList2;
    public ListView listView;
    public listadapter listViewAdapter;
    String id;
    SQLiteDatabase db;
    Animation animation;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefcht = database.getReference("chat");
    DatabaseReference myRefreport = database.getReference("groups");
    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_second, container, false);
        db=SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().getAbsolutePath()+"groupdb", null);
        db.execSQL("CREATE TABLE IF NOT EXISTS groups(gname VARCHAR(20) PRIMARY KEY,aid INTEGER);");
        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        id= Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        listView=(ListView)view.findViewById(R.id.list);
        animation = AnimationUtils.loadAnimation(
                getActivity(), R.anim.slide_top_to_bottom);
        listViewAdapter=new listadapter(
                getActivity(),
                arrayList,arrayList2
        );
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
               gchat(position);
            }
        });

        Cursor c = db.rawQuery("Select gname,aid from groups",null);
        if (group.firstt!=4&& c.getCount()>0){
            c.moveToFirst();
            do {
                arrayList.add(c.getString(0));
                arrayList2.add(c.getInt(1));
            }while (c.moveToNext());
            Collections.reverse(arrayList);
            Collections.reverse(arrayList2);
            listViewAdapter.notifyDataSetChanged();
            if(getActivity().getIntent().getIntExtra("groupn",0)==1){
                gchat(arrayList.indexOf(getActivity().getIntent().getStringExtra("grp")));
                getActivity().getIntent().removeExtra("groupn");
                getActivity().getIntent().removeExtra("grp");
            }
        }
        else {
            db.execSQL("INSERT INTO groups VALUES('Cnectus Family','"+R.drawable.family+"')");
            arrayList.add("Cnectus Family");
            arrayList2.add(R.drawable.family);
            myRefreport.child("status").child(id).child("Cnectus Family").setValue("Allowed");
            Log.d("araaaa", String.valueOf(arrayList));
            listViewAdapter.notifyDataSetChanged();
            }
        return view;


    }

    public void creatingNewListView(final String addName) {
        int aid=0;
        if(addName!=null) {
            if (addName.equals("Cnectus Family")){
                aid = R.drawable.family;
                arrayList2.add(0,aid);
            }else if (addName.equals("Wizards")){
                aid=R.drawable.wizard;
                arrayList2.add(0,aid);
            }else if (addName.equals("Artists")){
                aid=R.drawable.art;
                arrayList2.add(0,aid);
            }else if (addName.equals("Dreamers")){
                aid=R.drawable.dreamers;
                arrayList2.add(0,aid);
            }else if (addName.equals("Geeks")){
                aid=R.drawable.geek;
                arrayList2.add(0,aid);
            }else if (addName.equals("Adventurers")){
                aid=R.drawable.adventure;
                arrayList2.add(0,aid);
            }
            arrayList.add(0,addName);
            Log.d("adaaar",addName);
            myRefreport.child("status").child(id).child(addName).setValue("Allowed");
            db.execSQL("INSERT INTO groups VALUES('"+addName+"','"+aid+"');");
            listViewAdapter.notifyDataSetChanged();
            listView.startAnimation(animation);
        }
    }

    public void deleteFromList(String delName) {
        if (delName != null) {
            myRefreport.child("status").child(id).child(delName).setValue("Removed");
            db.execSQL("DELETE FROM groups WHERE gname = '"+delName+"'");
            int pos = arrayList.indexOf(delName);
            arrayList.remove(pos);
            arrayList2.remove(pos);
            listViewAdapter.notifyDataSetChanged();
            listView.startAnimation(animation);
        }
    }
    public void gchat(int position){
        Intent intent = new Intent(getActivity(),groupchat.class);
        intent.putExtra("activity","grp");
        intent.putExtra("gname",arrayList.get(position));
        intent.putExtra("aid",arrayList2.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
