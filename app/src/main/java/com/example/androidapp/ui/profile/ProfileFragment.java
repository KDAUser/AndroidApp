package com.example.androidapp.ui.profile;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profileImage = (ImageView) root.findViewById(R.id.profilePage_image);
        TextView login = (TextView) root.findViewById(R.id.profilePage_login);
        TextView email = (TextView) root.findViewById(R.id.profilePage_email);
        TextView registeredDate = (TextView) root.findViewById(R.id.profilePage_registeredDate);
        TextView description = (TextView) root.findViewById(R.id.profilePage_description);

        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        login.setText(sp.getString("login", ""));
        email.setText(sp.getString("email", ""));
        registeredDate.setText(sp.getString("registered", ""));
        description.setText(sp.getString("description", ""));
        String avatar_path = sp.getString("avatar", "");
        if(avatar_path != "") {
            profileImage.setImageBitmap(BitmapFactory.decodeFile(avatar_path));
        }

        profileViewModel.setView();

        profileViewModel.buildRecyclerView(root.findViewById(R.id.trophiesView),root.getContext());
        profileViewModel.updateTrophiesList();
        return root;
    }
}