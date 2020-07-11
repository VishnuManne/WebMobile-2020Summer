package com.example.pizzaorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

    }
    public void ordernow(View view) {
        Intent intent = new Intent(Homepage.this, Orderpage.class);
        startActivity(intent);
    }
}
