package com.example.androidapp.ui.searchProfile;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchProfileFragment extends Fragment implements SearchProfileAdapter.OnProfileListener {

    private SearchProfileViewModel searchProfileViewModel;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchProfileViewModel =
                new ViewModelProvider(requireActivity()).get(SearchProfileViewModel.class);
        profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_profile, container, false);

        searchProfileViewModel.buildRecyclerView(root.findViewById(R.id.profilesView), root.getContext(), this);


        EditText editText = root.findViewById(R.id.searchProfileField);
        editText.setText(searchProfileViewModel.getFilterText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                searchProfileViewModel.setFilterText(s.toString());
                searchProfileViewModel.filter(s.toString());
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ((MainActivity) requireActivity()).hideKeyboard(v);
                }
            }
        });

        new GetUsersList().execute();
        return root;
    }

    @Override
    public void onProfileClick(int position) {
        ProfileItem profileItem = searchProfileViewModel.getFilteredList().get(position);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", String.valueOf(profileItem.getSearchItemProfileId())));
        new GetProfileData().execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    class GetUsersList extends AsyncTask<Void, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "getUsersList.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("default", "test"));
            return new JSONParser().makeHttpRequest(link, "POST", params);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            try{
                if(feedback.getInt("success") == 1) {
                    JSONArray usersList = feedback.getJSONArray("usersList");
                    searchProfileViewModel.createProfilesList(usersList);
                    searchProfileViewModel.filter(searchProfileViewModel.getFilterText());
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
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

                    if(profileViewModel.getLoginProfile().getId() == user.getInt("id")) {
                        profileViewModel.getProfileFromDB(user, profileViewModel.getLoginProfile());
                        if(!profileViewModel.isOwnProfileShow())
                            profileViewModel.toggleShownProfile();
                    } else {
                        profileViewModel.getProfileFromDB(user, profileViewModel.getSearchProfile());
                        if(profileViewModel.isOwnProfileShow())
                            profileViewModel.toggleShownProfile();
                    }

                    NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.nav_profile);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_profile);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}