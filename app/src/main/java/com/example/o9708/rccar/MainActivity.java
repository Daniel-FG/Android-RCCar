package com.example.o9708.rccar;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
{
    short mode;  //紀錄目前是哪個模式
    static final short stop = 101, forward = 102, forwardLeft = 103, forwardRight = 104,
            backward = 105, backwardLeft = 106, backwardRight = 107,
            remote = 1, auto = 2;  //設定數值
    Button buttonMode1, buttonMode2, buttonForward, buttonBackward, buttonForwardLeft,
            buttonForwardRight, buttonBackwardLeft, buttonBackwardRight,
            buttonConnect, buttonDisconnect;  //宣告按鈕
    TextView textView;
    BluetoothAdapter bluetoothAdapter;  //宣告藍芽
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMode1 = (Button) findViewById(R.id.buttonMode1);  //連結按鈕到ID
        buttonMode2 = (Button) findViewById(R.id.buttonMode2);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        buttonForwardLeft = (Button) findViewById(R.id.buttonForwardLeft);
        buttonForwardRight = (Button) findViewById(R.id.buttonForwardRight);
        buttonBackward = (Button) findViewById(R.id.buttonBackward);
        buttonBackwardLeft = (Button) findViewById(R.id.buttonBackwardLeft);
        buttonBackwardRight = (Button) findViewById(R.id.buttonBackwardRight);
        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);
        textView = (TextView) findViewById(R.id.textView);

        buttonConnect.setOnClickListener(new View.OnClickListener()  //按下連接鈕後
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    findBluetooth();
                    openBluetooth();
                }
                catch(IOException e)
                {

                }
            }
        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener()  //按下斷開連接鈕後
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    closeBluetooth();
                }
                catch(IOException e)
                {

                }
            }
        });

        buttonMode1.setOnClickListener(new View.OnClickListener()  //按下遙控鈕後
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    mode1();
                }
                catch (IOException e)
                {

                }
            }
        });

        buttonMode2.setOnClickListener(new View.OnClickListener()  //按下自走鈕後
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    mode2();
                }
                catch (IOException e)
                {

                }
            }
        });

        buttonForward.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                try
                {
                    if(mode == remote)
                    {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                                outputStream.write(forward);
                                break;
                            case MotionEvent.ACTION_UP:
                                outputStream.write(stop);
                                break;
                        }
                        return true;
                    }
                    else if(mode == auto)
                    {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_UP:
                                outputStream.write(forward);
                                break;
                        }
                        return true;
                    }
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });



        buttonForwardRight.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                try
                {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            outputStream.write(forwardRight);
                            break;
                        case MotionEvent.ACTION_UP:
                            outputStream.write(stop);
                            break;
                    }
                    return true;
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });

        buttonForwardLeft.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                try
                {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            outputStream.write(forwardLeft);
                            break;
                        case MotionEvent.ACTION_UP:
                            outputStream.write(stop);
                            break;
                    }
                    return true;
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });

        buttonBackward.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                try
                {
                    if(mode == remote)
                    {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                                outputStream.write(backward);
                                break;
                            case MotionEvent.ACTION_UP:
                                outputStream.write(stop);
                                break;
                        }
                        return true;
                    }
                    else if(mode == auto)
                    {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_UP:
                                outputStream.write(backward);
                                break;
                        }
                        return true;
                    }
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });

        buttonBackwardRight.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                try
                {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            outputStream.write(backwardRight);
                            break;
                        case MotionEvent.ACTION_UP:
                            outputStream.write(stop);
                            break;
                    }
                    return true;
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });

        buttonBackwardLeft.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                try
                {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            outputStream.write(backwardLeft);
                            break;
                        case MotionEvent.ACTION_UP:
                            outputStream.write(stop);
                            break;
                    }
                    return true;
                }
                catch(IOException e)
                {

                }
                return false;
            }
        });
    }

    void mode1() throws IOException  //遙控模式
    {
        mode = remote;
        buttonMode1.setEnabled(false);
        buttonMode2.setEnabled(true);
        buttonForward.setEnabled(true);
        buttonBackward.setEnabled(true);
        buttonForwardLeft.setEnabled(true);
        buttonForwardRight.setEnabled(true);
        buttonBackwardLeft.setEnabled(true);
        buttonBackwardRight.setEnabled(true);
        buttonBackward.setText("後退");
        outputStream.write(remote);
    }

    void mode2() throws IOException  //自走模式
    {
        mode = auto;
        buttonMode1.setEnabled(true);
        buttonMode2.setEnabled(false);
        buttonForward.setEnabled(true);
        buttonBackward.setEnabled(true);
        buttonForwardLeft.setEnabled(false);
        buttonForwardRight.setEnabled(false);
        buttonBackwardLeft.setEnabled(false);
        buttonBackwardRight.setEnabled(false);
        buttonBackward.setText("停止");
        outputStream.write(auto);
    }

    void findBluetooth()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  //取得手機的藍芽適配器
        if(!bluetoothAdapter.isEnabled())  //如果手機的藍芽未開啟的話
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetooth);  //跳出詢問使用者是否開啟藍芽的視窗
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)  //如果附近還有其他裝置
        {
            for(BluetoothDevice device : pairedDevices)
            {
                textView.setText(device.getName());
                bluetoothDevice = device;
                break;
            }
        }
    }

    void openBluetooth() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        if(bluetoothDevice != null)  //如果有找到藍芽裝置 (藍芽連線成功)
        {
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();  //藍芽連線
            outputStream = bluetoothSocket.getOutputStream();  //傳送指令

            buttonConnect.setEnabled(false);
            buttonDisconnect.setEnabled(true);
            buttonMode1.setEnabled(true);
            buttonMode2.setEnabled(true);
        }
    }

    void closeBluetooth() throws IOException
    {
        outputStream.close();
        bluetoothSocket.close();

        buttonMode1.setEnabled(false);
        buttonMode2.setEnabled(false);
        buttonForward.setEnabled(false);
        buttonBackward.setEnabled(false);
        buttonForwardRight.setEnabled(false);
        buttonForwardLeft.setEnabled(false);
        buttonBackwardRight.setEnabled(false);
        buttonBackwardLeft.setEnabled(false);
        buttonDisconnect.setEnabled(false);
        buttonConnect.setEnabled(true);
    }

}
