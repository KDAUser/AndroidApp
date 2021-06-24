package com.example.androidapp.ui.addLocations;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.example.androidapp.ui.locations.TipItem;

import java.util.ArrayList;

public class AddLocationsFragment extends Fragment {

    private AddLocationsViewModel addLocationsViewModel;
    private LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_locations, container, false);

        addLocationsViewModel = new ViewModelProvider(requireActivity()).get(AddLocationsViewModel.class);

        EditText addLocationName = (EditText) root.findViewById(R.id.addLocationName);
        EditText firstTipText = (EditText) root.findViewById(R.id.firstTipText);
        EditText secondTipText = (EditText) root.findViewById(R.id.secondTipText);
        EditText thirdTipText = (EditText) root.findViewById(R.id.thirdTipText);
        EditText fourthTipText = (EditText) root.findViewById(R.id.fourthTipText);
        EditText fifthTipText = (EditText) root.findViewById(R.id.fifthTipText);
        EditText editTextLongitude = (EditText) root.findViewById(R.id.editTextLongitude);
        EditText editTextLatitude = (EditText) root.findViewById(R.id.editTextLatitude);

        Button firstTipBrowseButton = (Button) root.findViewById(R.id.firstTipBrowseButton);
        Button secondTipBrowseButton = (Button) root.findViewById(R.id.secondTipBrowseButton);
        Button thirdTipBrowseButton = (Button) root.findViewById(R.id.thirdTipBrowseButton);
        Button fourthTipBrowseButton = (Button) root.findViewById(R.id.fourthTipBrowseButton);
        Button fifthTipBrowseButton = (Button) root.findViewById(R.id.fifthTipBrowseButton);
        Button saveWrittenValuesButton = (Button) root.findViewById(R.id.saveWrittenValuesButton);
        Button saveActualValuesButton = (Button) root.findViewById(R.id.saveActualValuesButton);
        Button saveLocationButton = (Button) root.findViewById(R.id.saveLocationButton);

        ImageView firstTipImage = (ImageView) root.findViewById(R.id.firstTipImage);
        ImageView secondTipImage = (ImageView) root.findViewById(R.id.secondTipImage);
        ImageView thirdTipImage = (ImageView) root.findViewById(R.id.thirdTipImage);
        ImageView fourthTipImage = (ImageView) root.findViewById(R.id.fourthTipImage);
        ImageView fifthTipImage = (ImageView) root.findViewById(R.id.fifthTipImage);

        locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);

        saveWrittenValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocationsViewModel.saveWrittenValues(Double.parseDouble(editTextLongitude.getText().toString()), Double.parseDouble(editTextLatitude.getText().toString()));
            }
        });

        saveActualValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location actualLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                addLocationsViewModel.saveActualValues(actualLocation);
                editTextLongitude.setText("" + Math.floor(actualLocation.getLongitude()*100000)/100000);
                editTextLatitude.setText("" + Math.floor(actualLocation.getLatitude()*100000)/100000);
            }
        });

        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> currentTipTextArray = new ArrayList<>();
                ArrayList<ImageView> currentTipImageArray = new ArrayList<>();
                currentTipTextArray.add(firstTipText.getText().toString());
                currentTipTextArray.add(secondTipText.getText().toString());
                currentTipTextArray.add(thirdTipText.getText().toString());
                currentTipTextArray.add(fourthTipText.getText().toString());
                currentTipTextArray.add(fifthTipText.getText().toString());
                currentTipImageArray.add(firstTipImage);
                currentTipImageArray.add(secondTipImage);
                currentTipImageArray.add(thirdTipImage);
                currentTipImageArray.add(fourthTipImage);
                currentTipImageArray.add(fifthTipImage);
                addLocationsViewModel.saveLocation(addLocationName.getText().toString(), currentTipTextArray, currentTipImageArray);
            }
        });

        return root;
    }
}
