package com.ams303.cityconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ams303.cityconnect.data.Courier;
import com.ams303.cityconnect.data.Order;
import com.ams303.cityconnect.data.OrderItem;
import com.ams303.cityconnect.data.Store;
import com.ams303.cityconnect.lib.utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private TextView order_date;
    private TextView order_from;
    private TextView order_to;
    private TextView order_total;
    private TextView order_price;
    private TextView order_status;
    private ImageView order_rating;
    private ConstraintLayout order_view;

    private ConstraintLayout courier_view;
    private TextView courier_name;
    private TextView courier_number;
    private TextView courier_rating;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MapView mapView;
    private RequestQueue queue;

    private Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final Order order = i.getParcelableExtra("order");
        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
        savedState = savedInstanceState;

        setContentView(R.layout.activity_delivery);

        order_date = findViewById(R.id.order_date);
        order_from = findViewById(R.id.order_from);
        order_to = findViewById(R.id.order_to);
        order_total = findViewById(R.id.order_total_tv);
        order_price = findViewById(R.id.order_price);
        order_status = findViewById(R.id.order_status);
        order_rating = findViewById(R.id.order_rating);
        order_view = findViewById(R.id.order_view);

        courier_view = findViewById(R.id.courier_view);
        courier_name = findViewById(R.id.courier_name);
        courier_number = findViewById(R.id.courier_number);
        courier_rating = findViewById(R.id.courier_rating);

        order_date.setText(utils.getPrettyTimestamp(order.getTimestamp()));
        order_from.setText(order.getStores());
        order_to.setText(order.getAddress());

        if(order.isActive()) {
            order_total.setText("Subtotal:");
            order_price.setText(utils.getFormattedPrice(order.getTotal_price()));
            if(order.hasCourier()) {
                order_status.setText("ATIVA");
                order_status.setTextColor(Color.GREEN);
            }
            else {
                order_status.setText("PENDENTE");
                order_status.setTextColor(Color.BLUE);
            }
            order_status.setVisibility(View.VISIBLE);
            order_rating.setVisibility(View.GONE);
            order_rating.setImageResource(order.getDrawable());
        }
        else {
            order_total.setText("Preço:");
            order_price.setText(utils.getFormattedPrice(order.getTotal_price()));
            order_status.setVisibility(View.GONE);
            order_rating.setVisibility(View.VISIBLE);
            order_rating.setImageResource(order.getDrawable());
        }

        mapView = findViewById(R.id.mapView);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.order_items_rv);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        setItems(order.getItems());
        getStoresGPS(order.getStoreIDs());

        if(order.hasCourier()) {
            Courier courier = order.getCourier();
            courier_view.setVisibility(View.VISIBLE);
            courier_name.setText(courier.getUser().getFirst_name() + " " + courier.getUser().getLast_name());
            courier_number.setText(String.valueOf(courier.getNr_deliveries()));
            courier_rating.setText(String.valueOf(courier.getAvg_rating()));
        }
        else {
            courier_view.setVisibility(View.GONE);
        }
    }

    public void setItems(List<OrderItem> dataset){
        // specify an adapter
        mAdapter = new MyAdapter2(dataset, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void getStoresGPS(final List<Integer> stores){
        String endpoint = "/stores";

        StringRequest stores_request = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_url) + endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Store>>() {
                        }.getType();
                        final List<Store> stores_lst = new Gson().fromJson(response, listType);

                        mapView.onCreate(savedState);
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                                    @Override
                                    public void onStyleLoaded(@NonNull Style style) {

                                        for(Store store : stores_lst) {
                                            if (stores.contains(store.getId())){
                                                mapboxMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(store.getGps()[0], store.getGps()[1]))
                                                        .title(store.getName()));
                                            }
                                        }

                                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                                        mapboxMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(40.6405, -8.6538), 12.0f) );

                                    }
                                });

                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });

        queue.add(stores_request);
    }
}

class MyAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderItem> mDataset;
    private Activity root_context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name;
        public TextView product_store;
        public TextView product_price;
        public TextView product_quantity;
        public ImageButton product_delete;
        public Context context;

        public ProductViewHolder(View v) {
            super(v);
            product_name = v.findViewById(R.id.product_name);
            product_store = v.findViewById(R.id.product_store);
            product_price = v.findViewById(R.id.product_price);
            product_quantity = v.findViewById(R.id.product_quantity);
            product_delete = v.findViewById(R.id.product_delete);
            context = v.getContext();
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView request_description;
        public ImageButton request_edit;
        public ImageButton request_delete;
        public Context context;

        public RequestViewHolder(View v) {
            super(v);
            request_description = v.findViewById(R.id.request_description);
            request_edit = v.findViewById(R.id.request_edit);
            request_delete = v.findViewById(R.id.request_delete);
            context = v.getContext();
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter2(List<OrderItem> myDataset, Activity context) {
        mDataset = myDataset;
        root_context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v;
        RecyclerView.ViewHolder vh;

        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
            vh = new ProductViewHolder(v);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request, parent, false);
            vh = new RequestViewHolder(v);
        }

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final OrderItem item = mDataset.get(position);

        if (holder instanceof ProductViewHolder) {
            ProductViewHolder pholder = (ProductViewHolder) holder;

            pholder.product_name.setText(item.getProduct().getName());
            pholder.product_store.setText(item.getStore_name());
            pholder.product_price.setText(utils.getFormattedPrice(item.getCumul_price()));
            pholder.product_quantity.setText(item.getQuantity() + " " + item.getProduct().getUnit());
            pholder.product_delete.setVisibility(View.GONE);
        }
        else {
            RequestViewHolder rholder = (RequestViewHolder) holder;

            rholder.request_description.setText(item.getRequest());
            rholder.request_delete.setVisibility(View.GONE);
            rholder.request_edit.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(mDataset.get(position).isProduct())
            return 1;
        else
            return 2;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
