package com.example.androidapp.ui.comments;

public class CommentItem {
    private final String mCommentAuthor;
    private final String mCommentText;
    private final String mCommentDate;

    public CommentItem(String mCommentAuthor, String mCommentText, String mCommentDate) {
        this.mCommentAuthor = mCommentAuthor;
        this.mCommentText = mCommentText;
        this.mCommentDate = mCommentDate;
    }

    public String getCommentAuthor() {
        return mCommentAuthor;
    }

    public String getCommentText() {
        return mCommentText;
    }

    public String getCommentDate() {
        return mCommentDate;
    }
}
