package com.ams303.cityconnect.ui.deliveries;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ams303.cityconnect.DeliveryActivity;
import com.ams303.cityconnect.R;
import com.ams303.cityconnect.data.Order;
import com.ams303.cityconnect.lib.utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeliveriesFragment extends Fragment {

    private ConstraintLayout active_layout;
    private RecyclerView active_recyclerView;
    private RecyclerView history_recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager active_layoutManager;
    private RecyclerView.LayoutManager history_layoutManager;
    private RequestQueue queue;

    private final String active_list = "[{id:'a', items: [{product: {store_id: 1, id: 1, name: 'Ramona com ôvo', description: 'Hamburguer', base_unit: 1, unit: 'unidade', unit_price: 2.35}, quantity: 2, cumul_price: 2.35, discount: 0.1, store_name: 'Ramona', request: null}], active: true, courier: null, star_rating: null, total_price: 31.29, timestamp: '2019-12-13', address: 'Rua do Padeiro, nº 221b'}]";
    private final String history_list = "[{id:'a', items: [{product: {store_id: 4, id: 1, name: 'Leite Agros 1L', description: 'Hamburguer', base_unit: 1, unit: 'unidade', unit_price: 2.35}, quantity: 2, cumul_price: 2.35, discount: 0.1, store_name: 'Mini Mercado Farol'}, {product: {store_id: 6, id: 1, name: 'Carne Picada', description: 'Hamburguer', base_unit: 500, unit: 'gramas', unit_price: 0.93}, quantity: 600, cumul_price: 1.12, discount: 0.1, store_name: 'Flor de Aveiro'}], active: false, courier: {user: {first_name: 'Eurico', last_name: 'Dias'}, avg_rating: 4.6, nr_deliveries: 522}, star_rating: 4, total_price: 25.79, timestamp: '2019-12-10', address: 'Rua do Padeiro, nº 221b'}, {id:'a', items: [{product: {store_id: 3, id: 1, name: 'Tripa com Nestle', description: 'Hamburguer', base_unit: 1, unit: 'unidade', unit_price: 1.5}, quantity: 1, cumul_price: 1.5, discount: 0.1, store_name: 'Tripas da Praça', request: null}, {product: {store_id: 8, id: 1, name: 'Croissant com carne de vitela', description: 'Hamburguer', base_unit: 1, unit: 'unidade', unit_price: 2.35}, quantity: 1, cumul_price: 2.35, discount: 0.1, store_name: 'Oita', request: null}, {store_id: null, product: null, quantity: null, cumul_price: null, discount: null, store_name: null, request: '3.5kg de bananas da Madeira'}], active: false, courier: {user: {first_name: 'Rodrigo', last_name: 'Rosmaninho'}, avg_rating: 4.8, nr_deliveries: 873}, star_rating: 5, total_price: 6.30, timestamp: '2019-03-28', address: 'Rua do Padeiro, nº 221b'}]";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_deliveries, container, false);

        active_layout = root.findViewById(R.id.active_layout);
        active_recyclerView = root.findViewById(R.id.active_rv);
        history_recyclerView = root.findViewById(R.id.history_rv);

        active_layoutManager = new LinearLayoutManager(root.getContext());
        active_recyclerView.setLayoutManager(active_layoutManager);
        active_recyclerView.setNestedScrollingEnabled(false);
        active_recyclerView.setHasFixedSize(true);

        history_layoutManager = new LinearLayoutManager(root.getContext());
        history_recyclerView.setLayoutManager(history_layoutManager);
        history_recyclerView.setNestedScrollingEnabled(false);
        history_recyclerView.setHasFixedSize(true);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(root.getContext());

        getActiveItems();
        getHistoryItems();

        return root;
    }

    public void getActiveItems(){
        String endpoint = "/order?active=true";

        StringRequest orders_request = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_url) + endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Order>>() {
                        }.getType();
                        List<Order> orders_lst = new Gson().fromJson(response, listType);
                        setActiveItems(orders_lst);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Type listType = new TypeToken<ArrayList<Order>>() {
                }.getType();
                List<Order> orders_lst = new Gson().fromJson(active_list, listType);
                setActiveItems(orders_lst);
            }
        });

        queue.add(orders_request);
    }

    public void getHistoryItems(){
        String endpoint = "/order?active=false";

        StringRequest orders_request = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_url) + endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Order>>() {
                        }.getType();
                        List<Order> orders_lst = new Gson().fromJson(response, listType);
                        setHistoryItems(orders_lst);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Type listType = new TypeToken<ArrayList<Order>>() {
                }.getType();
                List<Order> orders_lst = new Gson().fromJson(history_list, listType);
                setHistoryItems(orders_lst);
            }
        });

        queue.add(orders_request);
    }

    public void setActiveItems(List<Order> dataset){
        if (dataset.size() == 0) {
            active_layout.setVisibility(View.GONE);
            return;
        }
        active_layout.setVisibility(View.VISIBLE);

        // specify an adapter
        mAdapter = new MyAdapter(dataset, getActivity(), true);
        active_recyclerView.setAdapter(mAdapter);
    }

    public void setHistoryItems(List<Order> dataset){
        // specify an adapter
        mAdapter = new MyAdapter(dataset, getActivity(), false);
        history_recyclerView.setAdapter(mAdapter);
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Order> mDataset;
    private Activity root_context;
    private boolean isActive;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView order_date;
        public TextView order_from;
        public TextView order_to;
        public TextView order_total;
        public TextView order_price;
        public TextView order_status;
        public ImageView order_rating;
        public ConstraintLayout order_view;

        public MyViewHolder(View v) {
            super(v);
            order_date = v.findViewById(R.id.order_date);
            order_from = v.findViewById(R.id.order_from);
            order_to = v.findViewById(R.id.order_to);
            order_total = v.findViewById(R.id.order_total_tv);
            order_price = v.findViewById(R.id.order_price);
            order_status = v.findViewById(R.id.order_status);
            order_rating = v.findViewById(R.id.order_rating);
            order_view = v.findViewById(R.id.order_view);
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Order> myDataset, Activity context, boolean active) {
        mDataset = myDataset;
        root_context = context;
        isActive = active;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Order order = mDataset.get(position);
        holder.order_date.setText(utils.getPrettyTimestamp(order.getTimestamp()));
        holder.order_from.setText(order.getStores());
        holder.order_to.setText(order.getAddress());

        if(isActive) {
            holder.order_total.setText("Subtotal:");
            holder.order_price.setText(utils.getFormattedPrice(order.getTotal_price()));
            if(order.hasCourier()) {
                holder.order_status.setText("ATIVA");
                holder.order_status.setTextColor(Color.GREEN);
            }
            else {
                holder.order_status.setText("PENDENTE");
                holder.order_status.setTextColor(Color.BLUE);
            }
            holder.order_status.setVisibility(View.VISIBLE);
            holder.order_rating.setVisibility(View.GONE);
            holder.order_rating.setImageResource(order.getDrawable());
        }
        else {
            holder.order_total.setText("Preço:");
            holder.order_price.setText(utils.getFormattedPrice(order.getTotal_price()));
            holder.order_status.setVisibility(View.GONE);
            holder.order_rating.setVisibility(View.VISIBLE);
            holder.order_rating.setImageResource(order.getDrawable());
        }

        holder.order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root_context, DeliveryActivity.class);
                intent.putExtra("order", order);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(root_context, holder.order_view, "deliveryDetails");
                root_context.startActivity(intent, options.toBundle());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}