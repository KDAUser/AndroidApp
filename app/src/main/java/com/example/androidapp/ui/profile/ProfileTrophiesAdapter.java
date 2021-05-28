package com.example.androidapp.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.ArrayList;

public class ProfileTrophiesAdapter extends RecyclerView.Adapter<ProfileTrophiesAdapter.TrophiesViewHolder> {
    private ArrayList<TrophyItem> mTrophiesList;

    public static class TrophiesViewHolder extends RecyclerView.ViewHolder {
        public TextView mLocationName;
        public ImageView firstStarOn;
        public ImageView secondStarOn;
        public ImageView thirdStarOn;
        public ImageView fourthStarOn;
        public ImageView fifthStarOn;
        public ImageView firstStarOff;
        public ImageView secondStarOff;
        public ImageView thirdStarOff;
        public ImageView fourthStarOff;
        public ImageView fifthStarOff;

        public TrophiesViewHolder(View itemView) {
            super(itemView);
            mLocationName = itemView.findViewById(R.id.trophyLocationName);
            firstStarOn = itemView.findViewById(R.id.trophy_firstStarOn);
            secondStarOn = itemView.findViewById(R.id.trophy_secondStarOn);
            thirdStarOn = itemView.findViewById(R.id.trophy_thirdStarOn);
            fourthStarOn = itemView.findViewById(R.id.trophy_fourthStarOn);
            fifthStarOn = itemView.findViewById(R.id.trophy_fifthStarOn);
            firstStarOff = itemView.findViewById(R.id.trophy_firstStarOff);
            secondStarOff = itemView.findViewById(R.id.trophy_secondStarOff);
            thirdStarOff = itemView.findViewById(R.id.trophy_thirdStarOff);
            fourthStarOff = itemView.findViewById(R.id.trophy_fourthStarOff);
            fifthStarOff = itemView.findViewById(R.id.trophy_fifthStarOff);
        }
    }

    public ProfileTrophiesAdapter(ArrayList<TrophyItem> trophiesList) {
        mTrophiesList = trophiesList;
    }

    @Override
    public ProfileTrophiesAdapter.TrophiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_trophy_item,
                parent, false);
        ProfileTrophiesAdapter.TrophiesViewHolder evh = new ProfileTrophiesAdapter.TrophiesViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ProfileTrophiesAdapter.TrophiesViewHolder holder, int position) {
        TrophyItem currentItem = mTrophiesList.get(position);

        holder.mLocationName.setText(currentItem.getmLocationName());
        switch (currentItem.getmNumberOfStars()){
            case 1:
                holder.firstStarOn.setVisibility(View.VISIBLE);
                holder.secondStarOn.setVisibility(View.GONE);
                holder.thirdStarOn.setVisibility(View.GONE);
                holder.fourthStarOn.setVisibility(View.GONE);
                holder.fifthStarOn.setVisibility(View.GONE);
                holder.firstStarOff.setVisibility(View.GONE);
                holder.secondStarOff.setVisibility(View.VISIBLE);
                holder.thirdStarOff.setVisibility(View.VISIBLE);
                holder.fourthStarOff.setVisibility(View.VISIBLE);
                holder.fifthStarOff.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.firstStarOn.setVisibility(View.VISIBLE);
                holder.secondStarOn.setVisibility(View.VISIBLE);
                holder.thirdStarOn.setVisibility(View.GONE);
                holder.fourthStarOn.setVisibility(View.GONE);
                holder.fifthStarOn.setVisibility(View.GONE);
                holder.firstStarOff.setVisibility(View.GONE);
                holder.secondStarOff.setVisibility(View.GONE);
                holder.thirdStarOff.setVisibility(View.VISIBLE);
                holder.fourthStarOff.setVisibility(View.VISIBLE);
                holder.fifthStarOff.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.firstStarOn.setVisibility(View.VISIBLE);
                holder.secondStarOn.setVisibility(View.VISIBLE);
                holder.thirdStarOn.setVisibility(View.VISIBLE);
                holder.fourthStarOn.setVisibility(View.GONE);
                holder.fifthStarOn.setVisibility(View.GONE);
                holder.firstStarOff.setVisibility(View.GONE);
                holder.secondStarOff.setVisibility(View.GONE);
                holder.thirdStarOff.setVisibility(View.GONE);
                holder.fourthStarOff.setVisibility(View.VISIBLE);
                holder.fifthStarOff.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.firstStarOn.setVisibility(View.VISIBLE);
                holder.secondStarOn.setVisibility(View.VISIBLE);
                holder.thirdStarOn.setVisibility(View.VISIBLE);
                holder.fourthStarOn.setVisibility(View.VISIBLE);
                holder.fifthStarOn.setVisibility(View.GONE);
                holder.firstStarOff.setVisibility(View.GONE);
                holder.secondStarOff.setVisibility(View.GONE);
                holder.thirdStarOff.setVisibility(View.GONE);
                holder.fourthStarOff.setVisibility(View.GONE);
                holder.fifthStarOff.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.firstStarOn.setVisibility(View.VISIBLE);
                holder.secondStarOn.setVisibility(View.VISIBLE);
                holder.thirdStarOn.setVisibility(View.VISIBLE);
                holder.fourthStarOn.setVisibility(View.VISIBLE);
                holder.fifthStarOn.setVisibility(View.VISIBLE);
                holder.firstStarOff.setVisibility(View.GONE);
                holder.secondStarOff.setVisibility(View.GONE);
                holder.thirdStarOff.setVisibility(View.GONE);
                holder.fourthStarOff.setVisibility(View.GONE);
                holder.fifthStarOff.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTrophiesList.size();
    }

    public void filterList(ArrayList<TrophyItem> filteredList) {
        mTrophiesList = filteredList;
        notifyDataSetChanged();
    }
}