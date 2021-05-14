package com.example.androidapp.ui.comments;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CommentsViewModel extends ViewModel {
    private ArrayList<CommentItem> mCommentsList;

    private RecyclerView mCommentsView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void showComments() {
        ArrayList<CommentItem> commentsList = new ArrayList<>();
        for (CommentItem item : mCommentsList) {
            commentsList.add(item);
        }
        mAdapter.filterList(commentsList);
    }

    public void createExampleCommentsList() {
        mCommentsList = new ArrayList<>();
        mCommentsList.add(new CommentItem("Jake","Nice location", Calendar.getInstance().getTime().toString()));
        mCommentsList.add(new CommentItem("Linda","I like this location", Calendar.getInstance().getTime().toString()));
        mCommentsList.add(new CommentItem("Conor","Man, this location is pretty scary", Calendar.getInstance().getTime().toString()));
    }

    public void buildRecyclerView(RecyclerView mCommentsView, Context context) {
        mCommentsView = mCommentsView;
        mCommentsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CommentAdapter(mCommentsList);

        mCommentsView.setLayoutManager(mLayoutManager);
        mCommentsView.setAdapter(mAdapter);
    }
}
