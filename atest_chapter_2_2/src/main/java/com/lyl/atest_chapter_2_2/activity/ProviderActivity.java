package com.lyl.atest_chapter_2_2.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.atest_chapter_2_2.R;

/**
 * Created by lyl on 19-3-18
 */
public class ProviderActivity extends Activity {

    private final String TAG = ProviderActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Uri uri = Uri.parse("content://com.lyl.chapter_2_test.provider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);

    }
}
