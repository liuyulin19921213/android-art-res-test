package com.lyl.chapter_2_test.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lyl.chapter_2_test.utils.MyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by lyl on 19-3-18
 */
public class TCPServerService extends Service {
    private final String TAG = TCPServerService.class.getSimpleName();

    private boolean mIsServiceDestoryed = false;

    private String[] mDefinedMessages = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket =null;
            try {
                serverSocket = new ServerSocket(8688);

            } catch (IOException e) {
                Log.d(TAG,"establish tcp server failed,port:8688");

                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestoryed){
                try {
                    final Socket client = serverSocket.accept();
                    Log.d(TAG,"accept");
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
        out.println("欢迎来到聊天室");

        while (!mIsServiceDestoryed){
            String str = in.readLine();
            Log.d(TAG,"msg from client :" + str);
            if (str ==null){
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            Log.d(TAG,"send:" + msg);
        }

        Log.d(TAG,"client queit.");
        MyUtils.close(out);
        MyUtils.close(in);
        client.close();
    }
}
