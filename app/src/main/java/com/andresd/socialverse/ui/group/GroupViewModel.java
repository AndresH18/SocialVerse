package com.andresd.socialverse.ui.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;
import com.andresd.socialverse.data.repository.UserRepository;

import java.util.Map;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<AbstractGroup> group = new MutableLiveData<>();
    private MutableLiveData<AbstractUser> user;

    public GroupViewModel() {
        // TODO : get user if exists and put it on livedata
        user = new MutableLiveData<>();
    }

    public void setGroupId(@NonNull String groupId) {
        GroupRepository.getInstance().getGroup(groupId, group);
    }

    public void setUser(@Nullable String userId) {
        if (userId != null) {
            UserRepository.getInstance().getUser(userId, user);
        } else {
            user.setValue(null);
        }
    }

    /**
     * <p>Checks if the user is subscribed to the group.</p>
     *
     * @return true if subscribed, false otherwise.
     */
    public boolean isUserSubscribed() {
        // TODO : Implement.
        if (user.getValue() != null && group.getValue() != null) {
            for (Map<String, Object> groups : user.getValue().getGroups()) {
                Object g = groups.get("id");
                if (g != null && group.getValue().getId().equals(g)) {
                    return true;
                }
            }
        }
        return false;


    }

    public LiveData<AbstractGroup> getGroup() {
        return group;
    }

    public LiveData<AbstractUser> getUser() {
        return user;
    }
}
