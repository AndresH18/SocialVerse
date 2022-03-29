package com.andresd.socialverse.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder> {

//    private List<String> titles;
//    private List<String> details;

    private List<Group> mGroups;

    public GroupRecyclerAdapter() {
//        titles = new ArrayList<>(Arrays.asList(
//                "Games", "Sports",
//                "Minecraft", "Metroid"
//        ));
//        details = new ArrayList<>(Arrays.asList(
//                "Grupo de juegos", "Grupo de Deportes",
//                "Grupo de Minecraft", "Metroid rules!"
//        ));

        mGroups = new ArrayList<>();
    }

    public void setGroupList(@Nullable List<Group> groups) {
//        if (groups != null) {
//            this.mGroups = groups;
//        } else {
//            this.mGroups = new ArrayList<>(0);
//        }
        this.mGroups = groups != null ? groups : new ArrayList<>(0);
        this.notifyDataSetChanged();
    }

//    public void add(String[] titles, String[] details) {
//        this.titles.addAll(Arrays.asList(titles));
//        this.details.addAll(Arrays.asList(details));
//        this.notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlayout_group, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.itemTitle.setText(titles.get(position));
//        holder.itemDetail.setText(details.get(position));
        Group g = mGroups.get(position);
        holder.itemTitle.setText(g.getName());
        holder.itemDetail.setText(g.getDetail());
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            // itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);

        }
    }
}
