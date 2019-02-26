package com.reflex;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.reflex.core.model.App;
import com.reflex.core.model.Trigger;
import com.reflex.services.AppProvider;
import java.util.List;
import java.util.Objects;

public class AppConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_config);
        RecyclerView recyclerView = findViewById(R.id.recycler_app_triggers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        App app = AppProvider.getInstance().getApp(Objects.requireNonNull(getIntent().getExtras()).getString("app"));
        recyclerView.setAdapter(new TriggerRecyclerAdapter(this, app.getTriggers()));
        ImageView imageStartApp = findViewById(R.id.image_start_app);
        imageStartApp.setImageResource(app.getIconResource());
    }



    private class TriggerRecyclerAdapter extends RecyclerView.Adapter<TriggerRecyclerAdapter.ViewHolder> {


        private List<Trigger> mData;
        private LayoutInflater mInflater;

        // data is passed into the constructor
        TriggerRecyclerAdapter(Context context, List<Trigger> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public TriggerRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_triggers, parent, false);
            return new TriggerRecyclerAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull TriggerRecyclerAdapter.ViewHolder holder, int position) {
            holder.triggerName.setText(getItem(position).getTriggerName());
            holder.itemView.setOnClickListener(view -> {
                Trigger trigger = getItem(position);
                if (trigger.getFields() != null && trigger.getFields().size() != 0) {
                    Intent intent = new Intent(AppConfigActivity.this, TriggerConfigActivity.class);
                    intent.putExtra("trigger", trigger.getTriggerName());
                    intent.putExtra("triggerString", trigger.getTriggerString());
                    intent.putExtra("app", getIntent().
                            getExtras().
                            getString("app"));
                    startActivity(intent);
                }
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView triggerName;

            ViewHolder(View view) {
                super(view);
                triggerName = view.findViewById(R.id.text_trigger);
            }
        }

        // convenience method for getting data at click position
        Trigger getItem(int id) {
            return mData.get(id);
        }
    }


}
