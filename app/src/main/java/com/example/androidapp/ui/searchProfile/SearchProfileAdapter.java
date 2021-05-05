package com.example.androidapp.ui.searchProfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.ArrayList;

public class SearchProfileAdapter extends RecyclerView.Adapter<SearchProfileAdapter.ProfilesViewHolder> {

    private ArrayList<ProfileItem> mProfilesList;

    public static class ProfilesViewHolder extends RecyclerView.ViewHolder {
        public TextView mSearchItemProfileName;

        public ProfilesViewHolder(View itemView) {
            super(itemView);
            mSearchItemProfileName = itemView.findViewById(R.id.searchItemProfileName);
        }
    }

    public SearchProfileAdapter(ArrayList<ProfileItem> profilesList) {
        mProfilesList = profilesList;
    }

    @Override
    public SearchProfileAdapter.ProfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_search_item,
                parent, false);
        SearchProfileAdapter.ProfilesViewHolder evh = new SearchProfileAdapter.ProfilesViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(SearchProfileAdapter.ProfilesViewHolder holder, int position) {
        ProfileItem currentItem = mProfilesList.get(position);

        holder.mSearchItemProfileName.setText(currentItem.getSearchItemProfileName());
    }

    @Override
    public int getItemCount() {
        return mProfilesList.size();
    }

    public void filterList(ArrayList<ProfileItem> filteredList) {
        mProfilesList = filteredList;
        notifyDataSetChanged();
    }
}
