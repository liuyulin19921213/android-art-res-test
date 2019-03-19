package com.lyl.chapter_2_test.binderpool;

import android.os.RemoteException;

/**
 * Created by lyl on 19-3-19
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private final String TAG = SecurityCenterImpl.class.getSimpleName();

    private static final char SECRET_CODE ='^';


    @Override
    public String encrypt(String content) throws RemoteException {
       char[] chars = content.toCharArray();
       for (int i =0;i<chars.length;i++){
           chars[i] ^= SECRET_CODE;
       }

        return new String(chars);

    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
