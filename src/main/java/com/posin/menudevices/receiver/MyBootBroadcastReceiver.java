package com.posin.menudevices.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Greetty on 2018/5/18.
 * <p>
 * 开机广播
 */
public class MyBootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBootBroadcastReceiver";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT)) { //开机启动完成后，要做的事情
            Log.i(TAG, "MyBootBroadcastReceiver onReceive(), Do thing!");
        }
    }
}
