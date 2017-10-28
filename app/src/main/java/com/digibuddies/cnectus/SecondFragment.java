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
    String newItem="";
    String newItem2="";
    int myNum=0;
    int myNum2=0;

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

//addition of elements
            if(getArguments()!=null) {
                Bundle bundle = getArguments();
                newItem = bundle.getString("key");
                try {
                    myNum = Integer.parseInt(newItem);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getActivity(), nfe.toString(), Toast.LENGTH_LONG).show();

                }

                if (myNum == 1) {
                    creatingNewListView("Geeks");
                } else if (myNum == 3) {
                    creatingNewListView("pros");
                } else if (myNum == 4) {
                    creatingNewListView("daydreamers");
                } else if (myNum == 5) {
                    creatingNewListView("adventurers");
                } else {
                    creatingNewListView("Artists");
                }
            }


            //deletion of elements
        if(getArguments()!=null) {
            Bundle bundle = getArguments();
            newItem2 = bundle.getString("delete");
            try {
                myNum2 = Integer.parseInt(newItem2);
            } catch (NumberFormatException nfe) {
                Toast.makeText(getActivity(), nfe.toString(), Toast.LENGTH_LONG).show();

            }

            if (myNum2 == 1) {
                deleteFromList("Geeks");
            } else if (myNum2 == 3) {
                deleteFromList("pros");
            } else if (myNum2 == 4) {
                deleteFromList("daydreamers");
            } else if (myNum2 == 5) {
                deleteFromList("adventurers");
            } else {
                deleteFromList("Artists");
            }
        }


            return view;


    }

    public void creatingNewListView(String addName) {
        arrayList.add(addName);
            listViewAdapter.notifyDataSetChanged();

    }

    public void deleteFromList(String delName)
    {
        int pos=arrayList.indexOf(delName);
        arrayList.remove(pos);
    }


}
