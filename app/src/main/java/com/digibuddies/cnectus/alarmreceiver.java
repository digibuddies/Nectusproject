package com.digibuddies.cnectus;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vikram singh on 05-03-2017.
 */

public class alarmreceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, thebackservice.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
