package com.lyl.chapter_2_test.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lyl.chapter_2_test.R;
import com.lyl.chapter_2_test.utils.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lyl on 19-3-18
 */
public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = TCPClientActivity.class.getSimpleName();

    private static final int MESSAGE_RECEIVE_NEW_MSG =1;
    private static final int MESSAGE_SOCKET_CONNECTED =2;
    private static final int MESSAGE_SOCKET_SENDMESSAGE =3;

    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:{
                    mMessageTextView.setText(mMessageTextView.getText() +
                            (String)msg.obj);
                    break;
                }

                case MESSAGE_SOCKET_CONNECTED:{
                    mSendButton.setEnabled(true);
                    break;
                }

                case MESSAGE_SOCKET_SENDMESSAGE:{

//                    mPrintWriter.println(msg.obj);
                    mMessageEditText.setText("");
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showeMsg = "self" + time +":" +msg.obj +"\n";
                    mMessageTextView.setText(mMessageTextView.getText() + showeMsg);

                    break;
                }
                default:{
                    break;
                }
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = (TextView) findViewById(R.id.msg_container);
        mSendButton = (Button) findViewById(R.id.send);
        mSendButton.setOnClickListener(this);
        mMessageEditText = (EditText) findViewById(R.id.msg);

        Intent service = new Intent(this,TCPServerService.class);
        startService(service);
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onDestroy();

    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("localhost",8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                ),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG,"CONNECT SERVER SUCCESS");

            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.d(TAG,"connect tcp server failed,retry...");
//                e.printStackTrace();
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()
            ));
            while (! TCPClientActivity.this.isFinishing()){
                String msg =br.readLine();
                Log.d(TAG,"receive:"+msg);
                if (msg !=null){
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg = "server" + time +":" +msg +"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showedMsg).sendToTarget();
                }
            }
            Log.d(TAG,"quit...");
            MyUtils.close(mPrintWriter);
            MyUtils.close(br);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v == mSendButton){
            final String msg = mMessageEditText.getText().toString();
            if(!TextUtils.isEmpty(msg) && mPrintWriter !=null){
//                mPrintWriter.println(msg);
//                mMessageEditText.setText("");
//                String time = formatDateTime(System.currentTimeMillis());
//                final String showeMsg = "self" + time +":" +msg +"\n";
//                mMessageTextView.setText(mMessageTextView.getText() + showeMsg);
//                mHandler.obtainMessage(MESSAGE_SOCKET_SENDMESSAGE,msg).sendToTarget();
                new Thread(){
                    @Override
                    public void run() {
                        mPrintWriter.println(msg);
                        mHandler.obtainMessage(MESSAGE_SOCKET_SENDMESSAGE,msg).sendToTarget();
                    }
                }.start();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long currentTimeMillis) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(currentTimeMillis));
    }
}
