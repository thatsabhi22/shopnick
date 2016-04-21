package com.theleafapps.shopnick.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theleafapps.shopnick.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WebActivity extends AppCompatActivity {

    DownloadTask downloadTask;
    Button getWebDataButton,parseWebData;
    TextView webDatatv;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getWebDataButton = (Button) findViewById(R.id.getWebDataButton);
        parseWebData     = (Button) findViewById(R.id.parseDataButton);
        webDatatv        = (TextView) findViewById(R.id.webDataTextView);

        getWebDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTask   = new DownloadTask();
                try {
                    result = downloadTask.execute("http://apidev.accuweather.com/currentconditions/v1/204411.json?language=en&apikey=hoArfRosT1215").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(!TextUtils.isEmpty(result)){
                    webDatatv.setText(result);
                }
                else{
                    webDatatv.setText("???");
                }
            }
        });

        parseWebData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(result)){
                    try {
                        //JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = new JSONArray(result);
                        jsonArray.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        });

        }

    private class DownloadTask  extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... urls) {

                String result = "";
                URL url;
                HttpURLConnection urlConnection = null;

                try {

                    url = new URL(urls[0]);

                    urlConnection = (HttpURLConnection)url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader reader = new InputStreamReader(in);

                    int data = reader.read();

                    while (data != -1) {

                        char current = (char) data;

                        result += current;

                        data = reader.read();

                    }

                    return result;

                }
                catch(Exception e) {

                    e.printStackTrace();

                    return "Failed";

                }


            }

    }

}


