package com.ams303.cityconnect.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ams303.cityconnect.MainActivity;
import com.ams303.cityconnect.R;
import com.ams303.cityconnect.data.model.User;
import com.ams303.cityconnect.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        //textView.setText("This is profile fragment");
        //type
        final Switch SwitchT = root.findViewById(R.id.Switch);
        final ImageView avatar = root.findViewById(R.id.imageView);
        final EditText Name = root.findViewById(R.id.Name);
        final EditText Email = root.findViewById(R.id.Email);
        final EditText Address = root.findViewById(R.id.Address);
        final EditText NIF = root.findViewById(R.id.NIF);
        final EditText Phone = root.findViewById(R.id.Phone);

        final Button editButton = root.findViewById(R.id.edit);
        final Button signOutButton = root.findViewById(R.id.sign_out);
        final Button deleteAccountButton = root.findViewById(R.id.delete);

        // Blocking the switch in a position
        SwitchT.setChecked(true);
        SwitchT.setClickable(false);


        MainActivity main = (MainActivity) getActivity();
        String email = main.email;

        Map<String, User> dummy_data = new HashMap<>();
        dummy_data.put("r.rosmaninho@ua.pt", new User("r.rosmaninho@ua.pt", "Rodrigo", "Rosmaninho", "Rua Sao Joao", 234567890, 911111111));
        dummy_data.put("dias.eurico@ua.pt", new User("dias.eurico@ua.pt", "Eurico", "Dias", "Rua Sao Tiago",234567890, 912222222));
        dummy_data.put("demo@ua.pt", new User("demo@ua.pt", "Utilizador", "Demonstração", "Test", 234567890, 913333333));

        if(email == null || !dummy_data.containsKey(email)){
            email = "demo@ua.pt";
        }

        User user = dummy_data.get(email);

        //avatar.setImageResource(R.drawable.avatar);
        Name.setText(user.getFirst_name() + " " + user.getLast_name());
        Email.setText(email);
        NIF.setText(String.valueOf(user.getNif()));
        Address.setText(user.getAddress());
        Phone.setText(String.valueOf(user.getPhone()));

        final Activity activity = this.getActivity();
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = activity.getSharedPreferences("CityConnect", MODE_PRIVATE);
                sp.edit().remove("email").commit();
                triggerRebirth(activity);
            }
        });

        return root;
    }

    public static void triggerRebirth(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

        Runtime.getRuntime().exit(0);
    }
}