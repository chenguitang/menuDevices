package com.posin.menudevices.services;

import android.os.RemoteException;
import android.util.Log;

import com.posin.menudevices.IMenuManage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public String setListMenuList(List<String> listNames) throws RemoteException {
        Log.e(TAG, "setListMenuList ...............");


        for (String name : listNames) {
            Log.e(TAG, "setListMenuList name: " + name);

        }
        return "Greetty182612";
    }

    @Override
    public void setMenuMap(Map menuMaps) throws RemoteException {



        for (Object o : menuMaps.keySet()) {
            Log.e(TAG, "map key name: " + o);
            Log.e(TAG, "map values: " + menuMaps.get(o));
        }


    }

}
