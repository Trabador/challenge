package com.challenge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;


public class ReplyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Listens for reply and gets response from notification*/
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        String replyText = "";
        if (remoteInput != null) {
            replyText = remoteInput.getCharSequence("text_reply").toString();
        }

        NotificationModule.sendReplyData(replyText);
        /*Close the actual notification*/
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }
}