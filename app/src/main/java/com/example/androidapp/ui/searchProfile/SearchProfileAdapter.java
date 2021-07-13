package com.example.androidapp.ui.searchProfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchProfileAdapter extends RecyclerView.Adapter<SearchProfileAdapter.ProfilesViewHolder> {

    private ArrayList<ProfileItem> mProfilesList;
    private final OnProfileListener mOnProfileListener;

    public static class ProfilesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mSearchItemProfileName;
        OnProfileListener onProfileListener;

        public ProfilesViewHolder(View itemView, OnProfileListener onProfileListener) {
            super(itemView);
            mSearchItemProfileName = itemView.findViewById(R.id.searchItemProfileName);
            this.onProfileListener = onProfileListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProfileListener.onProfileClick(getAdapterPosition());
        }
    }

    public SearchProfileAdapter(ArrayList<ProfileItem> profilesList, OnProfileListener onProfileListener) {
        mProfilesList = profilesList;
        mOnProfileListener = onProfileListener;
    }

    @NotNull
    @Override
    public SearchProfileAdapter.ProfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_search_item,
                parent, false);
        return new ProfilesViewHolder(v, mOnProfileListener);
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

    public interface OnProfileListener {
        void onProfileClick(int position);
    }
}
