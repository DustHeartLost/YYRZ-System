package com.yyrz.doctor.Util.DataRestored;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.model.Record;
import com.yyrz.doctor.Util.myhttp.MyHTTP;
import com.yyrz.doctor.Util.util.GsonAdapter;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Repository {

    private static Repository instance;

    private Repository(){}

    public static Repository getInstance() {
        if(instance==null){
            instance=new Repository();
        }
        return  instance;
    }

    public void insertRecord(Record...records){
        new InsertRecord().execute(records);
   }

    public void deleteRecord(String...strings){
        new DeleteRecord().execute(strings);
    }

    public void querydRecord(String...strings){
        new QueryRecord().execute(strings);
    }

    public void insertMoca(Moca... mocas) {
        new InsertMoca().doInBackground(mocas);
    }

    public void queryMoca(){
        new QueryMoca().execute();
    }

    static class InsertRecord extends AsyncTask<Record,Void,Void>{

        @Override
        protected Void doInBackground(Record... Records) {
            for (Record temp:Records){
                File file=new File(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/"+temp.getPaccount()+".txt");
                try {
                    FileOutputStream out=new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(temp);
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
            return null;
        }
    }

    static class DeleteRecord extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            for(String temp:strings) {
                File file=new File(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/"+temp+".txt");
                if(file.exists())file.delete();;
                File imagefile=new File(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + temp + ".jpg");
                if(imagefile.exists()) imagefile.delete();
            }
            return null;
        }
    }

    static class QueryRecord extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            for(String temp:strings){
                File file=new File((CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS))+"/"+temp+".txt");
                if(file.exists()) {
                    try {
                        FileInputStream in=new FileInputStream(file);
                        ObjectInputStream objectOutputStream = new ObjectInputStream(in);
                        CommonViewModel.getInstance().setRecord((Record) objectOutputStream.readObject());
                     } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                   Bitmap bitmap = BitmapFactory.decodeFile(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + temp + ".jpg");
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] bytes = baos.toByteArray();
                        Base64.Encoder encoder = Base64.getEncoder();
                        String xx = encoder.encodeToString(bytes);
                        try {
                            CommonViewModel.getInstance().getRecord().setPhoto(URLEncoder.encode(xx, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        CommonViewModel.getInstance().getBitmap().postValue(ThumbnailUtils.extractThumbnail(bitmap,100,100));
                    }else {
                        CommonViewModel.getInstance().getBitmap().postValue(null);
                    }
                    CommonViewModel.getInstance().getIsQueryEnd().postValue(1);
                }else{
                    CommonViewModel.getInstance().getIsQueryEnd().postValue(2);
                }
            }
            return null;
        }
    }

    static class InsertMoca extends AsyncTask<Moca,Void,Void>{
        @Override
        protected Void doInBackground(Moca... mocas) {
            for (Moca temp:mocas){
                File file=new File(String.valueOf(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))+"/"+temp.getDate()+".txt");
                try {
                    FileOutputStream out=new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    static class QueryMoca extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            File file=new File(String.valueOf(CommonViewModel.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)));
            File[] files=file.listFiles();
            if(files==null){
                return null;
            }
            for(File temp:files) {
                Moca moca = null;
                try {
                    FileInputStream in = new FileInputStream(temp);
                    ObjectInputStream objectOutputStream = new ObjectInputStream(in);
                    moca = (Moca) objectOutputStream.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(postMoca(moca)) temp.delete();
                else return null;

            }
            return null;
        }

        private boolean postMoca(Moca moca) {
            boolean flag=false;
            try {
                URL url = new URL(MyHTTP.host+"moca");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                Gson gson = new GsonBuilder().registerTypeAdapter(Moca.class, new GsonAdapter<>()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                os.write(gson.toJson(moca).getBytes(StandardCharsets.UTF_8));
                os.flush();
                connection.connect();
                if (connection.getResponseCode() == 200)flag=true;
                else {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String lines;
                    StringBuilder json= new StringBuilder();
                    while ((lines = reader.readLine()) != null) {
                        json.append(lines);
                    }
                    reader.close();
                    connection.disconnect();
                    flag=false;
                    Log.d("我queryMoca",json.toString());}
            } catch (IOException e) {
                e.printStackTrace();
            }
            return flag;
               }
    }
}

