package com.example.climatecarpoolv3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    private Button back;

    public static String homeAddress = "", homeLatLng = "Undefined", workAddress = "", workLatLng = "Undefined";

    public AutocompleteSupportFragment homeAutocomplete, workAutocomplete;

    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.backButton);
        back.setText("Home");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        String apiKey = "AIzaSyDRffrhIgMzWuOP-Xq0qKvI-5ZlIX3G2XE";

        if(!Places.isInitialized()) Places.initialize(getApplicationContext(), apiKey); // getActivity(), apiKey);

        placesClient = Places.createClient(this);

        homeAutocomplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.home_autocomplete_fragment);
        workAutocomplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.work_autocomplete_fragment);

        homeAutocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        workAutocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        homeAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String str = place.toString();
                //Log.i("PLACE! INFO", str);
                homeAddress = str.substring(str.indexOf("address=") + 8, str.indexOf("attributions=") - 2);
                Log.i("PLACE! ADDRESS", homeAddress);

                homeLatLng = str.substring(str.indexOf("latLng=lat/lng:") + 16, str.indexOf("name=") - 2);
                Log.i("PLACE! ADDRESS", homeLatLng);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("PLACE!", "An error occurred: " + status);

                //new ResultActivity().AQI = (int)(Math.random() * 501);
            }
        });

        workAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String str = place.toString();
                //Log.i("PLACE! INFO", str);
                workAddress = str.substring(str.indexOf("address=") + 8, str.indexOf("attributions=") - 2);
                //Log.i("PLACE! ADDRESS", toAddress);

                workLatLng = str.substring(str.indexOf("latLng=lat/lng:") + 16, str.indexOf("name=") - 2);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("PLACE!", "An error occurred: " + status);

                //new ResultActivity().AQI = (int)(Math.random() * 501);
            }
        });

        homeAutocomplete.setUserVisibleHint(true);
        homeAutocomplete.setHint("Enter home address");

        workAutocomplete.setUserVisibleHint(true);
        workAutocomplete.setHint("Enter work address");
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        homeAutocomplete.onActivityResult(requestCode, resultCode, data);
        workAutocomplete.onActivityResult(requestCode, resultCode, data);
    }
}
