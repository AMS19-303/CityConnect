package com.ams303.cityconnect.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ams303.cityconnect.R;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        //textView.setText("This is profile fragment");
        final TextView Name = (TextView) root.findViewById(R.id.Name);
        final TextView Email = (TextView) root.findViewById(R.id.Email);
        final TextView Phone = (TextView) root.findViewById(R.id.Phone);
        final TextView Address = (TextView) root.findViewById(R.id.Address);
        final ImageView avatar = (ImageView) root.findViewById(R.id.imageView);
        //final Button editButton = (Button) root.findViewById(R.id.button);

        //avatar.setImageResource(R.drawable.avatar);
        Name.setText("Alexandre Lourenço");
        Email.setText("alexandre.lourenco@ua.pt");
        Phone.setText("910000000");
        Address.setText("Rua X");

        return root;
    }
}