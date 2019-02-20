package com.reflex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AvailableAppletSlider extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_applet_slider, container, false);
        return view;
    }

    public class AppletCategory{

        public String title;
        public int imageResource;

        public AppletCategory(String title, int imageResource) {
            this.title = title;
            this.imageResource = imageResource;
        }
    }


}
