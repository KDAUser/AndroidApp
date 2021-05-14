package com.example.androidapp.ui.comments;

public class CommentItem {
    private String mCommentAuthor;
    private String mCommentText;
    private String mCommentDate;

    public CommentItem(String mCommentAuthor, String mCommentText, String mCommentDate) {
        this.mCommentAuthor = mCommentAuthor;
        this.mCommentText = mCommentText;
        this.mCommentDate = mCommentDate;
    }

    public String getmCommentAuthor() {
        return mCommentAuthor;
    }

    public String getmCommentText() {
        return mCommentText;
    }

    public String getmCommentDate() {
        return mCommentDate;
    }
}
