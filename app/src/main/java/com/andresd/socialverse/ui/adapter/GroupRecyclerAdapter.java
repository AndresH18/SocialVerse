package com.andresd.socialverse.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder> {

    private List<String> titles;
    private List<String> details;

    public GroupRecyclerAdapter() {
        titles = new ArrayList<>(Arrays.asList(
                "Games", "Sports",
                "Minecraft", "Metroid"
        ));
        details = new ArrayList<>(Arrays.asList(
                "Grupo de juegos", "Grupo de Deportes",
                "Grupo de Minecraft", "Metroid rules!"
        ));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_cardlayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(titles.get(position));
        holder.itemDetail.setText(details.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);

        }
    }
}
