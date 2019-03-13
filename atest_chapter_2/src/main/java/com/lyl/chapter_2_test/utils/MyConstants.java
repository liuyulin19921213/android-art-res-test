package com.lyl.chapter_2_test.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by lyl on 19-3-13
 */
public class MyConstants {

    private String TAG = MyConstants.class.getSimpleName();


    public static final String CHAPTER_2_PATH = Environment
            .getExternalStorageDirectory().getPath()
            +"/liuyulin/chapter_2_test/";

    public static final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;
}
