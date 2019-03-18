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

##跨进程传输方式详解

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
Messenger 是串行的方式处理客户端发来的消息，如果有大量的并发请求不适合用，
Messenger 是用来传递消息，有时候可能需要跨进程调用服务端的方法，这时候就无法实现

使用AIDL


AIDL 支持的数据结构
。。。
List 只支持ArrayList
Map 只支持HashMap

不支持声明静态常量

调用另一个程序的service 另一个程序的 service 属性需要添加
android:exported="true"

AIDL 中能够使用List 只有ArrayList 但我们使用了CopyOnWriteArrayList
AIDL 中所支持的是抽象的List 而List只是一个接口，因此瑞然服务端返回的是CopyOnWriteArrayList
但是在Binder中会按照List 的规范去访问数据并最终形成一个新的ArrayList 传递给客户端，
类似的还有ConcurrentHashMap

RemoteCallbackList 系统专门提供用于删除跨进程listener 的接口
工作原理，它内部有一个Map结构专门用来保存所有的AIDL回调，
这个Map的key是Ibinder类型，value是Callback类型，
虽然跨进程传输都是新的对象，但是新对象的底层的binder对象是同一个，
所以只徐啊哟找到与注册listener 有相同的Binder对象的listener并删除就可以了
还有他当客户端进程终止后，自动移除客户端所注册的listener，
内部也实现了线程同步功能，不需要做线程同步工作

Binder 是可能意外死亡的，这往往是由于服务端进程意外停止了，这时我们需要重新链接服务。
第一种 是给 Binder 设置 DeathRecipient 监听，当 Binder 死亡，我们会收到 binderDied
方法的回调，在 binderDied 方法中我们可以重连远程服务，具体方法在Binder 那一节已经介绍过了，
另一种方法是在 onServiceDisconnected 中重连远程服务，
区别在于： onServiceDisconnected 在客户端的UI线程中被回调，而binderDied 在客户端Binder
线程池中被回调，

AIDL 中使用权限验证功能，常用的两种方法
第一种：在onBind中进行验证，验证不通过直接返回null，这样验证失败的客户端直接无法绑定服务，
至于验证方式可以有多种，比如使用permission 验证，我们要先在androidMenifest 中声明所需的权限
第二种：我们可以在服务端的onTransact 方法中进行权限验证，如果验证失败就直接返回false
这样服务器就不会终止执行AIDL 中的方法从而达到保护服务端的效果，至于具体的验证方式有很多，可以采用
permission验证，具体和第一种方法一样。
还可以采用UID 和Pid 来做验证，

## ContentProvider 
细节还是相当多，比如 CRUD 操作、防止 SQL 注入和权限控制等 
android:authorities 是 ContentProvide 的唯一标识 
android:permission 申请权限时候需要动态权限
onCreate 在主线程，不可做耗时操作
ContentProvider 根据URL 来区分外界要访问的数据集合，

## socket



