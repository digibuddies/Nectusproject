package com.digibuddies.nectus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class Feed extends AppCompatActivity{
    public static final String TAG="Feed";
    public SmileRating smileRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        smileRating = (SmileRating) findViewById(R.id.smile_rating);






    }
}