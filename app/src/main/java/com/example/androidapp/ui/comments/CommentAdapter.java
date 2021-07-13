package com.example.androidapp.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentsViewHolder> {

    private ArrayList<CommentItem> mCommentsList;

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        public TextView mCommentAuthor;
        public TextView mCommentText;
        public TextView mCommentDate;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            mCommentAuthor = itemView.findViewById(R.id.commentAuthor);
            mCommentText = itemView.findViewById(R.id.commentText);
            mCommentDate = itemView.findViewById(R.id.commentDate);
        }
    }

    public CommentAdapter(ArrayList<CommentItem> commentsList) {
        mCommentsList = commentsList;
    }

    @NotNull
    @Override
    public CommentAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_comment_item,
                parent, false);
        return new CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentsViewHolder holder, int position) {
        CommentItem currentItem = mCommentsList.get(position);

        holder.mCommentAuthor.setText(currentItem.getCommentAuthor());
        holder.mCommentText.setText(currentItem.getCommentText());
        holder.mCommentDate.setText(currentItem.getCommentDate());
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    public void filterList(ArrayList<CommentItem> filteredList) {
        mCommentsList = filteredList;
        notifyDataSetChanged();
    }
}
