package com.posin.menudevices.command;


import android.util.Log;

import com.posin.menudevices.constant.Dishes;
import com.posin.menudevices.utils.ArrayUtils;
import com.posin.menudevices.utils.StringUtils;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Greetty on 2018/5/8.
 * <p>
 * 控制副屏默认布局指令
 */
public class MenuCommand {

    private static final String TAG = "MenuCommand";


    /**
     * 生成菜单菜品指令
     *
     * @param menuList  菜品集合
     * @param sum       总金额
     * @param maxShow   最大显示数量
     * @param isChinese 是否为中文
     * @return 指令集合
     * @throws Exception 异常
     */
    public static String[][] addMenuCommandCode(List<Dishes> menuList,
                                                double sum, int maxShow, boolean isChinese)
            throws Exception {
        String[][] commandMenus = new String[][]{{}};
        if (menuList.isEmpty()) {
            throw new Exception("menu is null ,please Incoming menu ... ");
        }
        ListIterator<Dishes> iMenuList = menuList.listIterator(menuList.size());
        for (int i = 0; i < menuList.size(); i++) {
            if (i >= maxShow) {
                break;
            }
            Dishes dishes = iMenuList.previous();
            String dishName = dishes.getDishName();
            String[][] addMenuItemCommand = new String[][]{
                    {
                            "action", "add",
                            "parent", "layout_1_2",
                            "name", "layout_" + dishName + i,
                            "type", "linear_layout",
                            "orientation", "horizontal",
                            "width", "match_parent",
                            "height", "wrap_content",
                    },
                    {
                            "action", "add",
                            "parent", "layout_" + dishName + i,
                            "name", dishName + i,
                            "type", "text",
                            "gravity", "left",
                            "width", "0",
                            "weight", "1",
                            "height", "wrap_content",
                            "text", dishName,
                            "size", "16",
                            "color", "0xFFFFFFFF",
                    },
                    {
                            "action", "add",
                            "parent", "layout_" + dishName + i,
                            "name", "middle_" + dishName + i,
                            "type", "text",
                            "gravity", "center",
                            "width", "0",
                            "weight", "1",
                            "height", "wrap_content",
                            "text", StringUtils.append(dishes.getAmount(), "*",
                            dishes.getPrices(), isChinese ? "￥" : "$"),
                            "size", "16",
                            "color", "0xFFFFFFFF",
                    },
                    {
                            "action", "add",
                            "parent", "layout_" + dishName + i,
                            "name", "price_" + dishName + i,
                            "type", "text",
                            "gravity", "right",
                            "width", "0",
                            "weight", "1",
                            "height", "wrap_content",
                            "text", StringUtils.decimalFormat(dishes.getSubtotal(), 2) +
                            (isChinese ? "元" : "$"),
                            "size", "16",
                            "color", "0xFFFFFFFF",
                    },
                    {
                            "action", "set",
                            "name", "text_cum",
                            "text", String.valueOf(sum) + (isChinese ? "元" : "$"),
                    },
            };
            commandMenus = ArrayUtils.mergeTwoDimArrayVer(commandMenus, addMenuItemCommand);
        }

        return commandMenus;
    }

    /**
     * 支付
     *
     * @param discountPrice 优惠金额
     * @param sum           总计金额
     * @param alreadyPay    已付金额
     * @param giveChange    找零金额
     * @param isChinese     是否为中文
     * @return 指令集合
     * @throws Exception
     */
    public static String[][] pay(double discountPrice, double sum, double alreadyPay,
                                 double giveChange, boolean isChinese) throws Exception {
        String[][] payCommand = null;
        if (isChinese) {
            payCommand = new String[][]{
                    {
                            "action", "set",
                            "name", "text_pre",
                            "text", String.valueOf(discountPrice) + "元",
                    },
                    {
                            "action", "set",
                            "name", "text_cum",
                            "text", String.valueOf(sum) + "元",
                    },
                    {
                            "action", "set",
                            "name", "text_paid",
                            "text", String.valueOf(alreadyPay) + "元",
                    },
                    {
                            "action", "set",
                            "name", "text_odd",
                            "text", String.valueOf(giveChange) + "元",
                    },

            };
        } else {
            payCommand = new String[][]{
                    {
                            "action", "set",
                            "name", "text_pre",
                            "text", String.valueOf(discountPrice) + "$",
                    },
                    {
                            "action", "set",
                            "name", "text_cum",
                            "text", String.valueOf(sum) + "$",
                    },
                    {
                            "action", "set",
                            "name", "text_paid",
                            "text", String.valueOf(alreadyPay) + "$",
                    },
                    {
                            "action", "set",
                            "name", "text_odd",
                            "text", String.valueOf(giveChange) + "$",
                    },

            };
        }
        return payCommand;
    }


}
