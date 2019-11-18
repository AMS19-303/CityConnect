package com.ams303.cityconnect.ui.deliveries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ams303.cityconnect.R;

public class DeliveriesFragment extends Fragment {

    private DeliveriesViewModel deliveriesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deliveriesViewModel =
                ViewModelProviders.of(this).get(DeliveriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deliveries, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        deliveriesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}