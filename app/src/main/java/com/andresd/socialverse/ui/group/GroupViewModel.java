package com.andresd.socialverse.ui.group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.repository.GroupRepository;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<AbstractGroup> group = new MutableLiveData<>();


    public void setGroupId(@NonNull String groupId) {
        GroupRepository.getInstance().getGroup(groupId, group);
    }

    public LiveData<AbstractGroup> getGroup() {
        return group;
    }
}
