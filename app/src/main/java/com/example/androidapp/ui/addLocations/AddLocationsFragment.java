package com.example.androidapp.ui.addLocations;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AddLocationsFragment extends Fragment {

    private AddLocationsViewModel addLocationsViewModel;
    private LocationManager locationManager;
    private Uri[] imageUri = new Uri[5];
    private Boolean[] imageSet = {false, false, false, false, false};
    private ImageView[] tipImage= new ImageView[5];
    private int imageIndex = 0;

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

        tipImage[0] = (ImageView) root.findViewById(R.id.firstTipImage);
        tipImage[1] = (ImageView) root.findViewById(R.id.secondTipImage);
        tipImage[2] = (ImageView) root.findViewById(R.id.thirdTipImage);
        tipImage[3] = (ImageView) root.findViewById(R.id.fourthTipImage);
        tipImage[4] = (ImageView) root.findViewById(R.id.fifthTipImage);

        locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);

        setTipImageButtons(firstTipBrowseButton, secondTipBrowseButton, thirdTipBrowseButton, fourthTipBrowseButton, fifthTipBrowseButton);

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
                currentTipImageArray.add(tipImage[0]);
                currentTipImageArray.add(tipImage[1]);
                currentTipImageArray.add(tipImage[2]);
                currentTipImageArray.add(tipImage[3]);
                currentTipImageArray.add(tipImage[4]);
                addLocationsViewModel.saveLocation(addLocationName.getText().toString(), currentTipTextArray, currentTipImageArray);
            }
        });

        return root;
    }

    public void setTipImageButtons(Button button1, Button button2, Button button3, Button button4, Button button5) {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex = 0;
                startCropImageActivity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex = 1;
                startCropImageActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex = 2;
                startCropImageActivity();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex = 3;
                startCropImageActivity();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex = 4;
                startCropImageActivity();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri[imageIndex] = result.getUri();

                try
                {
                    // Resize chosen image
                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri[imageIndex].getEncodedPath());
                    // Write resized image
                    FileOutputStream f_out = new FileOutputStream(imageUri[imageIndex].getEncodedPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
                    // Update view
                    imageSet[imageIndex] = true;
                    tipImage[imageIndex].setImageBitmap(bitmap);
                    tipImage[imageIndex].setVisibility(View.VISIBLE);
                    //Toast.makeText(getActivity(), imageUri.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("Tip " + (imageIndex+1) + " image", e.getMessage(), e);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setActivityTitle(getString(R.string.registrationPage_editAvatar))
                .setCropMenuCropButtonTitle(getString(R.string.registrationPage_cropAvatar))
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }
}
