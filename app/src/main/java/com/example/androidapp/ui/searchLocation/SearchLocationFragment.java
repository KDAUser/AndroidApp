package com.example.androidapp.ui.searchLocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.androidapp.ui.locations.LocationsViewModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchLocationFragment extends Fragment implements SearchLocationAdapter.OnLocationListener {

    private SearchLocationViewModel searchLocationViewModel;
    private NavController navController;
    private LocationsViewModel locationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchLocationViewModel =
                new ViewModelProvider(requireActivity()).get(SearchLocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_location, container, false);

        searchLocationViewModel.buildRecyclerView(root.findViewById(R.id.locationsView), root.getContext(),this);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);

        EditText editText = root.findViewById(R.id.searchLocationField);
        editText.setText(searchLocationViewModel.getFilterText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                searchLocationViewModel.setFilterText(s.toString());
                searchLocationViewModel.filter(s.toString());
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    ((MainActivity) requireActivity()).hideKeyboard(v);
                }
            }
        });
        new GetLocationsList().execute();
        return root;
    }

    @Override
    public void onLocationClick(int position) {
        LocationItem locationItem = searchLocationViewModel.getFilteredList().get(position);
        SearchLocationFragment.GetLocationData getLocationData = new SearchLocationFragment.GetLocationData();

        SharedPreferences sp = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", String.valueOf(locationItem.getId())));
        params.add(new BasicNameValuePair("id_u", sp.getString("id", "")));
        getLocationData.execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    class GetLocationsList extends AsyncTask<Void, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "getLocationsList.php";

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
                    JSONArray locationsList = feedback.getJSONArray("locationsList");
                    searchLocationViewModel.createLocationsList(locationsList);
                    searchLocationViewModel.filter(searchLocationViewModel.getFilterText());
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetLocationData extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "getLocationData.php";

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
                    JSONArray locationData = feedback.getJSONArray("locationData");
                    JSONObject location = locationData.getJSONObject(0);
                    locationsViewModel.getLocationFromDB(location);
                    navController.navigate(R.id.nav_home);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}