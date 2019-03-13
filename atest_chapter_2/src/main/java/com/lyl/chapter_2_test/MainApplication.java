package com.lyl.chapter_2_test;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.lyl.chapter_2_test.utils.MyUtils;

public class MainApplication extends Application {

    private String TAG = MainApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, String.valueOf(System.currentTimeMillis()));
        String processName = MyUtils.getProcessName(getApplicationContext(),
                Process.myPid());
        Log.d(TAG,"application start,process name :" +processName);
    }
}
