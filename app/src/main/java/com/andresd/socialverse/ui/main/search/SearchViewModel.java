package com.andresd.socialverse.ui.main.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.Group;
import com.andresd.socialverse.data.repository.GroupRepository;

public class SearchViewModel extends ViewModel {

    //    private MutableLiveData<String> mText;
    private MutableLiveData<Group> groupLiveData = new MutableLiveData<>();

    public SearchViewModel() {
//        this.mText = new MutableLiveData<>();
//        mText.setValue("This is Search Fragment");
    }

    public void searchGroup(@NonNull String searchString) {
        searchString = searchString.replaceAll(" ", "_");
        GroupRepository.getInstance().searchGroup(searchString, this.groupLiveData);
    }


    public LiveData<Group> getGroupLiveData() {
        return groupLiveData;
    }
}