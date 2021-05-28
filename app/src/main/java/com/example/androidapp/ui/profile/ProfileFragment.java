package com.example.androidapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView description = root.findViewById(R.id.profilePage_description);
        final ImageView profileImage = root.findViewById(R.id.profilePage_image);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                description.setText(s);
            }
        });
        profileImage.setImageResource(R.drawable.ic_launcher_foreground);
        profileViewModel.createExampleItemList();
        profileViewModel.buildRecyclerView(root.findViewById(R.id.trophiesView),root.getContext());
        profileViewModel.updateTrophiesList();
        return root;
    }
}