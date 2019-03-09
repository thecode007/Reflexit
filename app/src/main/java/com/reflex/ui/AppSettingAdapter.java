package com.reflex.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.core.model.WebBased;

import java.util.List;

public class AppSettingAdapter  extends RecyclerView.Adapter<AppSettingAdapter.MyViewHolder> {

    private List<App> mDataset;
    private Context context;
    AppSettingAdapter(Context context, List<App> mDataset) {
        this.mDataset = mDataset;
        this.context = context;
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView labelAppName;
        ImageView appImage;

        MyViewHolder(View view) {
            super(view);
            labelAppName = view.findViewById(R.id.label_app_name);
            appImage = view.findViewById(R.id.image_app);

        }
    }


    public Context getContext() {
        return context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AppSettingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_app, parent, false);
        AppSettingAdapter.MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        App app = mDataset.get(position);
        holder.labelAppName.setText(app.getClass().getSimpleName());
        holder.appImage.setImageResource(app.getIconResource());
        holder.itemView.setOnClickListener(view -> {
            if (app instanceof WebBased) {
                context.startActivity(new Intent(getContext(), ((WebBased) app).getActivity().getClass()));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void deleteItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
}