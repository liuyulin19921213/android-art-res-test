adb shell 
sp | grep com.lyl

运行在不同进程中的四大组件，只要他们之间需要通过内存共享数据，都会共享失败。
多进程情况下有：
1：静态成员和单例模式完全失效
2：线程同步机制完全失效
3：SharedPreferences的可靠性下降
4：Application会多次创建

##Serializable
静态变量属于类不属于对象，所以不会参与序列化过程
其次用transient 关键字标记的成员变量不参与序列化过程

##Parcelable


Serializable 是java中的序列化接口，其使用开销很大，序列化和反序列化过程需要大量的I/O
操作，
Parcelable 是android 中的序列化方式，效率很高，Parcelable 主要用在内存
序列化上，
通过Parcelable 将对象序列化到存储设备中或者将对象 序列化后通过网络传输
也都是可以的，但过程稍显复杂，因此在这两种情况下建议大家使用Serializable

##Binder 
从IPC角度说Binder是android中的一种跨进 程通信方式;
Binder 还可以理解为一种虚拟的物理设备，设备驱动 /dev/binder,该通讯方式在Linux中没有;
从Android Framewordk 角度来说，Binder是ServiceManager链接各种Manager
（ActivityManager，WindowManager，等）和相应ManagerService 的桥梁;
从Android应用来说，Binder是客户端和服务端进行通信的媒介，
当bindService的时候，服务端会返回一个包含了服务端业务调用的Binder对象，通过这个Binder对象，
客户端可以获取服务端提供的服务或者数据，这里的服务包括普通服务和基于AIDL的服务

可以在Binder中传输的接口都需要继承IInterface

unlinkToDeath 移除绑定的服务，
linkToDeath 给服务设置死亡代理，服务死掉会收到通知 
通过Binder的方法isBinderAlive 也可以判断Binder是否死亡

##跨进程传输方式
通过Intent中附加extras来传递信息 
通过共享文件的方式来共享数据
Binder方式来跨进程通信
ContentProvider天生就是支持跨进程访问的，
通过网络通信也是可以实现数据传递的，Socket也可以实现IPC 

Intent 传递Bundle 这是最简单的进程间通信方式

曲线救国方案，A需要计算一个结果不支持用Bundle 之后传递给进程B并启动B中组件
换成：A启动B的一个server去计算之后Server传递给B的目标组件，只有启动是跨进程的。

使用文件共享 适合在对数据同步要求不高的进程之间进行通信

SharedPreferences 属于特殊的文件的一种，由于系统对它的读写有一定的缓存策略，
在内存中也会有一份SharedPreferences 文件的缓存，在多进程模式下，系统对它的读
写不可靠，在高并发读写访问，有很大几率丢失数据，不建议在进程间通信使用SharedPreferences

使用 Messenger 注意 Message 通过Messenger 可以在不同进程传递Message 对象，
在Message中放我们需要的对象，Messenger是一种轻量级的IPC方案，底层用AIDL
Messenger一次只处理一个请求，在服务端我们不用考虑线程同步的问题，服务端不存在并发的情形。

