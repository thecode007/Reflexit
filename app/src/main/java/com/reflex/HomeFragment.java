package com.reflex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reflex.core.providers.App;
import com.reflex.core.providers.Trigger;
import com.reflex.model.ActionBootstrap;
import com.reflex.model.Recipe;
import com.reflex.services.AppProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        recyclerView = view.findViewById(R.id.recycler_recipe);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<AppletCategory> categories;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            categories = new ArrayList<>();
            categories.add(new AppletCategory("Security Applets", R.drawable.security));
            categories.add(new AppletCategory("Social Applets", R.drawable.social));
        }

        @Override
        public Fragment getItem(int position) {

            AvailableAppletSlider appletSlider = new AvailableAppletSlider();
            Intent intent = new Intent();
            intent.putExtra("category",categories.get(position));
            appletSlider.setArguments(intent.getExtras());
            return appletSlider;
        }

        @Override
        public int getCount() {
            return categories.size();
        }
    }

    private class AppletCategory implements Serializable {

        public String title;
        public int imageResource;

        public AppletCategory(String title, int imageResource) {
            this.title = title;
            this.imageResource = imageResource;
        }
    }


    public static class AvailableAppletSlider extends Fragment {

        private AppletCategory category;
        public AvailableAppletSlider() {
        }

        @Override
        public void setArguments(@Nullable Bundle args) {
            super.setArguments(args);
            category = (AppletCategory) args.get("category");
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_available_applet_slider, container, false);
            if (category != null){
                TextView textCategory = view.findViewById(R.id.text_category);
                textCategory.setText(category.title);
                ImageView imageView = view.findViewById(R.id.img_category);
                imageView.setImageResource(category.imageResource);
            }
            return view;
        }
    }

    public void initRecipes(RecyclerView recyclerView) {

        if (recyclerView == null) {
            return;
        }

        ArrayList<Recipe> recipes = new ArrayList<>();
        List<App> apps = AppProvider.getInstance().getAllApps();

        for (App app : apps) {

              String appName = app.getClass().getSimpleName();
              int appImageResource = app.getIconResource();

              for (Trigger trigger : app.getTriggers()) {
                  String triggerName = trigger.getTriggerName();

                  for (ActionBootstrap actionBootstrap : trigger.getBootstraps()) {
                      App targetApp = AppProvider.getInstance().getApp(actionBootstrap.getApp());
                      Recipe recipe = new Recipe(appName, appImageResource, triggerName,
                              actionBootstrap.getApp(), targetApp.getIconResource(),
                              actionBootstrap.getDescription(), actionBootstrap.isActive());
                      recipes.add(recipe);
                  }
              }
        }

        RecipeAdapter adapter = new RecipeAdapter(getContext(), recipes);
        recyclerView.setAdapter(adapter);
    }


}
