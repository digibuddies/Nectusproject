package com.digibuddies.cnectus;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainl, top.newInstance(top.NODIR));
        ft.commit();

        SecondFragment secondFragment=new SecondFragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondfrag,secondFragment,secondFragment.getTag())
                .commit();


    }

}
