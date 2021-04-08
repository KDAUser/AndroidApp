package com.example.androidapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> mBool;

    public LoginViewModel() {
        mBool = new MutableLiveData<>();
        changeLoginCondition();
    }

    private void changeLoginCondition(){
        mBool.setValue(true);
    }

    public LiveData<Boolean> getLoginCondition(){
        return mBool;
    }

}