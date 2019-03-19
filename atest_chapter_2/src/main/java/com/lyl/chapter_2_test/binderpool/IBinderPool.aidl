package com.lyl.chapter_2_test.binderpool;

import android.os.IBinder;

interface IBinderPool {

    IBinder  queryBinder(int binderCode);
}