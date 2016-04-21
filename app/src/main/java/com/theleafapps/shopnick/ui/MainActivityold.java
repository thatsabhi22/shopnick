package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.theleafapps.shopnick.R;

public class MainActivityold extends AppCompatActivity {

    Button dataButton,photoButton,audioButton,videoButton,contactsButton,locationButton,pushButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        dataButton      =   (Button) findViewById(R.id.dataStorageButton);
//        photoButton     =   (Button) findViewById(R.id.photoButton);
//        audioButton     =   (Button) findViewById(R.id.audioButton);
//        videoButton     =   (Button) findViewById(R.id.videoButton);
//        contactsButton  =   (Button) findViewById(R.id.contactsButton);
//        locationButton  =   (Button) findViewById(R.id.locationButton);
//        pushButton      =   (Button) findViewById(R.id.pushNotiButton);
    }

    public void redirect(View view){
//        Intent intent = null;
//
//        if (view.getId() == R.id.dataStorageButton){
//            intent = new Intent(this, com.theleafapps.shopnick.ui.DataListActivity.class);
//        }
//        else if(view.getId() == R.id.pushNotiButton){
//            intent = new Intent(this,PushActivity.class);
//        }
//        else if(view.getId() == R.id.photoButton){
//            intent = new Intent(this,PhotoActivity.class);
//        }
//        else if(view.getId() == R.id.audioButton){
//            intent = new Intent(this,AudioActivity.class);
//        }
//        else if(view.getId() == R.id.videoButton){
//            intent = new Intent(this,VideoActivity.class);
//        }
//        else if(view.getId() == R.id.contactsButton){
//            intent = new Intent(this,ContactsActivity.class);
//        }
//
//        startActivity(intent);
    }
}
