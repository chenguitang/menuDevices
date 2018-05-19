package com.posin.menudevices.socket;

import android.util.Log;


import com.posin.menudevices.ICallback;
import com.posin.menudevices.global.AppConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static android.R.id.message;

/**
 * created by Greetty at 2018/4/27 10:35
 * <p>
 * SOCKET连接管理
 */
public class SocketConnection implements Runnable {

    private static final String TAG = "SocketConnection";
    /**
     * 心跳周期 (单位：毫秒）
     */
    private volatile static long activeCycle = 2500;
    private Socket socket = null;
    private ICallback mCallback;
    private static long lastActTime = 0;


    public SocketConnection(ICallback callback) {
        this.mCallback = callback;
    }


    @Override
    public void run() {
        try {
            conn();
            if (socket != null && !socket.isClosed()) {
                ConnManager.getConnManager().setSocket(socket);
                new Thread(heartBeatRunnable).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void conn() {
        try {
            socket = new Socket(AppConfig.TARGET_DISPLAY_ADDRESS, AppConfig.TARGET_DISPLAY_PORT);
            Log.e(TAG, "conn: 连接socket成功");
            mCallback.success();

        } catch (Exception e) {
            try {
                Log.e(TAG, "conn: socket连接异常！" + e.getMessage());
                mCallback.failure();
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private Runnable heartBeatRunnable = new Runnable() {

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                long time = System.currentTimeMillis();
//                if (getLastActTime() + activeCycle < time) {
                if (time - getLastActTime() >= activeCycle) {
                    Log.d(TAG, "发送心跳包。。。");
                    //发送心跳包
                    boolean isSuccess = sendHeartBeat("ok");
                    if (!isSuccess) {
                        //心跳包失败重连socket服务
                        releaseLastSocket(socket);
                        socket = null;
                        ConnManager.getConnManager().setSocket(socket);
                        running = false;
                    }

                }
            }
        }
    };

    /**
     * 向socket服务端发送心跳包
     *
     * @param msg 发送心跳信息
     * @return boolean
     */
    private boolean sendHeartBeat(String msg) {
        if (socket == null) {
            Log.d(TAG, "sendHeartBeat: socket = null");
            return false;
        }
        try {
            if (!socket.isClosed() || !socket.isOutputShutdown()) {
                Log.d(TAG, "sendHeartBeat: 允许发送");
                OutputStream out = socket.getOutputStream();
                out.write(msg.getBytes());
                out.flush();
                lastActTime = System.currentTimeMillis();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    /**
     * 获取最后发送心跳包的时间
     *
     * @return
     */
    public long getLastActTime() {
        return lastActTime;
    }

    /**
     * 设置最后发送时间信息
     *
     * @param lastActTime long 发送时间
     */
    public void setLastActTime(long lastActTime) {
        this.lastActTime = lastActTime;
    }

}
