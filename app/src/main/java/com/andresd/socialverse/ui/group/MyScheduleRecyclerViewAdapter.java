package com.andresd.socialverse.ui.group;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentScheduleItemBinding;

import java.util.ArrayList;
import java.util.TreeSet;


public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private final MyScheduleRecyclerViewAdapter.OnItemListener mOnItemListener;

    private ArrayList<AbstractScheduleItem> mValues = new ArrayList<>();


    public MyScheduleRecyclerViewAdapter(@Nullable OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public void updateDataSet(TreeSet<AbstractScheduleItem> itemTreeSet) {
        mValues.clear();

        mValues.addAll(itemTreeSet);
        notifyDataSetChanged();
    }
//    @Deprecated
//    public void updateDataSet(@NonNull Map<Integer, AbstractScheduleItem> scheduleItemMap) {
//        mValues = new ArrayList<>(scheduleItemMap.values());
//        Collections.sort(mValues);
//        notifyDataSetChanged();
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentScheduleItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // FIXME : FORMAT timestamp to date
        holder.mDate.setText(mValues.get(position).getTimestamp().toString());
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mDetails.setText(mValues.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mTime;
        private TextView mTitle;
        private TextView mDetails;
        private AbstractScheduleItem mItem;

        public ViewHolder(FragmentScheduleItemBinding binding) {
            super(binding.getRoot());
            mDate = binding.dateTextView;
            mTime = binding.timeTextView;
            mTitle = binding.titleTextView;
            mDetails = binding.detailsTextView;

            binding.editImageButton.setOnClickListener(v -> mOnItemListener.onModifyItemClicked());
            binding.deleteImageButton.setOnClickListener(v -> mOnItemListener.onDeleteItemClicked());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }


    public interface OnItemListener {

        void onDeleteItemClicked();

        void onModifyItemClicked();

    }

}