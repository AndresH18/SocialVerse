package com.andresd.socialverse.ui.group;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentScheduleItemBinding;
import com.andresd.socialverse.ui.group.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private List<AbstractScheduleItem> mValues = new LinkedList<>();

//    public MyScheduleRecyclerViewAdapter(@NonNull List<AbstractScheduleItem> items) {
//        mValues = items;
//    }

    public MyScheduleRecyclerViewAdapter() {

    }

    public void setValues(List<AbstractScheduleItem> values) {
        mValues = values;
        notifyDataSetChanged();
        // TODO : crear implementacion para mejor manejo del notify change del adapter
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentScheduleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDateTime.setText(mValues.get(position).getDateTime());
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mDetails.setText(mValues.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTime;
        private TextView mTitle;
        private TextView mDetails;
        private AbstractScheduleItem mItem;

        public ViewHolder(FragmentScheduleItemBinding binding) {
            super(binding.getRoot());
            mDateTime = binding.dateTimeTextView;
            mTitle = binding.titleTextView;
            mDetails = binding.detailsTextView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}