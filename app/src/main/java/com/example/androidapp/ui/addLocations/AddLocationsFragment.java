package com.example.androidapp.ui.addLocations;

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
import com.example.androidapp.ui.editProfile.EditProfileViewModel;
import com.example.androidapp.ui.locations.LocationsViewModel;

public class AddLocationsFragment extends Fragment {

    private AddLocationsViewModel addLocationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addLocationsViewModel =
                new ViewModelProvider(this).get(AddLocationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_locations, container, false);
        final TextView textView = root.findViewById(R.id.text_add_locations);
        addLocationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
