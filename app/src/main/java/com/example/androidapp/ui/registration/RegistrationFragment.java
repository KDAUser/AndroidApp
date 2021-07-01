package com.example.androidapp.ui.registration;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;
import com.example.androidapp.JSONParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    private ImageView avatarImage;
    private Uri imageUri;
    private Boolean imageSet = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        TextView error_loginLength = root.findViewById(R.id.registerError_loginLength);
        TextView error_loginSyntax = root.findViewById(R.id.registerError_loginSyntax);
        TextView error_emailSyntax = root.findViewById(R.id.registerError_emailSyntax);
        TextView error_passwordLength = root.findViewById(R.id.registerError_passwordLength);
        TextView error_passwordsNotMatch = root.findViewById(R.id.registerError_passwordsNotMatch);
        TextView error_emailOccupied = root.findViewById(R.id.registerError_emailOccupied);
        TextView error_loginOccupied = root.findViewById(R.id.registerError_loginOccupied);
        TextView error_imageNotLoad = root.findViewById(R.id.registerError_imageNotLoad);

        EditText login = (EditText) root.findViewById(R.id.registrationPage_login);
        EditText password = (EditText) root.findViewById(R.id.registrationPage_password);
        EditText confPassword = (EditText) root.findViewById(R.id.registrationPage_confPassword);
        EditText email = (EditText) root.findViewById(R.id.registrationPage_email);
        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ((MainActivity) getActivity()).hideKeyboard(v);
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

                class ConnectMySQL extends AsyncTask<String, Void, String> {
                    ProgressDialog pDialog;
                    List<NameValuePair> params;
                    String link = getString(R.string.server_address) + "register.php";
                    JSONParser jsonParser;
                    JSONObject feedback;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        pDialog = new ProgressDialog(getContext());
                        pDialog.setMessage("Loading data. Please wait...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();

                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("login", login.getText().toString()));
                        params.add(new BasicNameValuePair("email", email.getText().toString()));
                        params.add(new BasicNameValuePair("pass1", password.getText().toString()));
                        params.add(new BasicNameValuePair("pass2", confPassword.getText().toString()));
                        if(imageSet) {
                            // Encode registration image
                            Bitmap image_in = BitmapFactory.decodeFile(imageUri.getEncodedPath());
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            image_in.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                            byte[] byte_arr = stream.toByteArray();
                            String encodedImage = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                            params.add(new BasicNameValuePair("image", encodedImage));
                        }
                    }

                    protected String doInBackground(String... args) {
                        jsonParser = new JSONParser();
                        JSONObject json = jsonParser.makeHttpRequest(link, "POST", params);
                        feedback = json;
                        return "1";
                    }

                    protected void onPostExecute(String result) {
                        pDialog.dismiss();
                        try{
                            if(feedback.getInt("success") == 1) {
                                navController.navigate(R.id.nav_login);
                                Toast.makeText(getContext(), "Successful registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(feedback.getInt("error_loginLength") == 1){
                                    error_loginLength.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_loginLength.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_loginSyntax") == 1){
                                    error_loginSyntax.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_loginSyntax.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_emailSyntax") == 1){
                                    error_emailSyntax.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_emailSyntax.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_passwordLength") == 1){
                                    error_passwordLength.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_passwordLength.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_passwordsNotMatch") == 1){
                                    error_passwordsNotMatch.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_passwordsNotMatch.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_emailOccupied") == 1){
                                    error_emailOccupied.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_emailOccupied.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_loginOccupied") == 1){
                                    error_loginOccupied.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_loginOccupied.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_imageNotLoad") == 1){
                                    error_imageNotLoad.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_imageNotLoad.setVisibility(View.GONE);
                                }
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ConnectMySQL conn = new ConnectMySQL();
                conn.execute();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                try
                {
                    // Resize chosen image
                    Bitmap bm_in = BitmapFactory.decodeFile(imageUri.getEncodedPath());
                    Bitmap bm_out = Bitmap.createScaledBitmap(bm_in, 200, 200, false);
                    // Write resized image
                    FileOutputStream f_out = new FileOutputStream(imageUri.getEncodedPath());
                    bm_out.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
                    // Update view
                    imageSet = true;
                    avatarImage.setImageBitmap(bm_out);
                    avatarImage.setVisibility(View.VISIBLE);
                    //Toast.makeText(getActivity(), imageUri.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("Registration image", e.getMessage(), e);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setActivityTitle(getString(R.string.registrationPage_editAvatar))
                .setCropMenuCropButtonTitle(getString(R.string.registrationPage_cropAvatar))
                .setAspectRatio(1, 1)
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }
}
