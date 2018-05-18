package com.posin.menudevices.services;

import android.os.RemoteException;
import android.util.Log;

import com.posin.menudevices.command.MenuCommandManage;
import com.posin.menudevices.constant.Dishes;
import com.posin.menudevices.ICallback;
import com.posin.menudevices.IMenuManage;

import java.util.List;

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
    public void init(int maxShowItem, boolean isChinese, ICallback callback) throws RemoteException {
        Log.d(TAG, "maxShowItem: " + maxShowItem);
        Log.d(TAG, "isChinese: " + isChinese);
        if (callback==null){
            Log.d(TAG, "callback ==null ");
        }else{
            Log.d(TAG, "callback !=null ");
        }
        try {
            MenuCommandManage.getInstance().init(maxShowItem,isChinese,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMenu(List<Dishes> listDishes, double sum) throws RemoteException {

        Log.d(TAG, "sendMenu sum message： " + sum);
        try {
            MenuCommandManage.getInstance().sendMenu(listDishes,sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pay(double sum, double discountSum, double alreadyPay,
                    double giveChange) throws RemoteException {

        Log.d(TAG, "sum: " + sum + " discountSum: " + discountSum +
                " alreadyPay: " + alreadyPay     + " giveChange: "+giveChange);

        try {
            MenuCommandManage.getInstance().pay(discountSum,sum,alreadyPay,giveChange);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearMenu() throws RemoteException {
        Log.d(TAG, "清空菜单栏数据 ...");

        try {
            MenuCommandManage.getInstance().clearDishes();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
