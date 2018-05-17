package com.posin.menudevices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.posin.menudevices.IMenuManage;

public class MenuService extends Service {
    public MenuService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MenuServiceImpl();
    }


}
