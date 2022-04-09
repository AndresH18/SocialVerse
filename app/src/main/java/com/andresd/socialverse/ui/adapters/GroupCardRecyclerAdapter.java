package com.andresd.socialverse.ui.adapters;

import android.util.Log;
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

    private static final String TAG = GroupCardRecyclerAdapter.class.getSimpleName();

    private List<AbstractGroup> groupCards = new ArrayList<>();
    private OnCardItemSelectedListener onCardItemSelectedListener;

    public GroupCardRecyclerAdapter(String s) {

    }

    public GroupCardRecyclerAdapter(OnCardItemSelectedListener onCardItemSelectedListener) {
        this.onCardItemSelectedListener = onCardItemSelectedListener;
    }

    public void setGroupCards(List<AbstractGroup> groupList) {
        this.groupCards = groupList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlayout_group, parent, false);
        return new AdapterViewHolder(v, onCardItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        AbstractGroup g = groupCards.get(position);
        holder.itemTitle.setText(g.getName());
        holder.itemDetail.setText(g.getDetail());
    }

    @Override
    public int getItemCount() {
        return groupCards.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDetail;
        private OnCardItemSelectedListener onCardItemSelectedListener;

        public AdapterViewHolder(@NonNull View itemView, OnCardItemSelectedListener onCardItemSelectedListener) {
            super(itemView);
            this.onCardItemSelectedListener = onCardItemSelectedListener;
            // itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d(TAG, "onClick: selected item:" + position);
            AbstractGroup group = groupCards.get(position);
            String groupId = group.getId().getId();
            Log.d(TAG, "onClick: selected group id: " + groupId);

            onCardItemSelectedListener.onCardItemClicked(groupId);
//            MyGroupsFragmentDirections.NavigateToGroupActivity directions = MyGroupsFragmentDirections.navigateToGroupActivity(groupId);
//            try {
//                Navigation.findNavController(v).navigate(directions);
//            } catch (Exception exception) {
//                Log.e(TAG, "onClick: Failed Navigation", exception);
//            }
        }
    }

}
