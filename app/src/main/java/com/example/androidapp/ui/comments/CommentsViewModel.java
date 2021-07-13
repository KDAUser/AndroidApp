package com.example.androidapp.ui.comments;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsViewModel extends ViewModel {
    private final ArrayList<CommentItem> mCommentsList = new ArrayList<>();
    private CommentAdapter mAdapter;

    public void showComments() {
        mAdapter.filterList(mCommentsList);
    }

    public void addComment(CommentItem newItem){
        mCommentsList.add(newItem);
    }

    public void buildRecyclerView(RecyclerView mCommentsView, Context context) {
        mCommentsView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CommentAdapter(mCommentsList);

        mCommentsView.setLayoutManager(mLayoutManager);
        mCommentsView.setAdapter(mAdapter);
    }

    public void getCommentsFromDB(JSONArray comments) {
        try {
            mCommentsList.clear();
            for(int i = 0; i<comments.length(); i++) {
                JSONObject comment = comments.getJSONObject(i);
                mCommentsList.add(new CommentItem(comment.getString("author"), comment.getString("text"), comment.getString("date")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
