package com.andresd.socialverse.ui.main.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.repository.GroupRepository;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class SearchViewModel extends ViewModel {

    //    private MutableLiveData<String> mText;
    private MutableLiveData<List<AbstractGroup>> searchGroups = new MutableLiveData<>();

    public SearchViewModel() {
//        this.mText = new MutableLiveData<>();
//        mText.setValue("This is Search Fragment");
    }

    public void searchGroupByName(@NonNull String groupName) {
//        GroupRepository.getInstance().searchGroup(groupName, this.groupLiveData);
        GroupRepository.getInstance().searchGroupByName(groupName, searchGroups);
    }

    public void searchGroupsByTags(@NonNull String tags) {
        List<String> tagList = Arrays.asList(tags.replaceAll(" ", "").split(","));
        GroupRepository.getInstance().searchGroupsByTags(tagList, searchGroups);
    }

    public LiveData<List<AbstractGroup>> getSearchGroups() {
        return searchGroups;
    }


}