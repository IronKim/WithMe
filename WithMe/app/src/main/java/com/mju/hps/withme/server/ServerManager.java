package com.mju.hps.withme.server;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by KMC on 2016. 11. 15..
 */

public class ServerManager {
    OkHttpClient client;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    static ServerManager serverManager = new ServerManager();

    private ServerManager(){
        Log.e("ServerManager", "생성자");
        client = new OkHttpClient();
    }

    public static ServerManager getInstance(){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        return serverManager;
    }

    public String get(String url){
        Log.i("ServerManager.get", "get(" + url + ")");
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            Log.e("ServerManager.get", e.toString());
            return null;
        }
    }

    public String post(String url, String json) {
        Log.i("ServerManager.post", "post(" + url + ", "  +  json + ")");
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            Log.e("ServerManager.post", e.toString());
            return null;
        }
    }

    public String put(String url, String json) {
        Log.i("ServerManager.put", "put(" + url + ")");
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            Log.e("ServerManager.put", e.toString());
            return null;
        }
    }

    public String delete(String url, String json) {
        Log.i("ServerManager.delete", "delete(" + url + ")");
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            Log.e("ServerManager.delete", e.toString());
            return null;
        }
    }

}
