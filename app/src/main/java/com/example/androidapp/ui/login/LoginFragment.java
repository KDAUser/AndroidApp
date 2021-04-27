package com.example.androidapp.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;

import static androidx.core.content.ContextCompat.getSystemService;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        EditText edittext = (EditText) root.findViewById(R.id.editTextTextPersonName);
        EditText edittext2 = (EditText) root.findViewById(R.id.editTextTextPersonName2);
        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };
        edittext.setOnFocusChangeListener(hideSoftKeyboard);
        edittext2.setOnFocusChangeListener(hideSoftKeyboard);

        Button loginButton = (Button) root.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.setUser();
                navController.navigate(R.id.nav_home);
            }
        });
        if(profileViewModel.isUser())
            navController.navigate(R.id.nav_home);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
