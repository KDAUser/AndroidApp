package com.example.androidapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.example.androidapp.ui.login.LoginViewModel;
import com.example.androidapp.ui.profile.ProfileViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LocationsViewModel locationsViewModel;
    private NavController navController;

    public void navigateFromHome() {
        locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        if(!isUserLogin()){
            navController.navigate(R.id.nav_login);
        }
        else if (locationsViewModel.getmLastLocationId()!=0) {
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

    public boolean isUserLogin(){
        SharedPreferences sp = getActivity().getSharedPreferences("JustFindIt", Context.MODE_PRIVATE);
        if(sp.getString("login", "") != ""){
            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            View hView = navigationView.getHeaderView(0);
            ImageView profile_image = (ImageView) hView.findViewById(R.id.profile_image);
            TextView profile_login = (TextView) hView.findViewById(R.id.profile_login);
            TextView profile_email = (TextView) hView.findViewById(R.id.profile_email);
            profile_login.setText(sp.getString("login", ""));
            profile_email.setText(sp.getString("email", ""));
            String avatar_path = sp.getString("avatar", "");
            if(avatar_path != "") {
                profile_image.setImageBitmap(BitmapFactory.decodeFile(avatar_path));
            }

            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_home);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
            return true;
        }
        else{
            return false;
        }
    }
}