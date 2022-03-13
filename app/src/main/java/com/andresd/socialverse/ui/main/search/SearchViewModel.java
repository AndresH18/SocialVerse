package com.andresd.socialverse.ui.main.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue("This is Search Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}