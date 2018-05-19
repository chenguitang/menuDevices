package com.posin.menudevices.command;


import com.posin.menudevices.ICallback;
import com.posin.menudevices.constant.Dishes;
import com.posin.menudevices.socket.ConnManager;
import com.posin.menudevices.utils.ArrayUtils;
import com.posin.menudevices.utils.ThreadManage;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Greetty on 2018/5/2.
 * <p>
 * 默认UI布局
 */
public class MenuCommandManage {

    private static final String TAG = "MenuCommandManage";
    private static MenuCommandManage mPatternDefaultUIManager = null;
    private static int SHOW_MAX_DISHES = 10;   //默认菜单栏只显示5个菜品
    private static boolean IS_CHINESE = true;  //是否为中文
    private long endSendTime;

    public static synchronized MenuCommandManage getInstance() {
        if (mPatternDefaultUIManager == null) {
            mPatternDefaultUIManager = new MenuCommandManage();
        }
        return mPatternDefaultUIManager;
    }

    private MenuCommandManage() {
    }


    /**
     * 初始化默认布局
     *
     * @param showMaxDish 显示的最大菜品数
     * @param isChinese   是否为中文
     * @throws Exception 异常
     */
    public void init(int showMaxDish, boolean isChinese, ICallback callback) throws Exception {
        SHOW_MAX_DISHES = showMaxDish;
        IS_CHINESE = isChinese;
        ConnManager.getConnManager().connectServer(callback);
//        ThreadManage.getSinglePool().execute(new HandlerMessageThread());

    }


    /**
     * 清除所有菜品，及统计等，恢复到初始化状态
     *
     * @throws Exception 异常
     */
    public void clearDishes() throws Exception {

        ConnManager.getConnManager().sendViewCode(
                MenuConfig.getLayoutCommand(MenuConfig.LAYOUT_DEFAULT,IS_CHINESE));
    }


    /**
     * @param menuMaps LinkedHashMap<String, Dishes> 菜品集合
     * @param sum      总计金额
     * @throws Exception 异常
     */
    public void sendMenu(List<Dishes> menuMaps, double sum) throws Exception {
        String[][] defaultUiCommand = MenuConfig.getLayoutCommand(MenuConfig.LAYOUT_DEFAULT,IS_CHINESE);

        String[][] menuCommand = MenuCommand.addMenuCommandCode(menuMaps, sum, SHOW_MAX_DISHES, IS_CHINESE);

        String[][] addMenuCommand = ArrayUtils.mergeTwoDimArrayVer(defaultUiCommand, menuCommand);

        ConnManager.getConnManager().sendViewCode(addMenuCommand);
        Thread.sleep(10);
//        endSendTime = System.currentTimeMillis();
    }



    /**
     * 支付
     *
     * @param discountPrice 优惠金额
     * @param sum           总计金额
     * @param alreadyPay    已付金额
     * @param giveChange    找零金额
     * @throws Exception
     */
    public void pay(double discountPrice, double sum, double alreadyPay,
                    double giveChange) throws Exception {

        String[][] payCommand = MenuCommand.pay(discountPrice, sum, alreadyPay,
                giveChange, IS_CHINESE);

        ConnManager.getConnManager().sendViewCode(payCommand);

    }


}
