package com.theleafapps.pro.shopnick.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.theleafapps.pro.shopnick.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line;

            Pattern pattern = Pattern.compile("Tangho", 0);

            while ((line = bufferedReader.readLine()) != null) {
                if (pattern != null
                        && !pattern.matcher(line).find()) {
                    continue;
                }
                log.append(line);
                log.append("\n");
            }
            TextView tv = (TextView)findViewById(R.id.log_tv);
            tv.setText(log.toString());
        } catch (IOException e) {
        }
    }
}
