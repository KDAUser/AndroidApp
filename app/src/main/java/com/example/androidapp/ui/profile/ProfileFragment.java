package com.example.androidapp.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.JSONParser;
import com.example.androidapp.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private View root;
    private ProfileViewModel profileViewModel;
    private SharedPreferences sp;

    private TextView login;
    private TextView email;
    private TextView registeredDate;
    private TextView description;
    private ImageView profileImage;

    private TextView noTrophies;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        sp = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        loadViewElements();
        profileViewModel.buildRecyclerView(root.findViewById(R.id.trophiesView),root.getContext());
        if(sp.getString("id", "").equals(String.valueOf(profileViewModel.getId()))){
            loadViewDataFromSP();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", String.valueOf(profileViewModel.getId())));
            new GetProfileData().execute(params);
        } else {
            loadViewDataFromVM();
        }

        return root;
    }

    private void loadViewElements() {
        login = root.findViewById(R.id.profilePage_login);
        email = root.findViewById(R.id.profilePage_email);
        registeredDate = root.findViewById(R.id.profilePage_registeredDate);
        description = root.findViewById(R.id.profilePage_description);
        profileImage = root.findViewById(R.id.profilePage_image);

        noTrophies = root.findViewById(R.id.profilePage_noTrophies);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadViewDataFromSP() {
        login.setText(sp.getString("login", ""));
        email.setText(sp.getString("email", ""));
        registeredDate.setText(sp.getString("registered", ""));
        description.setText(sp.getString("description", ""));
        String avatar_path = sp.getString("avatar", "");
        if(!avatar_path.equals("")) {
            profileImage.setImageBitmap(BitmapFactory.decodeFile(avatar_path));
        }  else {
            profileImage.setImageBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_no_avatar)).getBitmap());
        }
        profileViewModel.updateTrophiesList();
        if(profileViewModel.getTrophiesListSize() == 0){
            noTrophies.setVisibility(View.VISIBLE);
        } else {
            noTrophies.setVisibility(View.GONE);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadViewDataFromVM(){
        login.setText(profileViewModel.getLogin());
        email.setText(profileViewModel.getEmail());
        registeredDate.setText(profileViewModel.getRegisteredDate());
        description.setText(profileViewModel.getDescription());
        if(profileViewModel.getAvatar()!=null) {
            profileImage.setImageBitmap(profileViewModel.getAvatar());
        } else {
            profileImage.setImageBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_no_avatar)).getBitmap());
        }
        profileViewModel.updateTrophiesList();
        if(profileViewModel.getTrophiesListSize() == 0){
            noTrophies.setVisibility(View.VISIBLE);
        } else {
            noTrophies.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetProfileData extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "getUserData.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @SafeVarargs
        @Override
        protected final JSONObject doInBackground(List<NameValuePair>... params) {
            return new JSONParser().makeHttpRequest(link, "POST", params[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            try{
                if(feedback.getInt("success") == 1) {
                    JSONArray userData = feedback.getJSONArray("userData");
                    JSONObject user = userData.getJSONObject(0);
                    profileViewModel.getProfileFromDB(user);

                    loadViewDataFromVM();

//                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//                    navController.navigate(R.id.nav_profile);
//                    NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
//                    navigationView.setCheckedItem(R.id.nav_profile);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}