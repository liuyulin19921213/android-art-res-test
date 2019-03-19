package com.lyl.chapter_2_test.binderpool;

import android.os.RemoteException;

/**
 * Created by lyl on 19-3-19
 */
public class ComputeImpl extends ICompute.Stub {
    private final String TAG = ComputeImpl.class.getSimpleName();

    @Override
    public int add(int a, int b) throws RemoteException {

        return a+b;
    }
}
