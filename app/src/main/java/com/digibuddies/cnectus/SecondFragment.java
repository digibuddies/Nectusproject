package com.digibuddies.cnectus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    public View view;
    public ArrayList<String> arrayList;
    public ListView listView;
    public ArrayAdapter<String> listViewAdapter;


    /*
public String itemG="Geeks";
    public String itemAr="Artists";
    public String itemAd="adventurers";
    public String itemPr="pros";
    public String itemDd="daydreamers";
*/
    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view=inflater.inflate(R.layout.fragment_second, container, false);
      //  String[] groups={"geeks","cnectus family","artists","pro","adventurers","daydreamers"};
        //receiving values from switch

        /*
 itemG=bundle.getString("1");
        itemAd=bundle.getString("4");
        itemAr=bundle.getString("5");
        itemPr=bundle.getString("2");
        itemDd=bundle.getString("3");
*/


        listView=(ListView)view.findViewById(R.id.list);

        arrayList=new ArrayList<>();
        arrayList.add("cnectus family");

        listViewAdapter=new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrayList
        );
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
            }
        });
        return view;


    }

    public void creatingNewListView(String addName) {
        if(addName!=null) {
            arrayList.add(addName);
            listViewAdapter.notifyDataSetChanged();
        }
    }

    public void deleteFromList(String delName) {
        if (delName != null) {
            int pos = arrayList.indexOf(delName);
            arrayList.remove(pos);
            listViewAdapter.notifyDataSetChanged();
        }
    }


}
