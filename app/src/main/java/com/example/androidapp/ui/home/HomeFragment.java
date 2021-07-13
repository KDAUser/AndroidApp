package com.example.androidapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private LocationsViewModel locationsViewModel;
    private NavController navController;

    public void navigateFromHome() {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sp.getString("login", "").equals("")){
            navController.navigate(R.id.nav_login);
        }
        else if (locationsViewModel.getLocationId()!=0) {
            navController.navigate(R.id.nav_locations);
            navigationView.setCheckedItem(R.id.nav_locations);
        } else {
            navController.navigate(R.id.nav_search_location);
            navigationView.setCheckedItem(R.id.nav_search_location);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navigateFromHome();
        return root;
    }
}