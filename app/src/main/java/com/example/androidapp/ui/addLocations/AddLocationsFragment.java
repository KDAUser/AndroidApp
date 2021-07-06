package com.example.androidapp.ui.addLocations;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.JSONParser;
import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.ui.locations.JFILocation;
import com.example.androidapp.ui.locations.LocationsViewModel;
import com.example.androidapp.ui.locations.TipItem;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddLocationsFragment extends Fragment {

    private AddLocationsViewModel addLocationsViewModel;
    //private LocationManager locationManager;
    private Uri[] imageUri = new Uri[5];
    private Boolean[] imageSet = {false, false, false, false, false};
    private ImageView[] tipImage = new ImageView[5];
    private EditText[] tipText = new EditText[5];
    private int imageIndex = 0;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_add_locations, container, false);

        addLocationsViewModel = new ViewModelProvider(requireActivity()).get(AddLocationsViewModel.class);

        EditText addLocationName = (EditText) root.findViewById(R.id.addLocationName);
        tipText[0] = (EditText) root.findViewById(R.id.firstTipText);
        tipText[1] = (EditText) root.findViewById(R.id.secondTipText);
        tipText[2] = (EditText) root.findViewById(R.id.thirdTipText);
        tipText[3] = (EditText) root.findViewById(R.id.fourthTipText);
        tipText[4] = (EditText) root.findViewById(R.id.fifthTipText);
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
        Button uploadLocationButton = (Button) root.findViewById(R.id.uploadLocationButton);

        tipImage[0] = (ImageView) root.findViewById(R.id.firstTipImage);
        tipImage[1] = (ImageView) root.findViewById(R.id.secondTipImage);
        tipImage[2] = (ImageView) root.findViewById(R.id.thirdTipImage);
        tipImage[3] = (ImageView) root.findViewById(R.id.fourthTipImage);
        tipImage[4] = (ImageView) root.findViewById(R.id.fifthTipImage);

        setTipImageButtons(firstTipBrowseButton, secondTipBrowseButton, thirdTipBrowseButton, fourthTipBrowseButton, fifthTipBrowseButton);

        View.OnFocusChangeListener hideSoftKeyboard = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ((MainActivity) getActivity()).hideKeyboard(v);
                }
            }
        };

        addLocationName.setOnFocusChangeListener(hideSoftKeyboard);
        tipText[0].setOnFocusChangeListener(hideSoftKeyboard);
        tipText[1].setOnFocusChangeListener(hideSoftKeyboard);
        tipText[2].setOnFocusChangeListener(hideSoftKeyboard);
        tipText[3].setOnFocusChangeListener(hideSoftKeyboard);
        tipText[4].setOnFocusChangeListener(hideSoftKeyboard);
        editTextLatitude.setOnFocusChangeListener(hideSoftKeyboard);
        editTextLongitude.setOnFocusChangeListener(hideSoftKeyboard);

        saveWrittenValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextLongitude.getText().toString().isEmpty())
                    if(!editTextLatitude.getText().toString().isEmpty())
                        addLocationsViewModel.saveWrittenValues(Double.parseDouble(editTextLongitude.getText().toString()), Double.parseDouble(editTextLatitude.getText().toString()));
            }
        });

        saveActualValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocationsViewModel.saveActualValues(((MainActivity) getActivity()).getActualLocation());
                editTextLongitude.setText("" + Math.floor(((MainActivity) getActivity()).getActualLocation().getLongitude() * 100000) / 100000);
                editTextLatitude.setText("" + Math.floor(((MainActivity) getActivity()).getActualLocation().getLatitude() * 100000) / 100000);
            }
        });

        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation(addLocationName);
            }
        });

        uploadLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation(addLocationName);
                if (addLocationsViewModel.isLocationComplete()) {
                    new addLocation().execute(addLocationsViewModel.prepareParams(imageSet));
                    addLocationsViewModel.clearObject();
                    clearView(addLocationName, editTextLatitude, editTextLongitude);
                    setupTips();
                } else {
                    Toast.makeText(root.getContext(), "Location is not complete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupView(addLocationName, editTextLatitude, editTextLongitude);
        setupTips();
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

                try {
                    // Resize chosen image
                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri[imageIndex].getEncodedPath());
                    // Write resized image
                    FileOutputStream f_out = new FileOutputStream(imageUri[imageIndex].getEncodedPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
                    // Update view
                    imageSet[imageIndex] = true;
                    tipImage[imageIndex].setImageBitmap(bitmap);
                    tipImage[imageIndex].setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e("Tip " + (imageIndex + 1) + " image", e.getMessage(), e);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setActivityTitle(getString(R.string.addLocation_cropImageTitle))
                .setCropMenuCropButtonTitle(getString(R.string.registrationPage_cropAvatar))
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }

    public void setupView(EditText locationName, EditText latitude, EditText longitude) {
        JFILocation location = addLocationsViewModel.getmLocation();
        if (!location.getLocationName().equals("")) {
            locationName.setText(location.getLocationName());
        }
        if (location.getLocationTips() != null) {
            ArrayList<TipItem> locationTips = location.getLocationTips();
            int i = 0;
            for (TipItem locationTip : locationTips) {
                if (!locationTip.getmTipText().equals("")) {
                    tipText[i].setText(locationTip.getmTipText());
                }
                if (locationTip.getmTipImage() != null) {
                    tipImage[i].setImageBitmap(((BitmapDrawable) locationTip.getmTipImage().getDrawable()).getBitmap());
                    tipImage[i].setVisibility(View.VISIBLE);
                }
                i++;
            }
        }
        if (location.getLatitude() != 0.0) {
            latitude.setText("" + Math.floor(location.getLatitude() * 100000) / 100000);
        }
        if (location.getLongitude() != 0.0) {
            longitude.setText("" + Math.floor(location.getLongitude() * 100000) / 100000);
        }
    }

    public void clearView(EditText locationName, EditText latitude, EditText longitude) {
        JFILocation location = addLocationsViewModel.getmLocation();
        locationName.setText(location.getLocationName());
        for(EditText tiptext: tipText) {
            tiptext.setText("");
        }
        for(ImageView tipimage: tipImage) {
            tipimage.setVisibility(View.GONE);
        }
        Arrays.fill(imageSet, false);
        latitude.setText("");
        longitude.setText("");
    }

    class addLocation extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "addLocation.php";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(List<NameValuePair>... args) {
            return new JSONParser().makeHttpRequest(link, "POST", args[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            pDialog.dismiss();
            try{
                if(feedback.getInt("success") == 1) {
                    Toast.makeText(root.getContext(), feedback.getString("name"), Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupTips() {
        ArrayList<String> currentTipTextArray = new ArrayList<>();
        ArrayList<ImageView> currentTipImageArray = new ArrayList<>();
        currentTipTextArray.add(tipText[0].getText().toString());
        currentTipTextArray.add(tipText[1].getText().toString());
        currentTipTextArray.add(tipText[2].getText().toString());
        currentTipTextArray.add(tipText[3].getText().toString());
        currentTipTextArray.add(tipText[4].getText().toString());
        currentTipImageArray.add(tipImage[0]);
        currentTipImageArray.add(tipImage[1]);
        currentTipImageArray.add(tipImage[2]);
        currentTipImageArray.add(tipImage[3]);
        currentTipImageArray.add(tipImage[4]);
        addLocationsViewModel.addTips(currentTipTextArray, currentTipImageArray);
    }

    public void saveLocation(EditText locationName) {
        ArrayList<String> currentTipTextArray = new ArrayList<>();
        ArrayList<ImageView> currentTipImageArray = new ArrayList<>();
        currentTipTextArray.add(tipText[0].getText().toString());
        currentTipTextArray.add(tipText[1].getText().toString());
        currentTipTextArray.add(tipText[2].getText().toString());
        currentTipTextArray.add(tipText[3].getText().toString());
        currentTipTextArray.add(tipText[4].getText().toString());
        currentTipImageArray.add(tipImage[0]);
        currentTipImageArray.add(tipImage[1]);
        currentTipImageArray.add(tipImage[2]);
        currentTipImageArray.add(tipImage[3]);
        currentTipImageArray.add(tipImage[4]);
        addLocationsViewModel.saveLocation(locationName.getText().toString(), currentTipTextArray, currentTipImageArray);
    }
}