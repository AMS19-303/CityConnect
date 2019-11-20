package com.ams303.cityconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ams303.cityconnect.data.Store;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final Store store = i.getParcelableExtra("store");
        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_catalog);
        TextView store_name = findViewById(R.id.store_name);
        TextView store_type = findViewById(R.id.store_type);
        TextView store_open = findViewById(R.id.store_open);
        ImageView store_image = findViewById(R.id.store_image);

        store_name.setText(store.getName());
        store_type.setText(store.getType());
        store_open.setText("ABERTO");
        Picasso.with(this)
                .load(store.getPhoto())
                .centerCrop()
                .fit()
                .into(store_image);


        List<String> categories_lst = Arrays.asList(store.getCategories());
        Collections.sort(categories_lst);

        TextView categories = findViewById(R.id.categories);
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < categories_lst.size(); j++) {
            sb.append(categories_lst.get(j));
            if (j != categories_lst.size() - 1){
                sb.append(", ");
            }
        }
        categories.setText(sb.toString());

        final MapView mapView = findViewById(R.id.mapView);

        final Chip map_chip = findViewById(R.id.map_chip);
        map_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map_chip.isChecked()){
                    mapView.setVisibility(View.VISIBLE);
                }
                else {
                    mapView.setVisibility(View.GONE);
                }
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        double[] gps = store.getGps();

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        mapboxMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(gps[0], gps[1]), 14.0f) );
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(gps[0], gps[1]))
                                .title(store.getName()));

                    }
                });

            }
        });

    }
}
