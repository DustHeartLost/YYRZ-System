package com.yyrz.patient.common.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.yyrz.patient.common.myException.ErrorCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class MyHTTP {
    //private final static String host = "http://192.168.2.177:8763/";
    private final static String host = "http://zhangxiye.free.vipnps.vip/patient-api/";
    public <T> void get(String url, final String param, final Handler handler, final Class<T> t ) {
        final String getURL = host + url+"?"+param;
        Log.d("我",getURL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    Gson gson = new Gson();
                    URL getUrl = new URL(getURL);
                    HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.connect();
                    Log.d("我", String.valueOf(connection.getResponseCode()));
                    switch (connection.getResponseCode()) {
                        case 200: {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String lines;
                            StringBuilder json= new StringBuilder();
                            while ((lines = reader.readLine()) != null) {
                                json.append(lines);
                            }
                            reader.close();
                            connection.disconnect();
                            T temp;
                            temp= gson.fromJson(json.toString(),t);
                            message.what = 100;
                            message.obj = temp;
                        }
                        break;
                        case 500: {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            String lines;
                            StringBuilder json= new StringBuilder();
                            while ((lines = reader.readLine()) != null) {
                                json.append(lines);
                            }
                            reader.close();
                            connection.disconnect();
                            ErrorCode errorCode = gson.fromJson(json.toString(), ErrorCode.class);
                            message.what = 101;
                            message.obj = errorCode;
                        }
                        break;
                        default:{
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            String lines;
                            StringBuilder json= new StringBuilder();
                            while ((lines = reader.readLine()) != null) {
                                json.append(lines);
                            }
                            reader.close();
                            connection.disconnect();
                            Log.d("我",json.toString());
                            ErrorCode errorCode=new ErrorCode();
                            errorCode.setCode(400);
                            errorCode.setMsg(json.toString());
                            message.obj=errorCode;
                        }break;
                    }
                } catch (IOException ex) {
                    message.what = -1;
                    ErrorCode errorCode=new ErrorCode();
                    errorCode.setCode(-1);
                    errorCode.setMsg(ex.getMessage());
                    message.obj = errorCode;
                }
                if(handler!=null) handler.sendMessage(message);
            }
        }).start();
    }
}
