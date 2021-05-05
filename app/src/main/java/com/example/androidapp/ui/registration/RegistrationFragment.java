package com.example.androidapp.ui.registration;

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

import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    private static int RESULT_LOAD_IMAGE = 1;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        EditText login = (EditText) root.findViewById(R.id.registrationPage_login);
        EditText password = (EditText) root.findViewById(R.id.registrationPage_password);
        EditText confPassword = (EditText) root.findViewById(R.id.registrationPage_confPassword);
        EditText email = (EditText) root.findViewById(R.id.registrationPage_email);
        View.OnFocusChangeListener hideSoftKeyboard = (v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        };
        login.setOnFocusChangeListener(hideSoftKeyboard);
        password.setOnFocusChangeListener(hideSoftKeyboard);
        confPassword.setOnFocusChangeListener(hideSoftKeyboard);
        email.setOnFocusChangeListener(hideSoftKeyboard);

        Button avatarButton = (Button) root.findViewById(R.id.registrationPage_avatarButton);
        avatarButton.setOnClickListener(arg0 -> {
//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
        });

        Button registerButton = (Button) root.findViewById(R.id.registrationPage_registerButton);
        registerButton.setOnClickListener(view -> {
            profileViewModel.setUser();
            navController.navigate(R.id.nav_home);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide(); //hide toolbar

        return root;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//        }
//
//
//    }
}
