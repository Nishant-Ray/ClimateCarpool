package com.example.climatecarpoolv3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import static android.graphics.Color.rgb;

public class ResultActivity extends AppCompatActivity {

    private TextView scaleText, messageText;

    private int key;
    private HashMap<Integer, String> map = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


//        if(aqi is 0 to 50): Gas powered automobile (Good)
//        if(aqi is 51 to 100): Ride-sharing service or carpool (Moderate)
//        if(aqi is 101 to 150): Bus or train/subway (Unhealthy for sensitive groups)
//        if(aqi is 151 to 200): Bike, walk, or electric vehicles (Unhealthy)
//        if(aqi is 201 to 300): Bike, walk, or electric vehicles (Very unhealthy)
//        if(aqi is 301 or higher): Bike, walk, or electric vehicles (hazardous)

        scaleText = findViewById(R.id.scale);
        messageText = findViewById(R.id.message);

        key = (int)Math.round(Math.random() * 10);

        scaleText.setTextColor(rgb(86, 171, 185));

        for (int i = 0; i <= 10; i++) {

            if (i == 0)
                map.put(i, "Regular commute does not harm the Earth ❤");
            else if (i == 1)
                map.put(i, "Regular commute inflicts little to no harm to the Earth. Carpooling or other alternate commute is not necessary.");
            else if (i == 2)
                map.put(i, "Regular commute somewhat harms the Earth. Carpooling or other alternate commute should be considered.");
            else if (i == 3)
                map.put(i, "Regular commute partially harms the Earth. Carpooling or other alternate commute should definitely be considered.");
            else if (i == 4)
                map.put(i, "Regular commute inflicts a reasonable amount of harm to the Earth. Carpooling or other alternate commute is recommended.");
            else if (i == 5)
                map.put(i, "Regular commute moderately harms the Earth. Carpooling or other alternate commute is recommended.");
            else if (i == 6)
                map.put(i, "Regular commute considerably harms the Earth. Carpooling or other alternate commute is recommended.");
            else if (i == 7)
                map.put(i, "Regular commute damages the Earth. Carpooling or other alternate commute is extremely recommended.");
            else if (i == 8)
                map.put(i, "Regular commute harms the Earth. Carpooling or other alternate commute is highly recommended.");
            else if (i == 9)
                map.put(i, "Regular commute significantly harms the Earth. Carpooling or other alternate commute is essentially mandatory.");
            else if (i == 10)
                map.put(i, "Regular commute will burn the Earth. You better Uber!");

        }

        scaleText.setText("" + key);
        messageText.setText(map.get(key));
        messageText.setTextColor(rgb(114, 114, 114));
    }
}
