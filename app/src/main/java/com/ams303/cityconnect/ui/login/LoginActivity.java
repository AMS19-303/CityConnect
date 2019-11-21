package com.ams303.cityconnect.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

import androidx.appcompat.app.AppCompatActivity;

import com.ams303.cityconnect.MainActivity;
import com.ams303.cityconnect.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from share SharePreference
        SharedPreferences sp = getSharedPreferences("CityConnect", MODE_PRIVATE);
        String result = sp.getString("email", null);

        setContentView(R.layout.activity_login);

        final TextView email_view = findViewById(R.id.username);
        final TextView password_view = findViewById(R.id.password);
        final ProgressBar loading_pb = findViewById(R.id.loading);
        final Button send_button = findViewById(R.id.login);

        if (result != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("email", result);
            startActivity(intent);
        }
        else {
            loading_pb.setVisibility(View.GONE);
            email_view.setVisibility(View.VISIBLE);
            password_view.setVisibility(View.VISIBLE);
            send_button.setVisibility(View.VISIBLE);
        }

        final Context context = this;
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_view.setVisibility(View.GONE);
                password_view.setVisibility(View.GONE);
                send_button.setVisibility(View.GONE);
                loading_pb.setVisibility(View.VISIBLE);

                String email = email_view.getText().toString();
                String password = password_view.getText().toString();

                if(email.length() == 0 || password.length() == 0){
                    loading_pb.setVisibility(View.GONE);
                    email_view.setVisibility(View.VISIBLE);
                    password_view.setVisibility(View.VISIBLE);
                    send_button.setVisibility(View.VISIBLE);

                    Snackbar.make(v, "Ambos os campos são obrigatórios!", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                md.update(password.getBytes(StandardCharsets.UTF_8));
                byte[] digest = md.digest();
                String hex = String.format("%064x", new BigInteger(1, digest));

                String compare = "4ed6ec75a6ba8ce79477a076b2c88543a73c43656ce962a2fe62915641a2919e";

                if (hex.equals(compare)){

                    // save data into share SharePreference
                    SharedPreferences sp = getSharedPreferences("CityConnect", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("email", email);
                    edit.apply();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else {
                    loading_pb.setVisibility(View.GONE);
                    email_view.setVisibility(View.VISIBLE);
                    password_view.setVisibility(View.VISIBLE);
                    send_button.setVisibility(View.VISIBLE);

                    Snackbar.make(v, R.string.login_failed, Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
