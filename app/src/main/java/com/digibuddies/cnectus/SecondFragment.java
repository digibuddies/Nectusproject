package com.digibuddies.cnectus;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    SQLiteDatabase db;
    Animation animation;

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
                Intent intent = new Intent(getActivity(),groupchat.class);
                intent.putExtra("activity","grp");
                intent.putExtra("gname",arrayList.get(position));
                intent.putExtra("aid",arrayList2.get(position));
                startActivity(intent);
            }
        });

        Cursor c = db.rawQuery("Select gname,aid from groups",null);
        if (c.getCount()>0){
            c.moveToFirst();
            do {
                arrayList.add(c.getString(0));
                arrayList2.add(c.getInt(1));
                Log.d("araaa", String.valueOf(arrayList));
            }while (c.moveToNext());
            Collections.reverse(arrayList);
            Collections.reverse(arrayList2);
            listViewAdapter.notifyDataSetChanged();
        }
        else {
            db.execSQL("INSERT INTO groups VALUES('Cnectus Family','"+R.drawable.family+"')");
            arrayList.add("cnectus family");
            arrayList2.add(R.drawable.family);
            Log.d("araaaa", String.valueOf(arrayList));
            listViewAdapter.notifyDataSetChanged();
            }
        return view;


    }

    public void creatingNewListView(String addName) {
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
            db.execSQL("INSERT INTO groups VALUES('"+addName+"','"+aid+"');");
            listViewAdapter.notifyDataSetChanged();
            listView.startAnimation(animation);
        }
    }

    public void deleteFromList(String delName) {
        if (delName != null) {
            db.execSQL("DELETE FROM groups WHERE gname = '"+delName+"'");
            int pos = arrayList.indexOf(delName);
            arrayList.remove(pos);
            arrayList2.remove(pos);
            listViewAdapter.notifyDataSetChanged();
            listView.startAnimation(animation);
        }
    }


}
