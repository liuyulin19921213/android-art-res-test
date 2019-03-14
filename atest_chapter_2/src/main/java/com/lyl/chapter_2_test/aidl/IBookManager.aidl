// IBookManager.aidl
package com.lyl.chapter_2_test.aidl;

// Declare any non-default types here with import statements
import com.lyl.chapter_2_test.aidl.Book;
import com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener;

interface IBookManager {
   
       //远程服务器获取图书列表
      List<Book> getBookList();
      //用于往图书列表中添加一本书
      void addBook(in Book book);

      void registerLisenter(IOnNewBookArrivedListener listener);
      void unregisterListener(IOnNewBookArrivedListener listener);
}
