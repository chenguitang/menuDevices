// IMenuManage.aidl
package com.posin.menudevices;

// Declare any non-default types here with import statements

interface IMenuManage {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     void setMenu(String name);

//      void setListMenuList(List<String> list);
}
