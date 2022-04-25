package com.andresd.socialverse.ui.group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;
import com.andresd.socialverse.data.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GroupViewModel extends ViewModel {

    private static final String TAG = GroupViewModel.class.getSimpleName();

    private final MediatorLiveData<Boolean> userSubscriptionMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<AbstractGroup> group = new MutableLiveData<>();
    private final MutableLiveData<AbstractUser> user = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isViewOnSchedule = new MutableLiveData<>(false);
    //
    private TreeSet<AbstractScheduleItem> itemTreeSet = new TreeSet<>();
    private Map<Integer, AbstractScheduleItem> itemMap = new HashMap<>();
    private final MutableLiveData<TreeSet<AbstractScheduleItem>> itemsTreeSetLiveData = new MutableLiveData<>(itemTreeSet);

    public GroupViewModel() {
        // listen livedata and notifies changes to the mediatorLiveData to via the observer,
        // this allows to observe the livedata inside the ViewModel and respond to its changes
        userSubscriptionMediatorLiveData.addSource(user,
                u -> userSubscriptionMediatorLiveData.setValue(checkUserSubscribed()));
        userSubscriptionMediatorLiveData.addSource(group,
                u -> userSubscriptionMediatorLiveData.setValue(checkUserSubscribed()));
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
    private boolean checkUserSubscribed() {
        if (user.getValue() != null && group.getValue() != null) {
            for (Map<String, Object> groupMap : user.getValue().getGroups()) {
                Object g = groupMap.get("id");
                if (g != null && group.getValue().getId().equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean addScheduleItem(@NonNull AbstractScheduleItem scheduleItem) {
        itemTreeSet.add(scheduleItem);
        itemMap.clear();
        int i = 0;
        for (AbstractScheduleItem item : itemTreeSet) {
            itemMap.put(i++, item);
        }

        notifyDataSetChange();

        return true;
    }

    public boolean removeScheduleItem(@NonNull AbstractScheduleItem scheduleItem) {
        itemTreeSet.remove(scheduleItem);
        itemMap.clear();
        int i = 0;
        for (AbstractScheduleItem item : itemTreeSet) {
            itemMap.put(i++, item);
        }

        notifyDataSetChange();

        return true;
    }

    public boolean updateElement(@NonNull AbstractScheduleItem scheduleItem) {
        itemTreeSet.remove(scheduleItem);
        return addScheduleItem(scheduleItem);
    }

    private void notifyDataSetChange() {

        itemsTreeSetLiveData.setValue(null);

        itemsTreeSetLiveData.setValue(itemTreeSet);

    }

    @NonNull
    public AbstractScheduleItem getItem(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        AbstractScheduleItem item = itemMap.get(index);
        if (item == null) {
            Log.e(TAG, "getItem: null item with key " + index);
            throw new RuntimeException("key returns null");
        }
        return item;
    }

    public boolean isUserSubscribed() {
        Boolean b = userSubscriptionMediatorLiveData.getValue();
        if (b != null) {
            return b;
        }
        return false;
    }

    public void setIsViewOnSchedule(boolean isViewOnSchedule) {
        this.isViewOnSchedule.postValue(isViewOnSchedule);
    }

    public LiveData<Boolean> getUserSubscriptionMediatorLiveData() {
        return userSubscriptionMediatorLiveData;
    }

    public LiveData<AbstractGroup> getGroup() {
        return group;
    }

    public LiveData<AbstractUser> getUser() {
        return user;
    }

    public LiveData<Boolean> getIsViewOnSchedule() {
        return isViewOnSchedule;
    }

    public MutableLiveData<TreeSet<AbstractScheduleItem>> getItemsTreeSetLiveData() {
        return itemsTreeSetLiveData;
    }
}
