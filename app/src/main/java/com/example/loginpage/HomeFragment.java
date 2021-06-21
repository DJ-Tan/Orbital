package com.example.loginpage;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class HomeFragment extends Fragment {

    int userid;
    String username;
    TextView home_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }
        username = "Welcome back " + username;

        home_username = view.findViewById(R.id.home_username);
        home_username.setText(username);

        return view;
    }

}
