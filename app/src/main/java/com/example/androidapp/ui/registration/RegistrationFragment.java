package com.example.androidapp.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    private static final int PICK_IMAGE = 100;
    private ImageView avatarImage;
    private Uri imageUri;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        EditText login = (EditText) root.findViewById(R.id.registrationPage_login);
        EditText password = (EditText) root.findViewById(R.id.registrationPage_password);
        EditText confPassword = (EditText) root.findViewById(R.id.registrationPage_confPassword);
        EditText email = (EditText) root.findViewById(R.id.registrationPage_email);
        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };
        login.setOnFocusChangeListener(hideSoftKeyboard);
        password.setOnFocusChangeListener(hideSoftKeyboard);
        confPassword.setOnFocusChangeListener(hideSoftKeyboard);
        email.setOnFocusChangeListener(hideSoftKeyboard);

        avatarImage = (ImageView) root.findViewById(R.id.registrationPage_avatarImage);
        Button avatarButton = (Button) root.findViewById(R.id.registrationPage_avatarButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button registerButton = (Button) root.findViewById(R.id.registrationPage_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.setUser();
                navController.navigate(R.id.nav_home);
                ((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide(); //hide toolbar

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            avatarImage.setImageURI(imageUri);
        }
    }
}
