package com.posin.menudevices.socket;

import android.util.Log;


import com.posin.menudevices.ICallback;
import com.posin.menudevices.global.AppConfig;

import java.io.IOException;
import java.net.Socket;

/**
 * created by Greetty at 2018/4/27 10:35
 * <p>
 * SOCKET连接管理
 */
public class SocketConnection implements Runnable {

    private static final String TAG = "SocketConnection";

    private Socket socket = null;
    private ICallback mCallback;

    public SocketConnection(ICallback callback) {
        this.mCallback = callback;
    }


    @Override
    public void run() {
        conn();
        if (socket != null && !socket.isClosed()) {
            ConnManager.getConnManager().setSocket(socket);
        }
    }

    private void conn() {
        try {
            socket = new Socket(AppConfig.TARGET_DISPLAY_ADDRESS, AppConfig.TARGET_DISPLAY_PORT);
            Log.e(TAG, "conn: 连接socket成功");
            mCallback.success();

        } catch (Exception e) {
            try {
                Log.e(TAG, "conn: socket连接异常！"+e.getMessage());
                mCallback.failure();
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     * <p>
     * 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
     *
     * @param socket Socket
     * @return Boolean
     */
    public static Boolean isServerClose(Socket socket) {
        boolean b = false;
        try {
            socket.sendUrgentData(0);
        } catch (Exception se) {
            b = true;
        }
        return b;
    }


    /**
     * 关闭客户端socket端
     *
     * @param socket Socket
     */
    private void releaseLastSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
