package com.example.androidapp.ui.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;
import com.example.androidapp.JSONParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    private ImageView avatarImage;
    private Uri imageUri;

    private String link;
    private List<NameValuePair> params;
    private String feedback;
    JSONParser jsonParser = new JSONParser();

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setActivityTitle(getString(R.string.registrationPage_editAvatar))
                .setCropMenuCropButtonTitle(getString(R.string.registrationPage_cropAvatar))
                .setAspectRatio(1, 1)
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }

    private String registerUser(String login, String email, String pass1, String pass2) {
        link = "http://192.168.0.3/TM/register.php";

        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("pass1", pass1));
        params.add(new BasicNameValuePair("pass2", pass2));

        ConnectMySQL conn = new ConnectMySQL();
        conn.execute();
        return feedback;
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
        avatarImage.setVisibility(View.GONE);
        Button avatarButton = (Button) root.findViewById(R.id.registrationPage_avatarButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropImageActivity();
            }
        });

        Button registerButton = (Button) root.findViewById(R.id.registrationPage_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String registryResult;
                registryResult = registerUser(login.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        confPassword.getText().toString());
                //profileViewModel.setUser();
                //navController.navigate(R.id.nav_home);
                //((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); //hide toolbar

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                Bitmap in = BitmapFactory.decodeFile(imageUri.getEncodedPath());
                Bitmap out = Bitmap.createScaledBitmap(in, 200, 200, false);
                avatarImage.setImageBitmap(out);
                avatarImage.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(), imageUri.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    class ConnectMySQL extends AsyncTask<String, Void, String> {

        JSONParser jsonParser;
        ProgressDialog pDialog;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(link, "POST", params);
            return json.toString();
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            params.clear();
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
