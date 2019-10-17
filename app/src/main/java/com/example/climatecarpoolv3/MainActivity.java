package com.example.climatecarpoolv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    private TextView errorText;
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

        fromAddressText = findViewById(R.id.from);
        toAddressText = findViewById(R.id.destination);
        errorText = findViewById(R.id.error);
        enter = findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isError = false;

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

                if(!isError) openResultActivity();
            }
        });
    }

    public void openResultActivity(){

        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);

    }
}
