package com.example.androidapp.ui.searchProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

public class SearchProfileFragment extends Fragment {

    private SearchProfileViewModel searchProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchProfileViewModel =
                new ViewModelProvider(this).get(SearchProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_search_profile);
        searchProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}