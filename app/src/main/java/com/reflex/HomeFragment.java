package com.reflex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflex.model.TriggerBootstrap;

import java.io.IOException;
import java.io.InputStream;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        InputStream i = null;
        try {
            i = getContext().getAssets().open("bootstrap-trigger.json");
            ObjectMapper objectMapper = new ObjectMapper();
            TriggerBootstrap triggerBootstrap = objectMapper.readValue(i, TriggerBootstrap.class);
            Toast.makeText(getContext(), triggerBootstrap.getActions().get(0).getConstraints().toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}
