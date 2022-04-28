package com.andresd.socialverse.ui.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentScheduleItemBinding;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private final MyScheduleRecyclerViewAdapter.OnItemListener mOnItemListener;

    private ArrayList<AbstractScheduleItem> mValues = new ArrayList<>();


    public MyScheduleRecyclerViewAdapter(@Nullable OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public void setDataList(ArrayList<AbstractScheduleItem> itemArrayList) {
        if (itemArrayList != null) {
            this.mValues = itemArrayList;
            notifyDataSetChanged();
        }
    }

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
//        final LocalDateTime dateTime = mValues.get(position).getDateTime().toDate().toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime();
        final LocalDateTime dateTime = mValues.get(position).getDateTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        final String date = DateTimeFormatter.ofPattern("yyyy MMMM dd").format(dateTime);
        final String time = DateTimeFormatter.ofPattern("hh:mm a").format(dateTime);
        holder.mItem = mValues.get(position);
        holder.mDate.setText(date);
        holder.mTime.setText(time);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mDetails.setText(mValues.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemListener {

        void onDeleteItemClicked(View v, int index);

        void onModifyItemClicked(View v, int index);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDate;
        private final TextView mTime;
        private final TextView mTitle;
        private final TextView mDetails;
        private AbstractScheduleItem mItem;

        public ViewHolder(FragmentScheduleItemBinding binding) {
            super(binding.getRoot());
            mDate = binding.dateTextView;
            mTime = binding.timeTextView;
            mTitle = binding.titleTextView;
            mDetails = binding.detailsTextView;

            binding.editImageButton.setOnClickListener(
                    v -> mOnItemListener.onModifyItemClicked(v, getBindingAdapterPosition()));

            binding.deleteImageButton.setOnClickListener(
                    v -> mOnItemListener.onDeleteItemClicked(v, getBindingAdapterPosition()));

        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

}