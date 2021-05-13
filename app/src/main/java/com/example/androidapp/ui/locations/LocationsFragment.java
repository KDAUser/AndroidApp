package com.example.androidapp.ui.locations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.R;

import java.util.ArrayList;

public class LocationsFragment extends Fragment {

    private LocationsViewModel locationsViewModel;
    private String[] starsOn = new String[] {"firstStarOn", "secondStarOn", "thirdStarOn", "fourthStarOn", "fifthStarOn"};
    private String[] starsOff = new String[] {"firstStarOff", "secondStarOff", "thirdStarOff", "fourthStarOff", "fifthStarOff"};



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationsViewModel =
                new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_locations, container, false);
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

        locationsViewModel.createExampleItemList();
        locationsViewModel.buildRecyclerView(root.findViewById(R.id.tipsView), root.getContext());

        addTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationsViewModel.addTip();
                locationsViewModel.setStars(locationsViewModel.getAreStarsOn(), starsOn, starsOff);
            }
        });
        locationsViewModel.firstTip();
        locationsViewModel.setStars(locationsViewModel.getAreStarsOn(), starsOn, starsOff);
        return root;
    }
}