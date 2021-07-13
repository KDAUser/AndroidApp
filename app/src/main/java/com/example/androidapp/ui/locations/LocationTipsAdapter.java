package com.example.androidapp.ui.locations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LocationTipsAdapter extends RecyclerView.Adapter<LocationTipsAdapter.TipsViewHolder> {
    private ArrayList<TipItem> mTipsList;

    public static class TipsViewHolder extends RecyclerView.ViewHolder {
        public TextView mTipName;
        public TextView mTipText;
        public ImageView mTipImage;

        public TipsViewHolder(View itemView) {
            super(itemView);
            mTipName = itemView.findViewById(R.id.tipName);
            mTipText = itemView.findViewById(R.id.tipText);
            mTipImage = itemView.findViewById(R.id.tipImage);
        }
    }

    public LocationTipsAdapter(ArrayList<TipItem> tipsList) {
        mTipsList = tipsList;
    }

    @NotNull
    @Override
    public LocationTipsAdapter.TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_tip_item,
                parent, false);
        return new TipsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocationTipsAdapter.TipsViewHolder holder, int position) {
        TipItem currentItem = mTipsList.get(position);

        holder.mTipName.setText(currentItem.getTipName());
        holder.mTipText.setText(currentItem.getTipText());
        if (currentItem.getTipImage()!=null) {
            holder.mTipImage.setImageBitmap(currentItem.getTipImage());
        } else {
            holder.mTipImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTipsList.size();
    }

    public void filterList(ArrayList<TipItem> filteredList) {
        mTipsList = filteredList;
        notifyDataSetChanged();
    }
}
