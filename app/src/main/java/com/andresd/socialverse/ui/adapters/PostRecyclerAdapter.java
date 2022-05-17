package com.andresd.socialverse.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractPost;
import com.andresd.socialverse.databinding.FragmentPostItemBinding;

import java.util.ArrayList;
import java.util.List;

public final class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    private static final String TAG = PostRecyclerAdapter.class.getName();

    private List<AbstractPost> mPostList;

    public PostRecyclerAdapter() {
        this.mPostList = new ArrayList<>();
    }

    public final void setData(@Nullable List<AbstractPost> abstractPostList) {
        if (abstractPostList != null) {
            this.mPostList = abstractPostList;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentPostItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AbstractPost post = mPostList.get(position);

        holder.mTitle.setText(post.getTitle());
        holder.mMessage.setText(post.getMessage());
        holder.mOwner.setText(post.getOwner());
        holder.mPost = post;

    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImage;
        private final TextView mTitle;
        private final TextView mMessage;
        private final TextView mOwner;
        private AbstractPost mPost;

        public ViewHolder(FragmentPostItemBinding binding) {
            super(binding.getRoot());
            this.mImage = binding.universityImageView;
            this.mTitle = binding.titleEditText;
            this.mMessage = binding.messageTextView;
            this.mOwner = binding.nameTextView;
        }

    }
}
