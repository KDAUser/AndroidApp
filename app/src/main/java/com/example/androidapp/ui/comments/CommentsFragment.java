package com.example.androidapp.ui.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

public class CommentsFragment extends Fragment {
    private CommentsViewModel commentsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commentsViewModel =
                new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location_comments, container, false);

        commentsViewModel.createExampleCommentsList();
        commentsViewModel.buildRecyclerView(root.findViewById(R.id.commentsView), root.getContext());
        commentsViewModel.showComments();
        return root;
    }
}
