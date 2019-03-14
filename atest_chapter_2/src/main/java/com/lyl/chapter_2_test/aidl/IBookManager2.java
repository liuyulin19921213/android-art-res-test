/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /media/lyl/liuyulin/LYL/DaiMa/AndroidStudio/android-art-res-master/atest_chapter_2/src/main/java/com/lyl/chapter_2_test/aidl/IBookManager.aidl
 */
package com.lyl.chapter_2_test.aidl;

import android.os.RemoteException;

public interface IBookManager2 extends android.os.IInterface
{

    /**
     * 内部类Stub，就是一个Binder类，当客户端和服务端都位于同一个进程时，
     * 方法调用不会走跨进程的transact过程，
     * 而当两者不同进程时，方法调用需要走transact过程，这个逻辑由Stub的内部代理类Proxy来完成
     */
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements IBookManager
    {
        //Binder 的唯一标识，一般用当前Binder的类类名表示
        private static final String DESCRIPTOR = "com.lyl.chapter_2_test.aidl.IBookManager";
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an com.lyl.chapter_2_test.aidl.IBookManager interface,
         * generating a proxy if needed.
         */
        /**
         * 用于将服务端的Binder 对象转换成客户端所需的AIDL接口类型的对象，这种转换过程是区分的，
         * 如果客户端和服务端位于同一进程，那么此方法返回的就是服务端的Stub对象本身，
         * 否则返回的是系统封装后的Stub.proxy对象
         */
        public static IBookManager asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof IBookManager))) {
                return ((IBookManager)iin);
            }
            return new Proxy(obj);
        }
        //此方法用于返回当前Binder 对象
        @Override public android.os.IBinder asBinder()
        {
            return this;
        }

        /**
         *  这个方法运行在服务端中Binder线程池中，当客户端发起跨进程请求时，
         *  远程请求会通过系统底层封装后交由此方法来处理，
         * @param code 可以确定客户端所请求的目标方法
         * @param data  目标方法所需要的参数
         * @param reply 执行完成，写入返回值
         * @param flags
         * @return 如果 返回 false那么客户端的请求会失败，权限验证，毕竟不希望随便一个进程都能远程调用我们的服务
         * @throws android.os.RemoteException
         */
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            String descriptor = DESCRIPTOR;
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getBookList:
                {
                    data.enforceInterface(descriptor);
                    java.util.List<Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook:
                {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    if ((0!=data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_registerLisenter:
                {
                    data.enforceInterface(descriptor);
                    com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener _arg0;
                    _arg0 = com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener.Stub.asInterface(data.readStrongBinder());
                    this.registerLisenter(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_unregisterListener:
                {
                    data.enforceInterface(descriptor);
                    com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener _arg0;
                    _arg0 = com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener.Stub.asInterface(data.readStrongBinder());
                    this.unregisterListener(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default:
                {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }
        private static class Proxy implements IBookManager
        {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }

            @Override public android.os.IBinder asBinder()
            {
                return mRemote;
            }

            public String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }

            /**
             *  这个方法运行在客户端，当客户端远程调用此方法时，它的内部是这样的：
             *  搜先创建该方法所需要输入型Parcel对象_data、输出型Parcel对象_reply和返回值List
             *  然后把该方法的参数信息写入_data中，接着调用transact方法来发起RPC（远程过程调用）请求
             *  同时当前线程挂起;然后服务端的onTransact 方法会被调用，知道RPC过程返回后，当前线程继续执行，并
             *  从_reply中取出RPC过程的返回结果;最后返回_reply中的数据。
             */
            @Override public java.util.List<Book> getBookList() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Book.CREATOR);
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            /**
             * 运行在客户端，
             */
            @Override public void addBook(Book book) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book!=null)) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void registerLisenter(IOnNewBookArrivedListener listener) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
                    mRemote.transact(Stub.TRANSACTION_registerLisenter, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
                    mRemote.transact(Stub.TRANSACTION_unregisterListener, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        /**
         * 声明两个整型的id分别用于标识这两个方法，这两个id用于标识在transact过程中
         * 客户端所请求的到底是哪个方法
         */
        static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_registerLisenter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_unregisterListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    }


    //声明了两个方法，显然是我们在IBookManager.aidl 中所声明的方法
    public java.util.List<Book> getBookList() throws android.os.RemoteException;
    public void addBook(Book book) throws android.os.RemoteException;
    public void registerLisenter(com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener listener) throws android.os.RemoteException;
    public void unregisterListener(com.lyl.chapter_2_test.aidl.IOnNewBookArrivedListener listener) throws android.os.RemoteException;

}
