package com.example.androidapp.ui.editProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.login.LoginFragment;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    private View root;

    private EditText login;
    private ImageView avatar;
    private Button avatarButton;
    private EditText description;
    private EditText email;
    private EditText password;
    private EditText confPassword;
    private Button saveButton;

    private TextView error_loginLength;
    private TextView error_loginSyntax;
    private TextView error_emailSyntax;
    private TextView error_passwordLength;
    private TextView error_passwordsNotMatch;
    private TextView error_emailOccupied;
    private TextView error_loginOccupied;
    private TextView error_imageNotLoad;

    private Uri newAvatarUri;
    private Boolean newAvatarSet = false;
    private EditProfileViewModel editProfileViewModel;
    private SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileViewModel =
                new ViewModelProvider(this).get(EditProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        loadViewElements();
        loadViewData();

        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                newAvatarUri = result.getUri();

                try
                {
                    // Resize chosen image
                    Bitmap bm_in = BitmapFactory.decodeFile(newAvatarUri.getEncodedPath());
                    Bitmap bm_out = Bitmap.createScaledBitmap(bm_in, 200, 200, false);
                    // Write resized image
                    FileOutputStream f_out = new FileOutputStream(newAvatarUri.getEncodedPath());
                    bm_out.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
                    // Update view
                    newAvatarSet = true;
                    avatar.setImageBitmap(bm_out);
                }
                catch (Exception e)
                {
                    Log.e("Edit Profile image", e.getMessage(), e);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void loadViewElements(){
        login = (EditText) root.findViewById(R.id.editProfilePage_login);
        avatar = (ImageView) root.findViewById(R.id.editProfilePage_avatar);
        avatarButton = (Button) root.findViewById(R.id.editProfilePage_avatarButton);
        description = (EditText) root.findViewById(R.id.editProfilePage_description);
        email = (EditText) root.findViewById(R.id.editProfilePage_email);
        password = (EditText) root.findViewById(R.id.editProfilePage_password);
        confPassword = (EditText) root.findViewById(R.id.editProfilePage_confPassword);
        description = (EditText) root.findViewById(R.id.editProfilePage_description);
        saveButton = (Button) root.findViewById(R.id.editProfilePage_saveButton);

        error_loginLength = (TextView) root.findViewById(R.id.editProfileError_loginLength);
        error_loginSyntax = (TextView) root.findViewById(R.id.editProfileError_loginSyntax);
        error_emailSyntax = (TextView) root.findViewById(R.id.editProfileError_emailSyntax);
        error_passwordLength = (TextView) root.findViewById(R.id.editProfileError_passwordLength);
        error_passwordsNotMatch = (TextView) root.findViewById(R.id.editProfileError_passwordsNotMatch);
        error_emailOccupied = (TextView) root.findViewById(R.id.editProfileError_emailOccupied);
        error_loginOccupied = (TextView) root.findViewById(R.id.editProfileError_loginOccupied);
        error_imageNotLoad = (TextView) root.findViewById(R.id.editProfileError_imageNotLoad);
    }

    private void loadViewData(){
        login.setHint(sp.getString("login", ""));
        String avatar_path = sp.getString("avatar", "");
        if(avatar_path != "") {
            avatar.setImageBitmap(BitmapFactory.decodeFile(avatar_path));
        }
        description.setText(sp.getString("description", ""));
        email.setHint(sp.getString("email", ""));

        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropImageActivity();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ((MainActivity)getActivity()).hideKeyboard(v);
                }
            }
        };
        login.setOnFocusChangeListener(hideSoftKeyboard);
        description.setOnFocusChangeListener(hideSoftKeyboard);
        email.setOnFocusChangeListener(hideSoftKeyboard);
        password.setOnFocusChangeListener(hideSoftKeyboard);
        confPassword.setOnFocusChangeListener(hideSoftKeyboard);
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setActivityTitle(getString(R.string.registrationPage_editAvatar))
                .setCropMenuCropButtonTitle(getString(R.string.registrationPage_cropAvatar))
                .setAspectRatio(1, 1)
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }

    private void saveChanges(){
        String loginStr = login.getText().toString();
        String descriptionStr = description.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String confPasswordStr = confPassword.getText().toString();


        List<NameValuePair>  params = new ArrayList<NameValuePair>();
        if(!loginStr.equals(sp.getString("login", "")) & !loginStr.equals(""))
            params.add(new BasicNameValuePair("login", loginStr));
        if(!descriptionStr.equals(sp.getString("description", "")))
            params.add(new BasicNameValuePair("description", descriptionStr));
        if(!emailStr.equals(sp.getString("email", "")) & !emailStr.equals(""))
            params.add(new BasicNameValuePair("email", emailStr));
        if(!passwordStr.equals("")) {
            params.add(new BasicNameValuePair("pass1", passwordStr));
            params.add(new BasicNameValuePair("pass2", confPasswordStr));
        }
        if(newAvatarSet) {
            // Encode registration image
            Bitmap image_in = BitmapFactory.decodeFile(newAvatarUri.getEncodedPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image_in.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            String encodedImage = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            params.add(new BasicNameValuePair("image", encodedImage));
        }

        if(params.isEmpty()){
            Toast.makeText(getContext(), "No changes provided", Toast.LENGTH_SHORT).show();
        }
        else{
            new updateUserProfile().execute(params);
            Toast.makeText(getContext(), "Changes detected", Toast.LENGTH_SHORT).show();
        }
    }

    class updateUserProfile extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private String link = getString(R.string.server_address) + "editProfile.php";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(List<NameValuePair>... args) {
            return new JSONParser().makeHttpRequest(link, "POST", args[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            pDialog.dismiss();
            try{
                if(feedback.getInt("success") == 1) {
                    Toast.makeText(getContext(), "Proper PHP script feedback", Toast.LENGTH_SHORT).show();
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
}