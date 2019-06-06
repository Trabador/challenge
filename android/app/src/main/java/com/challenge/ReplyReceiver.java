package com.challenge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


public class ReplyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        /*Listens for reply and gets response from notification*/
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        String replyText = "";


        if (remoteInput != null) {
            replyText = remoteInput.getCharSequence("text_reply").toString();
            Intent newIntent = new Intent(context, MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.putExtra("message", replyText);
            context.startActivity(newIntent);


        }

        /*Close the actual notification*/
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);

    }
}