package com.lyl.chapter_2_test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lyl on 19-3-18
 */
public class BookProvider extends ContentProvider {

    private final String TAG = BookProvider.class.getSimpleName();

    // 创建 回调在主线程，其余均在binder 线程池中
    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreat,current thread:"
                + Thread.currentThread().getName());
        return false;
    }
    @Nullable
    @Override
    public Cursor query( Uri uri,  String[] projection,
                         @Nullable String selection,
                         @Nullable String[] selectionArgs,
                         @Nullable String sortOrder) {

        Log.d(TAG,"query,current thread:"
                + Thread.currentThread().getName());
        return null;
    }

    // 用来返回一个Uri 请求所对应的 MIME 类型（媒体类型），比如照片、视频
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG,"getType,current thread:"
                + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
        Log.d(TAG,"insert,current thread:"
                + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG,"delete,current thread:"
                + Thread.currentThread().getName());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG,"update,current thread:"
                + Thread.currentThread().getName());
        return 0;
    }
}
