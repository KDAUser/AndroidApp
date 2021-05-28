package com.example.androidapp.ui.logout;

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
import com.example.androidapp.ui.profile.ProfileViewModel;

public class LogoutFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel.logout();
        navController.navigate(R.id.nav_login);
        return root;
    }
}
