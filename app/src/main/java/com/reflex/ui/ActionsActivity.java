package com.reflex.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.services.AppProvider;
import java.util.List;
import java.util.Objects;

public class ActionsActivity extends AppCompatActivity {

    private App app;
    private String appName;
    private String trigger;
    private String triggerString;
    private String constraints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        Bundle extras = getIntent().getExtras();

       app = AppProvider.
                getInstance().
                getApp(Objects.requireNonNull(extras).getString("targetApp"));
        RecyclerView actionRecyclerView = findViewById(R.id.recycler_actions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        actionRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        actionRecyclerView.addItemDecoration(dividerItemDecoration);
        ActionsRecyclerAdapter actionsRecyclerAdapter = new ActionsRecyclerAdapter(this, app.showActions());
        actionRecyclerView.setAdapter(actionsRecyclerAdapter);

        appName = extras.getString("app");
        trigger = extras.getString("trigger");
        triggerString = extras.getString("triggerString");
        constraints = extras.getString("constraints");
    }

    private class ActionsRecyclerAdapter extends RecyclerView.Adapter<ActionsRecyclerAdapter.ViewHolder> {


        private List<String> mData;
        private LayoutInflater mInflater;

        // data is passed into the constructor
        ActionsRecyclerAdapter(Context context, List<String> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public ActionsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_triggers, parent, false);
            return new ActionsRecyclerAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ActionsRecyclerAdapter.ViewHolder holder, int position) {
            String actionName = getItem(position);
            holder.actionName.setText(actionName);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(ActionsActivity.this, ResultActivity.class);
                intent.putExtra("app", appName);
                intent.putExtra("action", actionName);
                intent.putExtra("targetApp", app.getClass().getSimpleName());
                intent.putExtra("trigger", trigger);
                intent.putExtra("triggerString", triggerString);
                intent.putExtra("constraints", constraints);
                startActivity(intent);
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView actionName;

            ViewHolder(View view) {
                super(view);
                actionName = view.findViewById(R.id.text_trigger);
            }
        }

        // convenience method for getting data at click position
        String getItem(int id) {
            return mData.get(id);
        }
    }


}
