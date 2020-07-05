package com.yyrz.doctor.Util.myhttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yyrz.doctor.Util.exception.ErrorCode;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.util.GsonAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MyHTTP {
    //此时为局域网IP地址
    public final static String host = "http://192.168.0.103:8762/";
//
    public <T> void get(String url, final String param, final Handler handler,final Class<T> t ) {
        final String getURL = host + url+"?"+param;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    Gson gson = new Gson();
                    URL getUrl = new URL(getURL);
                    HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                    connection.setConnectTimeout(4000);
                    connection.connect();
                    Log.d("我",getURL);
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

    public <T> void post(String url, final Handler handler,final T t){
        final String posturl=host+url;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Field[] fields=Moca.class.getDeclaredFields();
                Message message=new Message();
                try {
                    URL url = new URL(posturl);
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    OutputStream os=connection.getOutputStream();
                    Gson gson = new GsonBuilder().registerTypeAdapter(Moca.class,new GsonAdapter<>()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    os.write(gson.toJson(t).getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    connection.connect();
                    String lines;
                    StringBuilder json= new StringBuilder();
                    switch (connection.getResponseCode()){
                        case 200:{
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            while ((lines = reader.readLine()) != null) {
                                json.append(lines);
                            }
                            reader.close();
                            connection.disconnect();
                            message.obj= gson.fromJson(json.toString(),Boolean.class);
                            message.what=100;
                        }break;
                        case 500:{
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            while ((lines = reader.readLine()) != null) {
                                json.append(lines);
                            }
                            reader.close();
                            connection.disconnect();
                            ErrorCode errorCode = gson.fromJson(json.toString(), ErrorCode.class);
                            message.what = 103;
                            message.obj = errorCode;
                        }break;
                        default:{
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
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
                } catch (IOException e) {
                    message.what = -1;
                    ErrorCode errorCode=new ErrorCode();
                    errorCode.setCode(-1);
                    errorCode.setMsg(e.getMessage());
                    message.obj = errorCode;
                }
                handler.sendMessage(message);
            }
        }).start();
    }
}

