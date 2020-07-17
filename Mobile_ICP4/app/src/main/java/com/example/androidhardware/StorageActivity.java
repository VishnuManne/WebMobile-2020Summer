package com.example.androidhardware;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorageActivity extends AppCompatActivity {
    EditText txtData;
    TextView lblData;
    private File pathSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txtData = findViewById(R.id.txtData);
        lblData = findViewById(R.id.lblData);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filename = "sample" + timeStamp + ".txt";

        pathSave = new File(getFilesDir(), filename);
    }
    // ICP Task4: Write the code to save the text

    public void save(View view) {
        String text = txtData.getText().toString();
        text = text + " ";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathSave, true);
            fos.write(text.getBytes());
            txtData.getText().clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // ICP Task4: Write the code to display the above saved text
    public void load(View view) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pathSave);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            lblData.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
