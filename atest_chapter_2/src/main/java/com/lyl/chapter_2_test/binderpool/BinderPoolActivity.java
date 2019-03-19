package com.lyl.chapter_2_test.binderpool;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lyl.chapter_2_test.R;

/**
 * Created by lyl on 19-3-19
 */
public class BinderPoolActivity extends AppCompatActivity {
    private final String TAG = BinderPoolActivity.class.getSimpleName();

    SecurityCenterImpl mSecurityCenter = null;
    ComputeImpl mCompute =null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        new Thread(){
            @Override
            public void run() {
                doWork();

            }
        }.start();

    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = (SecurityCenterImpl) SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG,"VISIT ISecurityCenter");
        String msg = "helloworld-按卓";
        Log.d(TAG,"content:" +msg);

        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.d(TAG,"ENCRYPT: "+password);
            Log.d(TAG,"decrypt "+ mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"visit Icompute");
        IBinder computeBinder =binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = (ComputeImpl) ComputeImpl.asInterface(computeBinder);

        try {
            Log.d(TAG,"3+5=" +mCompute.add(3,5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
