package com.lyl.chapter_2_test.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lyl.chapter_2_test.R;
import com.lyl.chapter_2_test.utils.MyConstants;

/**
 * Created by lyl on 19-3-13
 */
public class MessengerActivity extends AppCompatActivity {
    private static String TAG = MessengerActivity.class.getSimpleName() + "_lyl";

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello ,this is client.");
            msg.setData(data);
            //设置客户端接受服务端消息的Messenger
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MyConstants.MSG_FROM_SERVICE:{
                    Log.i(TAG,"receive msg from Service:"+msg.getData().getString("reply"));
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
//        Intent intent = new Intent(this,MessengerService.class);
        //调用另一个程序中的 Service
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.lyl.atest_chapter_2_2",
                "com.lyl.atest_chapter_2_2.messenger.MessengerService"));
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
