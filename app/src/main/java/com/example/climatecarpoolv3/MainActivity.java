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
    private ImageButton settingsButton;

    private boolean isError;

    public String toAddress, city = "Undefined";

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
                Log.i("PLACE INFO", str);
                toAddress = place.toString().substring(str.indexOf("address=") + 8, str.indexOf("attributions=") - 2);
                Log.i("PLACE ADDRESS", toAddress);

                city = toAddress.substring(toAddress.indexOf(",") + 2, toAddress.indexOf(",", toAddress.indexOf(",") + 2));
            }


            /*
            Place{address=10100 Finch Ave, Cupertino, CA 95014, USA, attributions=[], id=ChIJOfgIZH-2j4ARfN8hMUB-lUM, latLng=lat/lng: (37.3194327,-122.0091231), name=Cupertino High School, openingHours=null, phoneNumber=null, photoMetadatas=null, plusCode=null, priceLevel=null, rating=null, types=null, userRatingsTotal=null, viewport=null, websiteUri=null}
            Place{address=10601 S De Anza Blvd #108, Cupertino, CA 95014, USA, attributions=[], id=ChIJRznvzAC1j4ARdEs5EP7tkLs, latLng=lat/lng: (37.3141452,-122.0326681), name=Jr. Programmer, openingHours=null, phoneNumber=null, photoMetadatas=null, plusCode=null, priceLevel=null, rating=null, types=null, userRatingsTotal=null, viewport=null, websiteUri=null}
             */

            @Override
            public void onError(@NonNull Status status) {
                Log.i("PLACE", "An error occurred: " + status);
            }
        });

        autocompleteSupportFragment.setUserVisibleHint(true);
        autocompleteSupportFragment.setHint("Enter your destination");

        settingsButton = findViewById(R.id.settings);
        errorText = findViewById(R.id.error);
        enter = findViewById(R.id.enterButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSettingsActivity();

            }

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

        new GetAQITask().execute(city);

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

    protected Integer doInBackground(String... city) {

        try {
            URL url = new URL("https://api.waqi.info/feed/" + city[0] + "/?token=edfe97da4ab3f58b8a5da3e5bf8e627aa9335070");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String jsonStr = in.readLine();
            String aqiString = "";

            if (jsonStr.indexOf("aqi") >= 0) {

                for (int i = jsonStr.indexOf("aqi") + 5; i < jsonStr.length(); i++) {

                    if (Character.isDigit(jsonStr.charAt(i))) aqiString += jsonStr.charAt(i);
                    else break;

                }
                in.close();
//                Log.e("JSON LINE", jsonStr);
//                Log.e("CITY", city[0]);
                return Integer.parseInt(aqiString);

            }
//            Log.e("JSON LINE", jsonStr);
//            Log.e("CITY", city[0]);
            return (int)(Math.random() * 501);
            //ResultActivity.AQI = Integer.parseInt(apiString);

        } catch (IOException exception){
//            Log.e("JSON LINE", "IOException");
//            Log.e("CITY", city[0]);
            return (int)(Math.random() * 501);
        }


    }

    protected void onPostExecute(Integer result) {
        new ResultActivity().AQI = result;
    }
}