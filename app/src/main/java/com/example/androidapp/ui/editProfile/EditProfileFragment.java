package com.example.androidapp.ui.editProfile;

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

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileViewModel =
                new ViewModelProvider(this).get(EditProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        return root;
    }
}