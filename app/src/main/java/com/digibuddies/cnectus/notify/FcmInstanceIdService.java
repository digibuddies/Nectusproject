package com.digibuddies.cnectus.notify;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Vikram on 31-05-2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN",token);
        sendtoken(token);

    }
    void sendtoken(String token){
        SharedPreferences mpref;
        SharedPreferences.Editor editor;
        mpref = PreferenceManager.getDefaultSharedPreferences(this);
            editor = mpref.edit();
            editor.putString("cntoken", token);
            editor.apply();
    }

}
