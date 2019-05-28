package com.challenge;

import android.support.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.content.Intent;
import android.app.PendingIntent;

public class NotificationModule extends ReactContextBaseJavaModule {
    private static final String CHANNEL_ID = "channel_custom_notification";
    private static ReactContext mReactContext;
    private static final String TXT_REPLY = "text_reply";

    public NotificationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "NotificationCustom";
    }

    @ReactMethod
    public void notifyLocal(String title, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mReactContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(title).setContentText(message)
                .setAutoCancel(true);

        RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLY).setLabel("Reply now").build();

        Intent intent = new Intent(mReactContext, ReplyReceiver.class);

        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(mReactContext, 0, intent,
                0);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
                "Reply", replyPendingIntent).addRemoteInput(remoteInput).build();

        builder.addAction(action);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mReactContext);
        notificationManager.notify(1, builder.build());
    }

    /*gets the data from the receiver*/
    public static void sendReplyData(String replyText) {
        final WritableMap params = Arguments.createMap();
        params.putString("Reply", replyText);
        sendEvent(mReactContext, "Replied", params);
    }

    @ReactMethod
    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel custom",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = mReactContext.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    /*Sends event to javascript react*/
    private static void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}