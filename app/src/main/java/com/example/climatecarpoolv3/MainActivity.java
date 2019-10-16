package com.example.climatecarpoolv3;

import android.location.Address;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    private TextView scaleText, messageText, errorText;
    private EditText fromAddressText, toAddressText;
    private Button enter;

    private boolean isError;

    private String fromAddress, toAddress;

    private int key;
    private HashMap<Integer, String> map = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleText = findViewById(R.id.scale);
        messageText = findViewById(R.id.message);
        fromAddressText = findViewById(R.id.from);
        toAddressText = findViewById(R.id.destination);
        errorText = findViewById(R.id.error);
        enter = findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isError = false;
                key = (int)Math.round(Math.random() * 10);

                fromAddress = "" + fromAddressText.getText();
                toAddress = "" + toAddressText.getText();

                if(fromAddress.equals("") && toAddress.equals("")){
                    isError = true;
                    errorText.setText("Please input a starting location and a destination");
                    errorText.setTextColor(rgb(255, 0, 0));
                } else if(fromAddress.equals("")){
                    isError = true;
                    errorText.setText("Please input a starting location");
                    errorText.setTextColor(rgb(255, 0, 0));
                } else if(toAddress.equals("")){
                    isError = true;
                    errorText.setText("Please input a destination");
                    errorText.setTextColor(rgb(255, 0, 0));
                }

                if(!isError) {
                    scaleText.setTextColor(rgb(86, 171, 185));
                    errorText.setTextColor(rgb(255, 255, 255));

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
        });
    }
}
