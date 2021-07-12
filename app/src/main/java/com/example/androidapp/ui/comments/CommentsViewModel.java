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
    private ArrayList<CommentItem> mCommentsList = new ArrayList<>();

    private RecyclerView mCommentsView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Boolean wasExampleListCreated = false;

    public void showComments() {
        mAdapter.filterList(mCommentsList);
    }

//    public void createExampleCommentsList() {
//        if(!wasExampleListCreated) {
//            mCommentsList = new ArrayList<>();
//            mCommentsList.add(new CommentItem("Jake", "Nice location", Calendar.getInstance().getTime().toString()));
//            mCommentsList.add(new CommentItem("Linda", "I like this location", Calendar.getInstance().getTime().toString()));
//            mCommentsList.add(new CommentItem("Conor", "Man, this location is pretty scary", Calendar.getInstance().getTime().toString()));
//            wasExampleListCreated = true;
//        }
//    }

    public void addComment(CommentItem newItem){
        mCommentsList.add(newItem);
    }

    public void buildRecyclerView(RecyclerView mCommentsView, Context context) {
        this.mCommentsView = mCommentsView;
        this.mCommentsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CommentAdapter(mCommentsList);

        this.mCommentsView.setLayoutManager(mLayoutManager);
        this.mCommentsView.setAdapter(mAdapter);
    }

    public void getCommentsFromDB(JSONArray comments) {
        try {
            mCommentsList.clear();
            for(int i = 0; i<comments.length(); i++) {
                JSONObject comment = comments.getJSONObject(i);
                mCommentsList.add(new CommentItem(comment.getString("author"), comment.getString("text"), comment.getString("date")));
            }
            //mAdapter.filterList(mCommentsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
