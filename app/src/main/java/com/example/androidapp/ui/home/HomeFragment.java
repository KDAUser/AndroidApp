package com.example.androidapp.ui.home;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.example.androidapp.ui.profile.ProfileViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ProfileViewModel profileViewModel;
    private LocationsViewModel locationsViewModel;
    private NavController navController;
    private Boolean isUser = false;


    public void navigateFromHome() {
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        if (!profileViewModel.isUser()) {
            //tu przejscie do fragmentu login
            navController.navigate(R.id.nav_login);
        } else if (locationsViewModel.getmLastLocationId()!=0) {
            navController.navigate(R.id.nav_locations);
        } else {
            navController.navigate(R.id.nav_search_location);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navigateFromHome();
        return root;
    }
}