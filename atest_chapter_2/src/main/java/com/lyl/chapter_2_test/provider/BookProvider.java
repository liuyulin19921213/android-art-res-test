package com.lyl.chapter_2_test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URI;

/**
 * Created by lyl on 19-3-18
 */
public class BookProvider extends ContentProvider {

    private final String TAG = BookProvider.class.getSimpleName();

    private  static  final  String AUTHORITY ="com.lyl.chapter_2_test.provider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"
    + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriURI_MATCHER.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriURI_MATCHER.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private String getTableName (Uri uri){
        String tableName =null;
        switch (sUriURI_MATCHER.match(uri)){
            case BOOK_URI_CODE:{
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            }
            case USER_URI_CODE:{
                tableName = DbOpenHelper.USER_TALBE_NAME;
                break;
            }
            default:break;
        }
        return tableName;
    }
    private DbOpenHelper dbOpenHelper ;
    private Context mContext;
    // 创建 回调在主线程，其余均在binder 线程池中
    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreat,current thread:"
                + Thread.currentThread().getName());
        mContext = getContext();
        dbOpenHelper = new DbOpenHelper(mContext);
        //不推荐在主线程中进行耗时的数据库操作
        initProviderData();
        return true;
    }

    private void initProviderData() {

        SQLiteDatabase mDb = dbOpenHelper.getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TALBE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'Ios');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");
    }

    @Nullable
    @Override
    public Cursor query( Uri uri,  String[] projection,
                         @Nullable String selection,
                         @Nullable String[] selectionArgs,
                         @Nullable String sortOrder) {

        Log.d(TAG,"query,current thread:"
                + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table ==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        return dbOpenHelper.getReadableDatabase().query(
                table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder,
                null
        );
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
        String table = getTableName(uri);
        if (table ==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }

        dbOpenHelper.getWritableDatabase().insert(table,
                null,
                values);
        mContext.getContentResolver().notifyChange(uri,null);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG,"delete,current thread:"
                + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table ==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int count = dbOpenHelper.getWritableDatabase().delete(table,
                selection,
                selectionArgs);
        if (count >0){
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG,"update,current thread:"
                + Thread.currentThread().getName());

        String table = getTableName(uri);
        if (table ==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int count = dbOpenHelper.getWritableDatabase().update(table,
                values,
                selection,
                selectionArgs);
        if (count >0){
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return count;
    }
}
