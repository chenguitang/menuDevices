package com.posin.menudevices.services;

import android.os.RemoteException;
import android.util.Log;

import com.posin.menudevices.IMenuManage;

/**
 * Created by Greetty on 2018/5/17.
 * <p>
 * 服务实现类
 */
public class MenuServiceImpl extends IMenuManage.Stub {

    private static final String TAG = "MenuServiceImpl";

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                           double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void setMenu(String name) throws RemoteException {
        Log.e(TAG, "name: " + name);
    }

}
