package com.example.androidapp.ui.searchLocation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.google.android.material.navigation.NavigationView;

public class SearchLocationFragment extends Fragment implements SearchLocationAdapter.OnLocationListener {

    private SearchLocationViewModel searchLocationViewModel;
    private TextView searchItemLocationName;
    private View root;
    private NavController navController;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchLocationViewModel =
                new ViewModelProvider(requireActivity()).get(SearchLocationViewModel.class);
        root = inflater.inflate(R.layout.fragment_search_location, container, false);
        searchLocationViewModel.createExampleLocationsList();
        searchLocationViewModel.buildRecyclerView(root.findViewById(R.id.locationsView), root.getContext(),this);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

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

    @Override
    public void onLocationClick(int position) {
        LocationItem locationItem = searchLocationViewModel.getFilteredList().get(position);
        LocationsViewModel locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        locationsViewModel.setLocationName(locationItem.getSearchItemLocationName());

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navController.navigate(R.id.nav_locations);
        navigationView.setCheckedItem(R.id.nav_locations);
    }
}