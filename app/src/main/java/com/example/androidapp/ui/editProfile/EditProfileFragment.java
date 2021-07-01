package com.example.androidapp.ui.editProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    private ImageView avatarImage;
    private Uri imageUri;
    private Boolean imageSet = false;

    private EditProfileViewModel editProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileViewModel =
                new ViewModelProvider(this).get(EditProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Button changeAvatarButton = (Button) root.findViewById(R.id.editProfilePage_avatarButton);
        //avatarImage = (ImageView) root.findViewById(R.id.editAvatarImage);

        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropImageActivity();
            }
        });


        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                try
                {
                    // Resize chosen image
                    Bitmap bm_in = BitmapFactory.decodeFile(imageUri.getEncodedPath());
                    Bitmap bm_out = Bitmap.createScaledBitmap(bm_in, 200, 200, false);
                    // Write resized image
                    FileOutputStream f_out = new FileOutputStream(imageUri.getEncodedPath());
                    bm_out.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
                    // Update view
                    imageSet = true;
                    avatarImage.setImageBitmap(bm_out);
                    avatarImage.setVisibility(View.VISIBLE);
                    //Toast.makeText(getActivity(), imageUri.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("Edit Profile image", e.getMessage(), e);
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
                .setAspectRatio(1, 1)
                .setMinCropResultSize(200, 200)
                .start(getContext(), this);
    }
}