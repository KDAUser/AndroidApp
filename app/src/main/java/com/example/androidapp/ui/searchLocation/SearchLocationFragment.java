package com.example.androidapp.ui.searchLocation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchLocationFragment extends Fragment implements SearchLocationAdapter.OnLocationListener {

    private SearchLocationViewModel searchLocationViewModel;
    private TextView searchItemLocationName;
    private View root;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchLocationViewModel =
                new ViewModelProvider(requireActivity()).get(SearchLocationViewModel.class);
        root = inflater.inflate(R.layout.fragment_search_location, container, false);

        searchLocationViewModel.buildRecyclerView(root.findViewById(R.id.locationsView), root.getContext(),this);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        EditText editText = root.findViewById(R.id.searchLocationField);
        editText.setText(searchLocationViewModel.getFilterText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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
                    ((MainActivity) getActivity()).hideKeyboard(v);
                }
            }
        });
        GetLocationsList getLocationsList = new GetLocationsList();
        getLocationsList.execute();
        return root;
    }

    @Override
    public void onLocationClick(int position) {
        LocationItem locationItem = searchLocationViewModel.getFilteredList().get(position);
        LocationsViewModel locationsViewModel = new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        locationsViewModel.setLocationId(locationItem.getId());
        navController.navigate(R.id.nav_home);
    }

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
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}