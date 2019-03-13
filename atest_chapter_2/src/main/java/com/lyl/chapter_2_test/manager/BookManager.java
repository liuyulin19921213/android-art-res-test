package com.lyl.chapter_2_test.manager;

import android.os.IBinder;

import com.lyl.chapter_2_test.aidl.IBookManager;

/**
 * Created by lyl on 19-3-12
 */
public class BookManager {

    private String TAG = BookManager.class.getSimpleName();

    private IBookManager mIBookManager;

    /**
     * unlinkToDeath 移除绑定的服务，
     * linkToDeath 给服务设置死亡代理，服务死掉会收到通知
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {

            if (mIBookManager == null){
                return;
            }
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mIBookManager = null;
            //这里需要重新绑定远程Service
        }
    };

}
