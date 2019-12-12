package com.ams303.cityconnect.ui.cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ams303.cityconnect.R;
import com.ams303.cityconnect.data.Cart;
import com.ams303.cityconnect.data.CartItem;
import com.ams303.cityconnect.data.Product;
import com.ams303.cityconnect.data.Request;
import com.ams303.cityconnect.lib.utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView empty;
    private Button add_request;
    private TextView price;
    private EditText date;
    private EditText address;
    private EditText comment;
    private Button reset;
    private Button order;
    private Cart cart;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_cart, container, false);

        fillPage();

        return root;
    }

    public void fillPage(){
        final View final_root = root;
        empty = root.findViewById(R.id.empty_tv);
        recyclerView = root.findViewById(R.id.cart_items_rv);
        add_request = root.findViewById(R.id.add_request_btn);
        price = root.findViewById(R.id.price_tv);
        date = root.findViewById(R.id.date_et);
        address = root.findViewById(R.id.address_et);
        comment = root.findViewById(R.id.comment_et);
        reset = root.findViewById(R.id.reset_btn);
        order = root.findViewById(R.id.order_btn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(final_root, "Carrinho apagado com sucesso!", Snackbar.LENGTH_SHORT)
                        .show();
                Cart.resetCart(final_root.getContext());
                recyclerView.clearOnChildAttachStateChangeListeners();
                fillPage();
            }
        });

        // TODO
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(final_root, "Encomenda agendada com sucesso!", Snackbar.LENGTH_SHORT)
                        .show();
                Cart.resetCart(final_root.getContext());
                recyclerView.clearOnChildAttachStateChangeListeners();
                fillPage();
            }
        });

        add_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRequestPopup(cart, -1, root, null);
            }
        });

        cart = Cart.getCart(root.getContext());

        setEmptyVisibility();
        price.setText(utils.getFormattedPrice(cart.getSubtotal()));
        // TODO
        String date_str = (cart.getDeliveryDate() != null) ? cart.getDeliveryDate().toString() : "null";
        date.setText(date_str);
        address.setText(cart.getAddress());
        comment.setText(cart.getComment());

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        final Cart final_cart = cart;
        final TextView final_price = price;
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                final_price.setText(utils.getFormattedPrice(final_cart.getSubtotal()));
                setEmptyVisibility();
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                final_price.setText(utils.getFormattedPrice(final_cart.getSubtotal()));
                setEmptyVisibility();
            }
        });
        setItems(cart.getItems());
    }

    @Override
    public void onStop() {
        super.onStop();
        cart.setAddress(address.getText().toString());
        cart.setComment(comment.getText().toString());
        cart.saveCart(root.getContext());
    }

    public void setEmptyVisibility() {
        if (cart.getSize() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.GONE);
        }
    }

    public void setItems(List<CartItem> dataset){
        // specify an adapter
        mAdapter = new MyAdapter(dataset, getActivity(), root, cart);
        recyclerView.setAdapter(mAdapter);
    }

    public static void showRequestPopup(final Cart cart, final int position, final View view, final RecyclerView.Adapter adapter) {
        String save_str = "Adicionar";
        String title_str = "Adicionar Pedido";
        String success_str = "Adicionado ao carrinho com sucesso!";
        if (position >= 0) {
            save_str = "Salvar";
            title_str = "Editar Pedido";
            success_str = "Pedido alterado com sucesso!";
        }

        final String final_success_str = success_str;
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.dialogAlertTheme));
        builder.setView(R.layout.dialog_add_request)
                // Add action buttons
                .setPositiveButton(save_str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText description = ((AlertDialog) dialog).findViewById(R.id.description);

                        if(description.getText().toString().length() != 0) {
                            if (position >= 0) {
                                cart.getItems().get(position).setName(description.getText().toString());
                                adapter.notifyDataSetChanged();
                            } else {
                                Request request = new Request(description.getText().toString());
                                cart.getItems().add(request);
                            }

                            cart.saveCart(view.getContext());

                            Snackbar.make(view, final_success_str, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.setTitle(title_str);
        AlertDialog dialog = builder.create();
        dialog.show();

        if(position >= 0) {
            EditText description = dialog.findViewById(R.id.description);
            description.setText(cart.getItems().get(position).getName());
        }
    }
}

class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CartItem> mDataset;
    private Activity root_context;
    private View root_view;
    private Cart cart;

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
    public MyAdapter(List<CartItem> myDataset, Activity context, View root, Cart c) {
        mDataset = myDataset;
        root_context = context;
        root_view = root;
        cart = c;
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
        final CartItem item = mDataset.get(position);

        View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.removeItem(root_context, position);
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        };

        if (holder instanceof ProductViewHolder) {
            ProductViewHolder pholder = (ProductViewHolder) holder;
            Product product = (Product) item;

            pholder.product_name.setText(product.getName());
            pholder.product_store.setText(product.getStore());
            pholder.product_price.setText(utils.getFormattedPrice(product.getPrice()));
            pholder.product_quantity.setText(product.getQuantity() + " " + product.getUnit());
            pholder.product_delete.setOnClickListener(deleteListener);
        }
        else {
            RequestViewHolder rholder = (RequestViewHolder) holder;
            Request request = (Request) item;
            final MyAdapter adapter = this;

            rholder.request_description.setText(request.getName());
            rholder.request_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartFragment.showRequestPopup(cart, position, root_view, adapter);
                }
            });
            rholder.request_delete.setOnClickListener(deleteListener);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(mDataset.get(position) instanceof Product)
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