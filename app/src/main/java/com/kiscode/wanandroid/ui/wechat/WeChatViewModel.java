package com.kiscode.wanandroid.ui.wechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class WeChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WeChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(Calendar.getInstance().getTimeInMillis()));
    }

    public LiveData<String> getText() {
        return mText;
    }
}