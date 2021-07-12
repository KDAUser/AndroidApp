package com.example.androidapp.ui.profile;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class JFIProfile {
    private int id;
    private String login;
    private String email;
    private String description;
    private String registeredDate;
    private Bitmap avatar;
    private ArrayList<TrophyItem> mTrophiesList;

    public JFIProfile(int id, String login, String email, String description, String registeredDate, Bitmap avatar, ArrayList<TrophyItem> mTrophiesList){
        this.id = id;
        this.login = login;
        this.email = email;
        this.description = description;
        this.registeredDate = registeredDate;
        this.avatar = avatar;
        this.mTrophiesList = mTrophiesList;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getEmail() { return email; }
    public String getDescription() { return description; }
    public String getRegisteredDate() { return registeredDate; }
    public Bitmap getAvatar() { return avatar; }
    public ArrayList<TrophyItem> getTrophiesList(){return mTrophiesList; }
    public int getTrophiesListSize(){return mTrophiesList.size(); }

    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setEmail(String email) { this.email = email; }
    public void setDescription(String description) { this.description = description; }
    public void setRegisteredDate(String registeredDate) { this.registeredDate = registeredDate; }
    public void setAvatar(Bitmap avatar) { this.avatar = avatar; }
    public void setTrophiesList(ArrayList<TrophyItem> mTrophiesList) { this.mTrophiesList = mTrophiesList; }
}
