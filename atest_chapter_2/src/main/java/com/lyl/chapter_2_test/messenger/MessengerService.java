package com.lyl.chapter_2_test.messenger;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.lyl.chapter_2_test.utils.MyConstants;

/**
 * Created by lyl on 19-3-13
 */
public class MessengerService extends Service {
    private static String TAG = MessengerService.class.getSimpleName() + "_lyl";


    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MyConstants.MSG_FROM_CLIENT:{
                    Log.i(TAG,"receive msg form Client:"
                            +msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message relplyMessage = Message.obtain(null,MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","恩，你的消息我收到了，稍后回复你");
                    relplyMessage.setData(bundle);

                    try {
                        client.send(relplyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
