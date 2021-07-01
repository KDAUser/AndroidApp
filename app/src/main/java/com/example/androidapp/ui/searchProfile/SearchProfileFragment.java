package com.example.androidapp.ui.searchProfile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;
import com.example.androidapp.ui.searchLocation.LocationItem;

public class SearchProfileFragment extends Fragment implements SearchProfileAdapter.OnProfileListener {

    private SearchProfileViewModel searchProfileViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchProfileViewModel =
                new ViewModelProvider(requireActivity()).get(SearchProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_search_profile, container, false);

        searchProfileViewModel.createExampleProfilesList();
        searchProfileViewModel.buildRecyclerView(root.findViewById(R.id.profilesView), root.getContext(), this);

        EditText editText = root.findViewById(R.id.searchProfileField);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchProfileViewModel.filter(s.toString());
            }
        });

        return root;
    }

    @Override
    public void onProfileClick(int position) {
        ProfileItem profileItem = searchProfileViewModel.getFilteredList().get(position);
        Toast.makeText(root.getContext(), profileItem.getSearchItemProfileName(), Toast.LENGTH_SHORT).show();
    }
}