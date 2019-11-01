package com.example.climatecarpoolv3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    public static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    public TextView errorText;
    private Button enter;
    private ImageButton settingsButton, homeButton, workButton;

    private boolean isError;

    public String toAddress;
    public String destLatLng = "(0, 0)"; //, city = "Undefined";
    public double destLat = 0.0, destLong = 0.0;

    private PlacesClient placesClient;

    public AutocompleteSupportFragment autocompleteSupportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "AIzaSyDRffrhIgMzWuOP-Xq0qKvI-5ZlIX3G2XE";

        if(!Places.isInitialized()) Places.initialize(getApplicationContext(), apiKey); // getActivity(), apiKey);

        placesClient = Places.createClient(this);

        autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull  Place place) {
                String str = place.toString();
                Log.i("PLACE! INFO", str);
                toAddress = str.substring(str.indexOf("address=") + 8, str.indexOf("attributions=") - 2);
                Log.i("PLACE! ADDRESS", toAddress);

                destLatLng = str.substring(str.indexOf("latLng=lat/lng:") + 16, str.indexOf("name=") - 2);
                destLat = Double.parseDouble(destLatLng.substring(1, destLatLng.indexOf(",")));
                destLong = Double.parseDouble(destLatLng.substring(destLatLng.indexOf(",") + 1, destLatLng.length() - 1));
                Log.i("PLACE! LAT/LNG", destLatLng);

                new GetAQITask().execute(destLatLng);
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.i("PLACE!", "An error occurred: " + status);

                new ResultActivity().AQI = (int)(Math.random() * 501);
            }
        });

        autocompleteSupportFragment.setUserVisibleHint(true);
        autocompleteSupportFragment.setHint("Enter your destination");

        settingsButton = findViewById(R.id.settings);
        errorText = findViewById(R.id.error);
        enter = findViewById(R.id.enterButton);

        homeButton = findViewById(R.id.home);
        workButton = findViewById(R.id.work);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity sA = new SettingsActivity();

                if(sA.homeLatLng.equals("Undefined")) openSettingsActivity();
                else {
                    Log.i("PLACE! LatLng", "" + sA.homeLatLng);
                    new GetAQITask().execute(sA.homeLatLng);
                    toAddress = sA.homeAddress;
                    autocompleteSupportFragment.setText("Home: " + toAddress);
                    //openResultActivity();

                }
            }
        });

        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity sA = new SettingsActivity();

                if(sA.workLatLng.equals("Undefined")) openSettingsActivity();
                else {
                    Log.i("PLACE! LatLng", "" + sA.workLatLng);
                    new GetAQITask().execute(sA.workLatLng);
                    toAddress = sA.workAddress;
                    autocompleteSupportFragment.setText("Work: " + toAddress);
                    //openResultActivity();
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSettingsActivity(); }

        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isError = false;

                if (toAddress.equals("")) {
                    isError = true;
                    errorText.setText("Please input a destination");
                    errorText.setTextColor(rgb(255, 0, 0));
                }

                if (!isError) openResultActivity();
            }
        });

        //new GetAQITask().execute(destLatLng);

    }

    public void openResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        autocompleteSupportFragment.onActivityResult(requestCode, resultCode, data);
    }

}

class GetAQITask extends AsyncTask<String, Void, Integer> {

    protected Integer doInBackground(String... latLng) {

        try {

            double lat = Double.parseDouble(latLng[0].substring(1, latLng[0].indexOf(",")));
            double lng = Double.parseDouble(latLng[0].substring(latLng[0].indexOf(",") + 1, latLng[0].length() - 1));

            lat = Math.round(lat * 10000) / 10000.0;
            lng = Math.round(lng * 10000) / 10000.0;

            URL url = new URL("https://api.waqi.info/feed/geo:" + lat + ";" + lng + "/?token=edfe97da4ab3f58b8a5da3e5bf8e627aa9335070");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String jsonStr = in.readLine();
            String aqiString = "";

            Log.i("PLACE! JSON LINE", jsonStr);

            if (jsonStr.indexOf("aqi") >= 0) {

                for (int i = jsonStr.indexOf("aqi") + 5; i < jsonStr.length(); i++) {

                    if (Character.isDigit(jsonStr.charAt(i))) aqiString += jsonStr.charAt(i);
                    else break;

                }
                in.close();
//                Log.e("JSON LINE", jsonStr);
                return Integer.parseInt(aqiString);

            }
//            Log.e("JSON LINE", jsonStr);
            return (int)(Math.random() * 501);

        } catch (IOException exception){
            Log.i("PLACE! EXCEPTION", "IOException");
            Log.i("PLACE! ERROR", Log.getStackTraceString(new IOException()));
            return (int)(Math.random() * 501);
        }

    }

    protected void onPostExecute(Integer result) {
        new ResultActivity().AQI = result;
    }
}