package com.lyl.chapter_2_test.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lyl.chapter_2_test.R;

import java.util.List;

/**
 * Created by lyl on 19-3-14
 */
public class BookManagerActivity extends AppCompatActivity {

    private String TAG = BookManagerActivity.class.getSimpleName() + "_lyl";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemotoBookManager;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:{
                    Log.d(TAG,"receive new book:"+msg.obj);
                    break;
                }
                default:
                    super.handleMessage(msg);
            }

        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);

            try {
                List<Book> list = bookManager.getBookList();
                //虽然我们用了 CopyOnWriteArrayList 但是接收的还是 type:java.util.ArrayList
                Log.i(TAG,"query book list,list type:" + list.getClass().getCanonicalName());
                Log.i(TAG,"query book list:" + list.toString());

                Book book = new Book(3,"Android 开发艺术探索");
                bookManager.addBook(book);
                Log.i(TAG,"add book:" + book);
                List<Book> newlist = bookManager.getBookList();
                Log.i(TAG,"query book list " + newlist.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
