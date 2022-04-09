package com.andresd.socialverse.ui.group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<AbstractGroup> group = new MutableLiveData<>();
    private LiveData<AbstractUser> userLiveData;

    public GroupViewModel() {
        // TODO : get user if exists and put it on livedata
        userLiveData = new MutableLiveData<>();
    }

    public void setGroupId(@NonNull String groupId) {
        GroupRepository.getInstance().getGroup(groupId, group);
    }

    /**
     * <p>Checks if the user is subscribed to the group.</p>
     * @return true if subscribed, false otherwise.
     */
    public boolean isUserSubscribed() {
        // TODO : Implement.
        return false;
    }

    public LiveData<AbstractGroup> getGroup() {
        return group;
    }

}
