// Book.aidl
package com.lyl.chapter_2_test.aidl;

// Declare any non-default types here with import statements

import com.lyl.chapter_2_test.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived (in Book newBook);
}
