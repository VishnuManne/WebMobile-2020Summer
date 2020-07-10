package com.example.pizzaorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
// Create Order Summary
public class Summary extends AppCompatActivity {

    TextView summaryText;
    Button OrderButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        summaryText = findViewById(R.id.summaryText);
        summaryText.setText(Html.fromHtml("<u>Your Order Summary</u><br/><br/>"));
        if (getIntent() != null) {
            summaryText.append(getIntent().getStringExtra("orderSummary"));
        } else {
            summaryText.setText("You have no orders !!");
        }
        summaryText.append(Html.fromHtml("<br/>"));
        summaryText.setVisibility(View.VISIBLE);
        OrderButton = findViewById(R.id.orderinsummaryXML);

        OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reDirectToOrderingPage();
            }
        });
    }
    public void reDirectToOrderingPage() {
        Intent intent = new Intent(Summary.this, Orderpage.class);
        startActivity(intent);
    }

}
