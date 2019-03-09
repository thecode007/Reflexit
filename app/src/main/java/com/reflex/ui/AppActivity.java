package com.reflex.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.services.AppProvider;
import java.util.List;

public class AppActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_activtiy);
        recyclerView = findViewById(R.id.recycler_apps);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        List<App> apps = AppProvider.getInstance().getTriggerProviders();
        AppRecyclerAdapter adapter = new AppRecyclerAdapter(this, apps);
        recyclerView.setAdapter(adapter);
    }




    private class AppRecyclerAdapter extends RecyclerView.Adapter<AppRecyclerAdapter.ViewHolder> {

        private Context context;
        private List<App> mData;
        private LayoutInflater mInflater;

        // data is passed into the constructor
        AppRecyclerAdapter(Context context, List<App> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.context = context;
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_app_grid_cell, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.appName.setText(mData.get(position).getClass().getSimpleName());
            holder.appImage.setImageResource(mData.get(position).getIconResource());
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, AppConfigActivity.class);
                intent.putExtra("app",getItem(position).getClass().getSimpleName());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(AppActivity.this).toBundle());
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        class ViewHolder extends RecyclerView.ViewHolder {
           ImageView appImage;
           TextView appName;

            ViewHolder(View view) {
                super(view);
                appImage = view.findViewById(R.id.image_service);
                appName = view.findViewById(R.id.text_app);

            }
        }

        // convenience method for getting data at click position
        App getItem(int id) {
            return mData.get(id);
        }
    }
}
