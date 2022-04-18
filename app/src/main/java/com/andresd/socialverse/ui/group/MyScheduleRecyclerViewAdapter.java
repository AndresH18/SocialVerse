package com.andresd.socialverse.ui.group;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentScheduleItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private MyScheduleRecyclerViewAdapter.OnItemListener mOnItemListener;
    private ArrayList<AbstractScheduleItem> mValues = new ArrayList<>();

//    public MyScheduleRecyclerViewAdapter(@NonNull List<AbstractScheduleItem> items) {
//        mValues = items;
//    }

    public MyScheduleRecyclerViewAdapter(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public void updateDataSet(@NonNull ArrayList<AbstractScheduleItem> values) {
        // FIXME : Implementar bien
        if (mValues.size() == values.size()) {
            mValues = values;
            notifyDataSetChanged();
        } else {

            if (mValues.size() > values.size()) {
                // items where removed
                removeItems();
            } else {
                // items where added
                insertItems();
            }
        }
         /*
         FIXME : Tener en cuenta la forma en la que se van a agregar y remover horarios, porque si se hace uno a la vez
            entonces la implementacion es mas facil.
            Para remover elementos se puede ver si el metodo de getcursor del viewholder sirve para obtener el indice, o usando la interfaz "OnItemListener"
            para asi informar que se elimino, agrego, modifico.
        */

        // TODO : crear implementacion para buscar en donde agregar el elemento, usando metodo de busqueda
        // TODO : crear implementacion para buscar en donde se quito el elemento
    }

    private void insertItems() {

    }

    private void removeItems() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentScheduleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // FIXME : FORMAT timestamp to date
        holder.mDate.setText(mValues.get(position).getTimestamp().toDate().toString());
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

            binding.editImageButton.setOnClickListener(v -> mOnItemListener.onModifyItem());
            binding.deleteImageButton.setOnClickListener(v -> mOnItemListener.onDeleteItem());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }


    public interface OnItemListener {

        void onDeleteItem();

        void onModifyItem();

    }


    // FIXME
    static class Formatter {
        /* FORMATTERS */
        private static final SimpleDateFormat formatter12 = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        private static final SimpleDateFormat formatter24 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        public static final String format12Hour(java.util.Date date) {
        /*
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date d = new Date(1647508626000L);
        System.out.println(formatter.format(d));
         */
            return formatter12.format(date);
        }

        public static final String format24Hour(java.util.Date date) {
        /*
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date d = new Date(1647508626000L);
        System.out.println(formatter.format(d));
         */
            return formatter24.format(date);
        }
    }
}