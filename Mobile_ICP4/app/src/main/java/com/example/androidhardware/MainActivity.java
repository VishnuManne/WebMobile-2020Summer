package com.example.androidhardware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Intent redirect = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LocationDetails(View view) {
        redirect = new Intent(MainActivity.this,LocationActivity.class);
        startActivity(redirect);
    }

    public void takePhoto(View view) {
        redirect = new Intent(MainActivity.this,CameraActivity.class);
        startActivity(redirect);
    }

    public void recordVoice(View view) {
        redirect = new Intent(MainActivity.this,RecordingActivity.class);
        startActivity(redirect);
    }

    public void saveData(View view) {
        redirect = new Intent(MainActivity.this,StorageActivity.class);
        startActivity(redirect);
    }
}