package com.lyl.chapter_2_test.provider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by lyl on 19-3-18
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private final String TAG = DbOpenHelper.class.getSimpleName();

    private static final String DB_NAME ="book_provide.db";
    public static  final  String BOOK_TABLE_NAME = "book";
    public static final String USER_TALBE_NAME = "user";

    private static final int DB_VERSION = 1;

    private  String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TALBE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT,"+
            "sex INT)";

    public DbOpenHelper(Context context) {

        super(context,DB_NAME ,null,DB_VERSION);

    }

    public DbOpenHelper(@Nullable Context context,
                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version) {

        super(context, name, factory, version);
    }

    public DbOpenHelper(@Nullable Context context,
                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version,
                        @Nullable DatabaseErrorHandler errorHandler) {

        super(context, name, factory, version, errorHandler);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
