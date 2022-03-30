package com.andresd.socialverse.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.model.AbstractGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupCardRecyclerAdapter extends RecyclerView.Adapter<GroupCardRecyclerAdapter.AdapterViewHolder> {

    private List<AbstractGroup> groupCards = new ArrayList<>();

    public GroupCardRecyclerAdapter() {
    }

    public void setGroupCards(List<AbstractGroup> mGroupCards) {
        this.groupCards = mGroupCards;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlayout_group, parent, false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        AbstractGroup g = groupCards.get(position);
        holder.itemTitle.setText(g.getName());
        holder.itemDetail.setText(g.getDescription());
    }

    @Override
    public int getItemCount() {
        return groupCards.size();
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder {
        // private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDetail;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            // itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
        }
    }
}
