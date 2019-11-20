package com.ams303.cityconnect.ui.catalog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ams303.cityconnect.R;
import com.ams303.cityconnect.data.Shop;
import com.ams303.cityconnect.lib.utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalog, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        //textView.setText("This is catalog fragment");

        recyclerView = (RecyclerView) root.findViewById(R.id.shop_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        String list = "[{name:'Oita', type:'Croissanteria', image_url:'https://i.ibb.co/vk3VfWb/oita.png'},{name:'Ramona', type:'Hamburgeria', image_url:'https://i.ibb.co/WNCvCQz/ramona.jpg'},{name:'Ria Pão', type:'Padaria', image_url:'https://i.ibb.co/RcYc5M5/riapao.png'},{name:'Tripas da Praça', type:'Confeitaria', image_url:'https://i.ibb.co/HnTG92V/tripaspra-a.jpg'},{name:'Mini Mercado Farol', type:'Mercearia', image_url:'https://i.ibb.co/L55cWtz/mercearia.jpg'}, {name:'Azuleto', type:'Sapataria', image_url:'https://i.ibb.co/GQK1bG0/sapataria.jpg'}, {name:'Flor de Aveiro', type:'Talho', image_url:'https://i.ibb.co/wK3jcZB/talho.jpg'}, {name:'Mar Aberto', type:'Peixaria', image_url:'https://i.ibb.co/bXMmrpM/peixaria.png'}]";

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Shop>>() {}.getType();
        ArrayList<Shop> dataset = new Gson().fromJson(list, listType);

        // specify an adapter
        mAdapter = new MyAdapter(dataset);
        recyclerView.setAdapter(mAdapter);


        return root;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Shop> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView shop_image;
        public TextView shop_name;
        public TextView shop_type;
        public TextView shop_open;
        public Context context;

        public MyViewHolder(View v) {
            super(v);
            shop_image = v.findViewById(R.id.shop_image);
            shop_name = v.findViewById(R.id.shop_name);
            shop_type = v.findViewById(R.id.shop_type);
            shop_open = v.findViewById(R.id.shop_open);
            context = v.getContext();
            // https://codinginflow.com/tutorials/android/recyclerview-cardview/part-2-adapter
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Shop> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = mDataset.get(position);
        holder.shop_name.setText(shop.getName());
        holder.shop_type.setText(shop.getType());
        holder.shop_open.setText("ABERTO");
        Picasso.with(holder.context)
                .load(shop.getImage_url())
                .centerCrop()
                .fit()
                .into(holder.shop_image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}