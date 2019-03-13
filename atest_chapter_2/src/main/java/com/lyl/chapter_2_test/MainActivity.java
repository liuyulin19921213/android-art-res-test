package com.lyl.chapter_2_test;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lyl.chapter_2_test.model.User;
import com.lyl.chapter_2_test.utils.MyConstants;
import com.lyl.chapter_2_test.utils.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     }

    /**
     * 在MainActivity的onResume中序列化一个User对象到sd卡中，
     * 然后在SecondActivity的onResume中去反序列化，
     */
    @Override
    protected void onResume() {
        super.onResume();

        persistToFile();

        startActivity(new Intent(this,SecondActivity.class));

    }

    private void persistToFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = new User(1,"hello world",false);
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;

                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    MyUtils.close(objectOutputStream);
                    Log.d(TAG,"完成");

                }
            }
        }).start();
    }
}
