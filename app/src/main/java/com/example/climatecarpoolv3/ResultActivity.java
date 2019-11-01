package com.example.climatecarpoolv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Color.rgb;

public class ResultActivity extends AppCompatActivity {

    private TextView scaleText, messageText;
    private Button routeButton;

    private ImageView vehicleImage;

    public static int AQI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        routeButton = findViewById(R.id.route);

        scaleText = findViewById(R.id.scale);
        messageText = findViewById(R.id.message);

        vehicleImage = findViewById(R.id.vehicle);

        scaleText.setTextColor(rgb(86, 171, 185));

        if(AQI >= 0 && AQI <= 50){
            messageText.setText("Car");
            scaleText.setText(AQI + " (Good)");
            vehicleImage.setImageResource(R.drawable.car);
        }
        if(AQI >= 51 && AQI <= 100){
            messageText.setText("Ride-Sharing Service or Carpool");
            scaleText.setText(AQI + " (Moderate)");
            vehicleImage.setImageResource(R.drawable.carpool);
        }
        if(AQI >= 101 && AQI <= 150){
            messageText.setText("Bus or Train/Subway");
            scaleText.setText(AQI + " (Unhealthy for sensitive groups)");
            vehicleImage.setImageResource(R.drawable.bus_train);
        }
        if(AQI >= 151 && AQI <= 200){
            messageText.setText("Bike, walk, or electric vehicles");
            scaleText.setText(AQI + " (Unhealthy)");
            vehicleImage.setImageResource(R.drawable.walk_bike);
        }
        if(AQI >= 201 && AQI <= 300){
            messageText.setText("Bike, walk, or electric vehicles");
            scaleText.setText(AQI + " (Very Unhealthy)");
            vehicleImage.setImageResource(R.drawable.walk_bike);
        }
        if(AQI >= 301){
            messageText.setText("Bike, walk, or electric vehicles");
            scaleText.setText(AQI + " (Hazardous)");
            vehicleImage.setImageResource(R.drawable.walk_bike);
        }

        messageText.setTextColor(rgb(0, 0, 0));

        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

    }

    public void openMapActivity(){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

}
