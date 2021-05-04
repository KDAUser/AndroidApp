package com.example.androidapp.ui.searchLocation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

public class SearchLocationFragment extends Fragment {

    private SearchLocationViewModel searchLocationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchLocationViewModel =
                new ViewModelProvider(requireActivity()).get(SearchLocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_location, container, false);
        searchLocationViewModel.createExampleLocationsList();
        searchLocationViewModel.buildRecyclerView(root.findViewById(R.id.locationsView), requireActivity());

        EditText editText = root.findViewById(R.id.searchLocationField);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchLocationViewModel.filter(s.toString());
            }
        });

        return root;
    }
}