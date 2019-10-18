package com.example.climatecarpoolv3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Color.rgb;

public class ResultActivity extends AppCompatActivity {

    private TextView scaleText, messageText;
    private Button rout



        eButton;

    private final int AQI = (int) (Math.random() * 501);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scaleText = findViewById(R.id.scale);
        messageText = findViewById(R.id.message);

        scaleText.setTextColor(rgb(86, 171, 185));

        if(AQI >= 0 && AQI <= 50) messageText.setText("Gas powered automobile (Good)");
        if(AQI >= 51 && AQI <= 100) messageText.setText("Ride-sharing service or carpool (Moderate)");
        if(AQI >= 101 && AQI <= 150) messageText.setText("Bus or train/subway (Unhealthy for sensitive groups)");
        if(AQI >= 151 && AQI <= 200) messageText.setText("Bike, walk, or electric vehicles (Unhealthy)");
        if(AQI >= 201 && AQI <= 300) messageText.setText("Bike, walk, or electric vehicles (Very unhealthy)");
        if(AQI >= 301) messageText.setText("Bike, walk, or electric vehicles (hazardous)");

        scaleText.setText("AQI = " + AQI);

        messageText.setTextColor(rgb(114, 114, 114));



    }



}
