package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edName;
    private EditText edPassword;
    private Button eLogin;
    private TextView teAttemptsInfo;
    private String username = "Vishnu";
    private String password = "qwerty123";
    boolean isValid = false;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = findViewById(R.id.etName);
        edPassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        teAttemptsInfo = findViewById(R.id.tvAttemptsInfo);
        
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String inputName = edName.getText().toString();
                String inputPassword = edPassword.getText().toString();
                if(inputName.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the Fields!", Toast.LENGTH_SHORT).show();
                } else {

                    isValid = validate(inputName, inputPassword);

                    if (!isValid) {
                        counter--;
                        Toast.makeText(MainActivity.this, "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                        teAttemptsInfo.setText("Number of Attempts Remaining: " + counter);
                        if (counter == 0) {
                            eLogin.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent redirect = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(redirect);
                    }
                }
            }
        });
    }
    private boolean validate(String Name, String Password){

        return Name.equals(username) && Password.equals(password);
    }
    
}