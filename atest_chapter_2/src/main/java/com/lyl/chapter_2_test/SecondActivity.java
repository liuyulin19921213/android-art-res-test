package com.lyl.chapter_2_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lyl.chapter_2_test.model.User;
import com.lyl.chapter_2_test.utils.MyConstants;
import com.lyl.chapter_2_test.utils.MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SecondActivity  extends AppCompatActivity {

    private String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {
        super.onResume();
        recoverFromFile();
//        startActivity(new Intent(this,ThirdActivity.class));

    }


    private void recoverFromFile(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = null;
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                if (cacheFile.exists()){
                    ObjectInputStream objectInputStream =null;

                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cacheFile));
                        user = (User) objectInputStream.readObject();
                        Log.d(TAG,"recover user:"+ user.toString());
                    } catch (IOException e) {
                        Log.e(TAG,e.toString());

                        e.printStackTrace();

                    } catch (ClassNotFoundException e) {
                        Log.e(TAG,e.toString());

                        e.printStackTrace();
                    }finally {
                        MyUtils.close(objectInputStream);
                        Log.d(TAG,"完成");

                    }
                }
            }
        }).start();
    }
}
