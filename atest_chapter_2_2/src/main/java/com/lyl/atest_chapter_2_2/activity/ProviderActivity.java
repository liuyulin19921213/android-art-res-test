package com.lyl.atest_chapter_2_2.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lyl.atest_chapter_2_2.R;
import com.lyl.atest_chapter_2_2.aidl.Book;
import com.lyl.atest_chapter_2_2.model.User;

/**
 * Created by lyl on 19-3-18
 */
public class ProviderActivity extends Activity {

    private final String TAG = ProviderActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

       /*
       Uri uri = Uri.parse("content://com.lyl.chapter_2_test.provider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
*/
        Uri uri = Uri.parse("content://com.lyl.chapter_2_test.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计的艺术");
        getContentResolver().insert(uri,values);

        Cursor bookCursor = getContentResolver().query(uri,
                new String[] {"_id","name"},
                null,
                null,
                null);

        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG,"query book:"+ book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.lyl.chapter_2_test.provider/user");

        Cursor userCursor = getContentResolver().query(userUri,
                new String[] {"_id","name","sex"},
                null,
                null,
                null);

        while (userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) ==1;
            Log.d(TAG,"query user:"+ user.toString());
        }
        userCursor.close();



    }
}
