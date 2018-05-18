package com.posin.menudevices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.posin.menudevices.IMenuManage;
import com.posin.menudevices.socket.ConnManager;

public class MenuService extends Service {

    private static final String TAG = "MenuService";

    public MenuService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "onBind start connect adv  ... ");
//        ConnManager.getConnManager().connectServer();


        return new MenuServiceImpl();
    }


}
