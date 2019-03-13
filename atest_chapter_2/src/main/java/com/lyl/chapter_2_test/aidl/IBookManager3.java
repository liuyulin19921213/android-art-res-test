package com.lyl.chapter_2_test.aidl;

import android.os.IInterface;

/**
 * Created by lyl on 19-3-12
 */
public interface  IBookManager3 extends IInterface {


    //Binder 的唯一标识，一般用当前Binder的类类名表示
    static final String DESCRIPTOR = "com.lyl.chapter_2_test.aidl.IBookManager3";


    /**
     * 声明两个整型的id分别用于标识这两个方法，这两个id用于标识在transact过程中
     * 客户端所请求的到底是哪个方法
     */
    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);


    //声明了两个方法，显然是我们在IBookManager.aidl 中所声明的方法
    public java.util.List<Book> getBookList() throws android.os.RemoteException;
    public void addBook(Book book) throws android.os.RemoteException;
}
