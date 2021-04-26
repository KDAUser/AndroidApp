package com.example.androidapp.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private boolean isUser;
    private MutableLiveData<String> mText;
    //private MutableLiveData<String> isUser;

    public Boolean isUser() {
        return isUser;
    }

    public ProfileViewModel() {
        isUser = false;

        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    public void setUser() {
        isUser = true;
    }

    public LiveData<String> getText() {
        return mText;
    }
}