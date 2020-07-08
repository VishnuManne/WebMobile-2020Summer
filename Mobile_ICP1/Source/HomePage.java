package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    private Button eLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.dashboard);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        eLogout = findViewById(R.id.btnLogout);
        eLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(redirect1);
            }
        });
    }
}