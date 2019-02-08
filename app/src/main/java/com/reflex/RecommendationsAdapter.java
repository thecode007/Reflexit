package com.reflex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.RecommendationHolder> {


    @NonNull
    @Override
    public RecommendationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RecommendationHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View itemView;
        public RecommendationHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

        }
    }


}
