package com.example.androidapp.ui.searchProfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.searchLocation.LocationItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchProfileFragment extends Fragment implements SearchProfileAdapter.OnProfileListener {

    private SearchProfileViewModel searchProfileViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchProfileViewModel =
                new ViewModelProvider(requireActivity()).get(SearchProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_search_profile, container, false);

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
                    ((MainActivity) getActivity()).hideKeyboard(v);
                }
            }
        });

        new GetUsersList().execute();
        return root;
    }

    @Override
    public void onProfileClick(int position) {
        ProfileItem profileItem = searchProfileViewModel.getFilteredList().get(position);
        Toast.makeText(root.getContext(), profileItem.getSearchItemProfileName(), Toast.LENGTH_SHORT).show();
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
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}