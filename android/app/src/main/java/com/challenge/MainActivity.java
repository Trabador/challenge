package com.challenge;

import android.app.RemoteInput;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "challenge";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ReactInstanceManager mReactInstanceManager = getReactNativeHost().getReactInstanceManager();
        //ReactContext context = (ReactContext) mReactInstanceManager.getCurrentReactContext();
        mReactInstanceManager.addReactInstanceEventListener(validContext -> {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){

                String message = bundle.getString("message");
                NotificationModule.sendReplyData(message, validContext);

            }
        });
    }
}
