package com.example.androidapp.ui.locations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.login.LoginFragment;
import com.example.androidapp.ui.searchLocation.SearchLocationFragment;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationsFragment extends Fragment {

    private LocationsViewModel locationsViewModel;
    private String[] starsOn = new String[]{"firstStarOn", "secondStarOn", "thirdStarOn", "fourthStarOn", "fifthStarOn"};
    private String[] starsOff = new String[]{"firstStarOff", "secondStarOff", "thirdStarOff", "fourthStarOff", "fifthStarOff"};
    private NavController navController;
    private View root;
    private SharedPreferences sp;

    private void prepareLocationView(View root, JFILocation location) {
        sp = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        ImageView firstStarOn = (ImageView) root.findViewById(R.id.firstStarOn);
        ImageView secondStarOn = (ImageView) root.findViewById(R.id.secondStarOn);
        ImageView thirdStarOn = (ImageView) root.findViewById(R.id.thirdStarOn);
        ImageView fourthStarOn = (ImageView) root.findViewById(R.id.fourthStarOn);
        ImageView fifthStarOn = (ImageView) root.findViewById(R.id.fifthStarOn);
        ImageView firstStarOff = (ImageView) root.findViewById(R.id.firstStarOff);
        ImageView secondStarOff = (ImageView) root.findViewById(R.id.secondStarOff);
        ImageView thirdStarOff = (ImageView) root.findViewById(R.id.thirdStarOff);
        ImageView fourthStarOff = (ImageView) root.findViewById(R.id.fourthStarOff);
        ImageView fifthStarOff = (ImageView) root.findViewById(R.id.fifthStarOff);

        Button commentsButton = (Button) root.findViewById(R.id.commentsButton);
        Button addTipButton = (Button) root.findViewById(R.id.addTipButton);
        Button checkPositionButton = (Button) root.findViewById(R.id.checkPositionButton);

        TextView locationName = (TextView) root.findViewById(R.id.locationName);
        locationName.setText(location.getLocationName());

        ArrayList<ImageView> starsOn;
        starsOn = new ArrayList<>();
        starsOn.add(firstStarOn);
        starsOn.add(secondStarOn);
        starsOn.add(thirdStarOn);
        starsOn.add(fourthStarOn);
        starsOn.add(fifthStarOn);

        ArrayList<ImageView> starsOff;
        starsOff = new ArrayList<>();
        starsOff.add(firstStarOff);
        starsOff.add(secondStarOff);
        starsOff.add(thirdStarOff);
        starsOff.add(fourthStarOff);
        starsOff.add(fifthStarOff);

        locationsViewModel.buildRecyclerView(root.findViewById(R.id.tipsView), root.getContext());

        addTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!locationsViewModel.isLocationSolved()) {
                    locationsViewModel.addTip();
                    locationsViewModel.setStars(locationsViewModel.getAreStarsOn(), starsOn, starsOff);

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("id", String.valueOf(locationsViewModel.getLocationId())));
                    params.add(new BasicNameValuePair("id_u", sp.getString("id", "")));
                    params.add(new BasicNameValuePair("updateStars", String.valueOf(locationsViewModel.getLocationNumberOfStars())));
                    new UpdateLocationProgress().execute(params);
                } else {
                    Toast.makeText(root.getContext(), R.string.locationFragment_note_alreadySolved, Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_comments);
            }
        });

        checkPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!locationsViewModel.isLocationSolved()) {
                    if (locationsViewModel.checkLocation(((MainActivity) requireActivity()).getActualLocation())) {
                        Toast.makeText(root.getContext(), R.string.locationFragment_note_locationSolved, Toast.LENGTH_SHORT).show();
                        locationsViewModel.setLocationSolved();

                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("id", String.valueOf(locationsViewModel.getLocationId())));
                        params.add(new BasicNameValuePair("id_u", sp.getString("id", "")));
                        params.add(new BasicNameValuePair("solved", "1"));
                        new UpdateLocationProgress().execute(params);
                    } else {
                        Toast.makeText(root.getContext(), R.string.locationFragment_note_badPosition, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(root.getContext(), R.string.locationFragment_note_alreadySolved, Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationsViewModel.updateTipList();
        locationsViewModel.setStars(locationsViewModel.getAreStarsOn(), starsOn, starsOff);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationsViewModel =
                new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_locations, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        if(locationsViewModel.getLocationId()==0) {
            navController.navigate(R.id.nav_home);
        }
        else {
            prepareLocationView(root, locationsViewModel.getmLocation());
        }
        return root;
    }

    @SuppressLint("StaticFieldLeak")
    class UpdateLocationProgress extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "updateLocationProgress.php";

        @Override
        protected void onPreExecute() { super.onPreExecute(); }
        @SafeVarargs
        @Override
        protected final JSONObject doInBackground(List<NameValuePair>... args) {
            return new JSONParser().makeHttpRequest(link, "POST", args[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            try{
                if(feedback.getInt("success") != 1) {
                    Toast.makeText(requireContext(), R.string.locationFragment_errorOnServerUpdate, Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}