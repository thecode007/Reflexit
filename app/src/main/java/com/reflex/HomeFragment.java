package com.reflex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.reflex.core.GlobalApplication;
import com.reflex.core.model.App;
import com.reflex.core.model.Recipe;
import com.reflex.services.AppProvider;
import com.reflex.services.os.OS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import static com.reflex.core.providers.ReflexProvider.READ_JSON_STREAM;
import static com.reflex.core.providers.ReflexProvider.UN_MUTE;
import static com.reflex.services.AppProvider.FILE_SYSTEM;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        ImageView btnApps = view.findViewById(R.id.btn_activity);

        btnApps.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AppActivity.class)));

        // Instantiate a ViewPager and a PagerAdapter.
        recyclerView = view.findViewById(R.id.recycler_recipe);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
               DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        initRecipes(recyclerView);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initRecipes(RecyclerView recyclerView) {

        if (recyclerView == null) {
            return;
        }

        ArrayList<Recipe> recipes = new ArrayList<>();

        App fileSystem = AppProvider.getInstance().getApp(FILE_SYSTEM);

        AppProvider appProvider = AppProvider.getInstance();

        try {
            JSONObject result = new JSONObject();
            InputStream inputStream  = new FileInputStream(Environment.getExternalStorageDirectory() +
                    "/reflexIt/bootstrap_trigger.json");
            fileSystem.execute(READ_JSON_STREAM, inputStream, result);
            result = result.getJSONObject("result");
            Iterator<String> iterator = result.keys();
            while (iterator.hasNext()) {
                String triggerString = iterator.next();
                JSONObject trigger = result.getJSONObject(triggerString);
                String app = trigger.getString("app");
                JSONArray actions = trigger.getJSONArray("actions");

                for (int i = 0; i < actions.length(); i++) {
                    JSONObject action = actions.getJSONObject(i);
                    String targetApp = action.getString("app");
                    String description = action.getString("description");
                    boolean isActive = action.getBoolean("active");
                    Recipe recipe = new Recipe(app, appProvider.getApp(app).getIconResource(),
                            triggerString, targetApp,appProvider.getApp(targetApp).getIconResource(), description, isActive);
                    recipes.add(recipe);
                    RecipeAdapter adapter = new RecipeAdapter(getContext(), recipes);
                    recyclerView.setAdapter(adapter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecipes(recyclerView);
    }
}
