package com.example.medicalassistantapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView VoiceInput;
    private ImageButton RecordBtn;
    TextToSpeech tts;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        setContentView(R.layout.activity_main);

        // Fetching text view, speak button by Id's
        VoiceInput = (TextView) findViewById(R.id.voiceInput);
        RecordBtn = (ImageButton) findViewById(R.id.btnSpeak);

        // Initializing Preferences, Editor
        preferences = getSharedPreferences("namePrefs",0);
        editor = preferences.edit();

        // Say Hello First on Page Load
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    // Setting the Locale.
                    tts.setLanguage(Locale.US);
                    tts.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);
                    VoiceInput.setText(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> Hello !!</p>"));
                }
            }
        });
        RecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        // Starting Voice Input on click of Mic button
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    // Fethcing the result
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result != null && result.size() > 0) {
                        VoiceInput.append(Html.fromHtml("<p style=\"color:#0000FF;\"><b>User :</b> "+result.get(0)+"</p>"));
                        // If user says hello, Ask for User's name & show the Greeting Text with users name.
                        if(result.get(0).equalsIgnoreCase("hello")) {
                            tts.speak(" What's your name ?", TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> What is your name ?</p>"));
                        }else if(result.get(0).contains("name")){
                            // Set the Greeting by indexing
                            String name = result.get(0).substring(result.get(0).lastIndexOf(' ') + 1);
                            // Setting into Editor
                            editor.putString("name", name).apply();
                            tts.speak("Hello, "+name,
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> Hello, "+name+"</p>"));
                        }else if(result.get(0).contains("not feeling good")){
                            tts.speak("I can understand. Please tell your symptoms in short",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> I can understand. Please tell your symptoms in short</p>"));
                        }else if(result.get(0).contains("thank you")){
                            tts.speak("Thank you too, "+preferences.getString("name","")+" Take care.", TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> Thank you too, "+preferences.getString("name","")+" Take care.</p>"));
                        }else if(result.get(0).contains("what time is it")){
                            // Speaking the Time for the User
                            SimpleDateFormat sdfDate =new SimpleDateFormat("HH:mm");//dd/MM/yyyy
                            Date now = new Date();
                            String[] strDate = sdfDate.format(now).split(":");
                            if(strDate[1].contains("00"))strDate[1] = "o'clock";
                            tts.speak("The time is : "+sdfDate.format(now), TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> The time is : "+sdfDate.format(now)+"</p>"));
                        }else if(result.get(0).contains("medicine")){
                            tts.speak("I think you have fever. Please take this medicine.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b> I think you have fever. Please take this medicine.</p>"));
                        } else {
                            tts.speak("Sorry, I cant help you with that", TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInput.append(Html.fromHtml("<p style=\"color:#FF3399;\"><b>Assistant :</b>Sorry, I cant help you with that</p>"));
                        }
                    }
                }
                break;
            }

        }
    }
}