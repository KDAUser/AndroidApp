package com.example.androidapp.ui.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment {
    private View root;
    private SharedPreferences sp;
    private NavController navController;
    private DrawerLayout drawer;

    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button registrationButton;

    private TextView error_noCredential;
    private TextView error_wrongCredential;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        sp = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        drawer = requireActivity().findViewById(R.id.drawer_layout);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //lock menu
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide(); //hide toolbar

        loadViewElements();
        loadViewData();

        return root;
    }

    private void loadViewElements() {
        login = root.findViewById(R.id.loginPage_login);
        password = root.findViewById(R.id.loginPage_password);
        loginButton = root.findViewById(R.id.loginPage_loginButton);
        registrationButton = root.findViewById(R.id.loginPage_registrationButton);

        error_noCredential = root.findViewById(R.id.loginError_noCredential);
        error_wrongCredential = root.findViewById(R.id.loginError_wrongCredential);
    }

    private void loadViewData(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("login", login.getText().toString()));
                params.add(new BasicNameValuePair("pass", password.getText().toString()));

                new LoginUser().execute(params);
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_registration);
            }
        });

        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ((MainActivity) requireActivity()).hideKeyboard(v);
                }
            }
        };
        login.setOnFocusChangeListener(hideSoftKeyboard);
        password.setOnFocusChangeListener(hideSoftKeyboard);
    }

    @SuppressLint("StaticFieldLeak")
    class LoginUser extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "login.php";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getString(R.string.note_loadingScreen));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @SafeVarargs
        @Override
        protected final JSONObject doInBackground(List<NameValuePair>... args) {
            return new JSONParser().makeHttpRequest(link, "POST", args[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            pDialog.dismiss();
            try{
                if(feedback.getInt("success") == 1) {
                    SharedPreferences.Editor sp_editor = sp.edit();
                    sp_editor.putString("id", feedback.getString("id"));
                    sp_editor.putString("login", login.getText().toString());
                    sp_editor.putString("email", feedback.getString("email"));
                    sp_editor.putString("description", feedback.getString("description"));
                    sp_editor.putString("registered", feedback.getString("registered"));
                    if(feedback.has("avatar")) {
                        byte[] imageBytes = Base64.decode(feedback.getString("avatar"), Base64.DEFAULT);
                        Bitmap avatar = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        sp_editor.putString("avatar", ((MainActivity)requireActivity()).saveAvatarToInternalStorage(avatar));
                    }
                    sp_editor.apply();

                    ((MainActivity) requireActivity()).isUserLogin();

                    NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.nav_home);
                    navController.navigate(R.id.nav_home);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //unlock menu
                    Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show(); //show toolbar
                    Toast.makeText(requireContext(), R.string.note_successfulLogin, Toast.LENGTH_SHORT).show();
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
}
