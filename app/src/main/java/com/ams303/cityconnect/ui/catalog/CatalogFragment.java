package com.ams303.cityconnect.ui.catalog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ams303.cityconnect.CatalogActivity;
import com.ams303.cityconnect.R;
import com.ams303.cityconnect.data.Category;
import com.ams303.cityconnect.data.Store;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatalogFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue queue;

    private final String categories = "[{id:0, name:'Doçes & Pastéis', food: true}, {id:1, name:'Latícinios', food:true}, {id:2, name:'Fruta & Legumes', food:true}, {id:3, name:'Carne & Enchidos', food:true}, {id:4, name:'Peixe', food:true}, {id:5, name:'Indumentária', food:false}, {id:6, name:'Artesanato', food:false}, {id:7, name:'Outros', food:false}]";
    private final String list = "[{id:0, name:'Oita', type:'Croissanteria', photo:'https://i.ibb.co/vk3VfWb/oita.png', gps:[40.6430422, -8.6495785], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Doçes & Pastéis']},{id:1, name:'Ramona', type:'Hamburgueria', photo:'https://i.ibb.co/WNCvCQz/ramona.jpg', gps:[40.6381073, -8.6513571], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Carne & Enchidos']},{id:2, name:'Ria Pão', type:'Padaria', photo:'https://i.ibb.co/RcYc5M5/riapao.png', gps:[40.64163, -8.6572227], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Doçes & Pastéis']},{id:4, name:'Mini Mercado Farol', type:'Mercearia', photo:'https://i.ibb.co/L55cWtz/mercearia.jpg', gps:[40.6422653, -8.656447], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Carne & Enchidos', 'Peixe', 'Doçes & Pastéis', 'Latícinios', 'Fruta & Legumes', 'Outros']}, {id:5, name:'Azuleto', type:'Sapataria', photo:'https://i.ibb.co/GQK1bG0/sapataria.jpg', gps:[40.6421325, -8.6513097], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Indumentária']}, {id:6, name:'Flor de Aveiro', type:'Talho', photo:'https://i.ibb.co/wK3jcZB/talho.jpg', gps:[40.6433839, -8.6506286], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Carne & Enchidos']}, {id:7, name:'Mar Aberto', type:'Peixaria', photo:'https://i.ibb.co/bXMmrpM/peixaria.png', gps:[40.6468266, -8.6437491], business_hours:[{open: \"10:30:00\", close: \"19:30:00\", day_of_week: 2}], categories: ['Peixe']}]";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalog, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        recyclerView = (RecyclerView) root.findViewById(R.id.store_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        final View final_root = root;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(root.getContext());

        // Request a string response from the provided URL.
        StringRequest categories_request = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_url) + "/categories",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Category>>() {
                        }.getType();
                        ArrayList<Category> categories_lst = new Gson().fromJson(response, listType);
                        setCategories(categories_lst, final_root);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Type listType = new TypeToken<ArrayList<Category>>() {
                }.getType();
                ArrayList<Category> categories_lst = new Gson().fromJson(categories, listType);
                setCategories(categories_lst, final_root);
            }
        });

        queue.add(categories_request);


        getStores(null);

        return root;
    }

    public void setCategories(List<Category> categories_lst, final View root) {
        Collections.sort(categories_lst);
        final ChipGroup chipGroup = root.findViewById(R.id.categories);
        for(Category category : categories_lst) {
            Chip chip = new Chip(root.getContext());
            chip.setText(category.getName());
            chip.setChipBackgroundColorResource(R.color.bg_chip_state_list);
            chip.setCheckable(true);
            chip.setCloseIconVisible(false);
            chip.setCheckedIconVisible(false);
            chip.setId(category.getId());
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int chip_count = chipGroup.getChildCount();
                    List<Integer> categories = new ArrayList<>();
                    for(int j = 0; j < chip_count; j++){
                        Chip chip = (Chip) chipGroup.getChildAt(j);
                        if(chip.isChecked()) {
                            categories.add(chip.getId());
                        }
                    }
                    getStores(categories);
                }
            });
            chipGroup.addView(chip);
        }
    }

    public void getStores(List<Integer> categories){
        String endpoint = "/stores";
        if(categories != null && categories.size() > 0) {
            endpoint += "?" + getCategoryList(categories);
        }

        StringRequest stores_request = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_url) + endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Store>>() {
                        }.getType();
                        ArrayList<Store> stores_lst = new Gson().fromJson(response, listType);
                        setStores(stores_lst);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Type listType = new TypeToken<ArrayList<Store>>() {
                }.getType();
                ArrayList<Store> stores_lst = new Gson().fromJson(list, listType);
                setStores(stores_lst);
            }
        });

        queue.add(stores_request);
    }

    public void setStores(List<Store> dataset){
        // specify an adapter
        mAdapter = new MyAdapter(dataset, getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    private String getCategoryList(List<Integer> categories){
        StringBuilder res = new StringBuilder();
        for(Integer i : categories){
            res.append("&category=" + i);
        }
        res.deleteCharAt(0);
        return res.toString();
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Store> mDataset;
    private Activity root_context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout store_card;
        public ImageView store_image;
        public TextView store_name;
        public TextView store_type;
        public TextView store_open;
        public Context context;

        public MyViewHolder(View v) {
            super(v);
            store_card = v.findViewById(R.id.item_view);
            store_image = v.findViewById(R.id.store_image);
            store_name = v.findViewById(R.id.item_name);
            store_type = v.findViewById(R.id.item_description);
            store_open = v.findViewById(R.id.store_open);
            context = v.getContext();
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Store> myDataset, Activity context) {
        mDataset = myDataset;
        root_context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Store store = mDataset.get(position);
        holder.store_name.setText(store.getName());
        holder.store_type.setText(store.getType());
        holder.store_open.setText("ABERTO");
        Picasso.with(holder.context)
                .load(store.getPhoto())
                .centerCrop()
                .fit()
                .into(holder.store_image);
        holder.store_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(root_context, CatalogActivity.class);
                intent.putExtra("store", store);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(root_context, holder.store_card, "storeCatalog");
                root_context.startActivity(intent, options.toBundle());

                //Bundle bundle = new Bundle();
                //bundle.putParcelable("store", store);
                //Navigation.findNavController(root_context, R.id.nav_host_fragment).navigate(R.id.navigation_catalog_activity, bundle);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}