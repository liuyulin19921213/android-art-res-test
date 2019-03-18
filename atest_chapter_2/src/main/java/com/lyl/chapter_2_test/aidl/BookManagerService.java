package com.lyl.chapter_2_test.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lyl on 19-3-13
 */
public class BookManagerService extends Service {
    private String TAG = BookManagerService.class.getSimpleName() + "_lyl";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    //支持并发读写，AIDL 方法是在服务端的Binder线程池中执行，多个客户端链接的时候，会有多个线程同时访问的情形
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    //使用RemotoCallbackList 替换 CopyOnWriteArrayList
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenersList
            = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerLisenter(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(!mListenersList.contains(listener)){
//                mListenersList.add(listener);
//            }else {
//                Log.d(TAG,"already exists.");
//            }
//            Log.d(TAG,"registerListener,size"+mListenersList.size());

            mListenersList.register(listener);
            final int N = mListenersList.beginBroadcast();
            Log.d(TAG,"registerListener,size"+N);
            mListenersList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(mListenersList.contains(listener)){
//                mListenersList.remove(listener);
//                Log.d(TAG,"unregister listener succed.");
//            }else {
//                Log.d(TAG,"not found ,can not ubregister.");
//            }
//            Log.d(TAG,"unregisterListener,current size:"+mListenersList.size());

            mListenersList.unregister(listener);

            final int N = mListenersList.beginBroadcast();
            Log.d(TAG,"unregisterListener,current size:"+N);
            mListenersList.finishBroadcast();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        /**
         * 权限验证,验证androidManifest 是否有权限，
         * 验证包名是否是con.lyl 开头
         */
        int check = checkCallingOrSelfPermission(
                "com.lyl.chapter_2_test.permission.ACCESS.BOOK.SERVICE");
        if (check == PackageManager.PERMISSION_DENIED){
            return null;
        }
        String packageName = null;
        String[] packes = getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if(packes !=null && packes.length >0){
            packageName = packes[0];
        }
        if(!packageName.startsWith("com.lyl")){
            return null;
        }


        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }
    private void onNewBookArrived(Book book ) throws RemoteException {
        mBookList.add(book);
        final int N = mListenersList.beginBroadcast();
        Log.d(TAG,"onNewBookArrived,notify listeners:" +N);
        for (int i=0 ;i<N; i++){
            IOnNewBookArrivedListener listener = mListenersList.getBroadcastItem(i);
            Log.d(TAG,"onNewBookArrived,notify listener:"+ listener);
            if(listener !=null){
                listener.onNewBookArrived(book);
            }
        }
        mListenersList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (! mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() +1;
                Book newBook = new Book(bookId,"new book#" +bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
