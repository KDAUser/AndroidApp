package com.example.androidapp.ui.logout;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;
import com.google.android.material.navigation.NavigationView;

public class LogoutFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        clearSharedPreferences();
        updateNavigationHeader();

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_login);

        return root;
    }

    public void clearSharedPreferences(){
        SharedPreferences sp = getActivity().getSharedPreferences("JustFindIt", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();
        sp_editor.clear();
        sp_editor.commit();
    }

    public void updateNavigationHeader(){
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageView profile_image = (ImageView) hView.findViewById(R.id.profile_image);
        TextView profile_login = (TextView) hView.findViewById(R.id.profile_login);
        TextView profile_email = (TextView) hView.findViewById(R.id.profile_email);

        profile_login.setText(R.string.navHeader_noUser_login);
        profile_email.setText(R.string.navHeader_noUser_email);
        profile_image.setImageResource(R.drawable.ic_no_avatar);
    }
}
