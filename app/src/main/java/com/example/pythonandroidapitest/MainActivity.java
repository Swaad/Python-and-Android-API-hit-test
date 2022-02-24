package com.example.pythonandroidapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button3 = (Button) findViewById(R.id.button3);
        try{
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    get();
                    post();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void post(){
        try{
            OkHttpClient client = new OkHttpClient();

            JsonObject postData = new JsonObject();
            postData.addProperty("user_name", "morpheus");
            postData.addProperty("email", "leader");

            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody postBody = RequestBody.create(JSON, postData.toString());
            Request post = new Request.Builder()
                    .url("http://10.11.201.34:5000/asd")
                    .post(postBody)
                    .build();

            client.newCall(post).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        ResponseBody responseBody = response.body();
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        Log.i("data", responseBody.string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void get(){
        try{
            //Log.d("showModelResponse", "button pressed");
            String url="http://10.11.201.34:5000/asd";
            JSONArray jsonArray = new JSONArray();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();

            //RequestBody requestBody = RequestBody.create(JSON, jsonArray.toString());
            Request request=new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Toast.makeText(MainActivity.this,"network not found",Toast.LENGTH_LONG).show();
                    Log.d("showModelResponse", "Error FOUND");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("showModelResponse", "No Error");

                    try {
                        ResponseBody responseBody = response.body();
                        String body = responseBody.string();
                        Log.d("showModelResponse", "responseBody: " + body);
                        JSONObject jObject = new JSONObject(body);
                        Log.d("showModelResponse", "responseBody: " + jObject.getString("message"));
                        Log.d("showModelResponse", "responseBody: " + jObject.getString("severity"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}