package com.example.androidapp.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        DrawerLayout drawer = ((AppCompatActivity)getActivity()).findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        TextView error_noCredential = root.findViewById(R.id.loginError_noCredential);
        TextView error_wrongCredential = root.findViewById(R.id.loginError_wrongCredential);

        EditText login = (EditText) root.findViewById(R.id.loginPage_login);
        EditText password = (EditText) root.findViewById(R.id.loginPage_password);
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

        Button loginButton = (Button) root.findViewById(R.id.loginPage_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class ConnectMySQL extends AsyncTask<String, Void, String> {
                    ProgressDialog pDialog;
                    List<NameValuePair> params;
                    String link = getString(R.string.server_address) + "login.php";
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
                        params.add(new BasicNameValuePair("pass", password.getText().toString()));
                    }
                    @Override
                    protected String doInBackground(String... args) {
                        jsonParser = new JSONParser();
                        JSONObject json = jsonParser.makeHttpRequest(link, "POST", params);
                        feedback = json;
                        return "1";
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        pDialog.dismiss();
                        try{
                            if(feedback.getInt("success") == 1) {
                                SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor sp_editor = sp.edit();
                                sp_editor.putString("login", login.getText().toString());
                                sp_editor.putString("email", feedback.getString("email"));
                                sp_editor.putString("description", feedback.getString("description"));
                                sp_editor.putString("registered", feedback.getString("registered"));
                                sp_editor.putString("updated", feedback.getString("updated"));
                                sp_editor.commit();

                                if(feedback.getInt("avatar") == 1) {
                                    new GetImage().execute(getString(R.string.server_address) + "avatars/" + feedback.getString("id") + ".jpg");
                                }
                                else{
                                    updateNavigationHeader();
                                }

                                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                                navigationView.setCheckedItem(R.id.nav_home);
                                navController.navigate(R.id.nav_home);
                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                                ((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
                                Toast.makeText(getContext(), "Successful login", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(feedback.getInt("error_noCredential") == 1){
                                    error_noCredential.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_noCredential.setVisibility(View.GONE);
                                }
                                if(feedback.getInt("error_wrongCredential") == 1){
                                    error_wrongCredential.setVisibility(View.VISIBLE);
                                }
                                else{
                                    error_wrongCredential.setVisibility(View.GONE);
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

        Button registrationButton = (Button) root.findViewById(R.id.loginPage_registrationButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_registration);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide(); //hide toolbar

        return root;
    }

    private void updateNavigationHeader(){
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageView profile_image = (ImageView) hView.findViewById(R.id.profile_image);
        TextView profile_login = (TextView) hView.findViewById(R.id.profile_login);
        TextView profile_email = (TextView) hView.findViewById(R.id.profile_email);

        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        profile_login.setText(sp.getString("login", ""));
        profile_email.setText(sp.getString("email", ""));
        String avatar_path = sp.getString("avatar", "");
        if(avatar_path != "") {
            profile_image.setImageBitmap(BitmapFactory.decodeFile(avatar_path));
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir("data", Context.MODE_PRIVATE);
        File file_path = new File(directory, "avatar.jpg");

        try {
            FileOutputStream fos = new FileOutputStream(file_path);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_path.getAbsolutePath();
    }

    class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... args) {
            String link = args[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(link).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor sp_editor = sp.edit();
            sp_editor.putString("avatar", saveToInternalStorage(result));
            sp_editor.commit();

            updateNavigationHeader();
        }
    }
}
