package com.example.androidapp.ui.comments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

import java.util.Calendar;

public class CommentsFragment extends Fragment {
    private CommentsViewModel commentsViewModel;
    private Dialog commentDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commentsViewModel =
                new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location_comments, container, false);
        commentDialog = new Dialog(requireContext());
        Button addCommentButton = (Button) root.findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(root);
            }
        });
        commentsViewModel.createExampleCommentsList();
        commentsViewModel.buildRecyclerView(root.findViewById(R.id.commentsView), root.getContext());
        commentsViewModel.showComments();
        return root;
    }

    private void showPopup(View v){
        commentDialog.setContentView(R.layout.add_comment_layout);
        Button acceptButton = (Button) commentDialog.findViewById(R.id.acceptCommentButton);
        EditText commentText = (EditText) commentDialog.findViewById(R.id.addCommentText);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsViewModel.addComment(new CommentItem("Me", commentText.getText().toString(), Calendar.getInstance().getTime().toString()));
                commentsViewModel.showComments();
                commentDialog.dismiss();
            }
        });
        commentDialog.show();
    }
}
