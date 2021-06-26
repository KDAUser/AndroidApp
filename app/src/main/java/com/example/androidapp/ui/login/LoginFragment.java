package com.example.androidapp.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.JSONParser;
import com.example.androidapp.R;
import com.example.androidapp.ui.profile.ProfileViewModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;
    private NavController navController;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        TextView error_noCredential = root.findViewById(R.id.loginError_noCredential);
        TextView error_wrongCredential = root.findViewById(R.id.loginError_wrongCredential);

        EditText login = (EditText) root.findViewById(R.id.loginPage_login);
        EditText password = (EditText) root.findViewById(R.id.loginPage_password);
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

        Button loginButton = (Button) root.findViewById(R.id.loginPage_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class ConnectMySQL extends AsyncTask<String, Void, String> {
                    ProgressDialog pDialog;
                    List<NameValuePair> params;
                    String link = "http://192.168.0.3/TM/login.php";
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
                                profileViewModel.setUser();
                                navController.navigate(R.id.nav_home);
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

        if(profileViewModel.isUser()) {
            navController.navigate(R.id.nav_home);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show(); //show toolbar
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide(); //hide toolbar

        return root;
    }
}
