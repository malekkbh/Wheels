package com.malekk.newdriver;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by malekkbh on 21/12/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIDService" ;

    public MyFirebaseInstanceIDService() {
    }
    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
// Get updated InstanceID token.
        String refreshedFirebaseToken = FirebaseInstanceId.getInstance().getToken();
        Log.d( TAG, "new Token: " + refreshedFirebaseToken);

        SharedPreferences prefs = getSharedPreferences("pushToken", MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();

        e.putString("token", refreshedFirebaseToken);
        e.commit();

    }



}
