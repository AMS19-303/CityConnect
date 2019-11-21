package com.ams303.cityconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ams303.cityconnect.data.Item;
import com.ams303.cityconnect.data.Store;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final Store store = i.getParcelableExtra("store");
        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_catalog);
        TextView store_name = findViewById(R.id.item_name);
        TextView store_type = findViewById(R.id.item_description);
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

        String items = "[{name:'Leite Agros Meio Gordo 1L', description:'UHT Ultrapasteurizado', base_unit:1, unit:'unidade', unit_price:0.5}, {name:'Carne Picada', description:'Feita na hora', base_unit:500, unit:'gramas', unit_price:1.2}]";

        recyclerView = (RecyclerView) findViewById(R.id.item_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        Type listType = new TypeToken<ArrayList<Item>>() {
        }.getType();
        ArrayList<Item> dataset = new Gson().fromJson(items, listType);

        // specify an adapter
        mAdapter = new MyAdapter(dataset, this);
        recyclerView.setAdapter(mAdapter);


    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Item> mDataset;
    private Context root_context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView item_description;
        public TextView item_price;
        public TextView item_unit;
        public ImageButton item_add;

        public MyViewHolder(View v) {
            super(v);
            item_name = v.findViewById(R.id.item_name);
            item_description = v.findViewById(R.id.item_description);
            item_price = v.findViewById(R.id.item_price);
            item_unit = v.findViewById(R.id.item_unit);
            item_add = v.findViewById(R.id.item_add);
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Item> myDataset, Context context) {
        mDataset = myDataset;
        root_context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Item item = mDataset.get(position);
        holder.item_name.setText(item.getName());
        holder.item_description.setText(item.getDescription());
        holder.item_price.setText(item.getPrice() + "€");
        holder.item_unit.setText("por " + item.getStrBase_unit() + item.getUnit());
        holder.item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Adicionado ao carrinho com sucesso!", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
