package com.andresd.socialverse.ui.group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;
import com.andresd.socialverse.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

public class GroupViewModel extends ViewModel {

    private static final String TAG = GroupViewModel.class.getName();

    private final LiveData<UserRepository.UserAuthState> userState;

    private final MediatorLiveData<Boolean> userSubscriptionMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<AbstractGroup> group = new MutableLiveData<>();
    private final MutableLiveData<AbstractUser> user = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isViewOnSchedule = new MutableLiveData<>(false);
    private final MediatorLiveData<ArrayList<AbstractScheduleItem>> listMediatorLiveData = new MediatorLiveData<>();
    private final ArrayList<AbstractScheduleItem> itemArrayList = new ArrayList<>(0);
    private LiveData<TreeSet<AbstractScheduleItem>> itemsTreeSetLiveData;

    GroupViewModel() {
        userState = UserRepository.getInstance().getUserAuthState();
        if (UserRepository.UserAuthState.checkStateIsValid(userState.getValue())) {
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) { // esto se puede remplazar con userState.getValue() == VALID o algo asi
//            userState.setValue(UserRepository.UserAuthState.VALID);

            itemsTreeSetLiveData = GroupRepository.getInstance().getItemsTreeSetLiveData();

            listMediatorLiveData.addSource(itemsTreeSetLiveData, new Observer<TreeSet<AbstractScheduleItem>>() {
                @Override
                public void onChanged(TreeSet<AbstractScheduleItem> abstractScheduleItems) {
                    itemArrayList.clear();

                    itemArrayList.addAll(abstractScheduleItems);

                    itemArrayList.trimToSize();

                    listMediatorLiveData.setValue(itemArrayList);
                }
            });

            /* ///////// */


            // listen livedata and notifies changes to the mediatorLiveData to via the observer,
            // this allows to observe the livedata inside the ViewModel and respond to its changes
            userSubscriptionMediatorLiveData.addSource(user,
                    u -> userSubscriptionMediatorLiveData.setValue(checkUserSubscribed()));
            userSubscriptionMediatorLiveData.addSource(group,
                    u -> userSubscriptionMediatorLiveData.setValue(checkUserSubscribed()));

        }
//        else {
//            userState.setValue(UserRepository.UserAuthState.NOT_LOGGED_IN);
//        }
    }

    public void setGroupId(@NonNull String groupId) {
        GroupRepository.getInstance().getGroup(groupId, group);
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

//        if (itemTreeSet.add(scheduleItem)) {
        if (itemsTreeSetLiveData.getValue() != null
                && !itemsTreeSetLiveData.getValue().contains(scheduleItem)) {
            if (group.getValue() != null) {
                GroupRepository.getInstance().addScheduleItem(group.getValue().getId().getId(), scheduleItem);
                return true;
            } else {
                Log.e(TAG, "addScheduleItem: received null Document Reference");
                return false;
            }
        }

        return false;
    }
    @Deprecated
    public boolean removeScheduleItem(@NonNull AbstractScheduleItem scheduleItem) {
        if (itemsTreeSetLiveData.getValue() != null) {
            itemsTreeSetLiveData.getValue().remove(scheduleItem);
            // TODO: Implement GroupRepository#DeleteScheduleItem
            // verify if #notifyDataSetChange is needed here
            // notifyDataSetChange(); // FIXME

            return true;
        }
        return false;
    }

    public void removeScheduleItem(int itemIndex) {
        if (itemsTreeSetLiveData.getValue() != null && group.getValue() != null) {
            AbstractScheduleItem item = getItem(itemIndex);

            GroupRepository.getInstance().deleteScheduleItem(group.getValue().getId().getId(), item);
        }
    }

    public boolean updateElement(@NonNull AbstractScheduleItem scheduleItem) {
//        if (itemsTreeSetLiveData.getValue() != null) {
//            itemsTreeSetLiveData.getValue().remove(scheduleItem);
//            return addScheduleItem(scheduleItem);
//        }

        if (itemsTreeSetLiveData.getValue() != null) {
            if (itemsTreeSetLiveData.getValue().contains(scheduleItem)) {
                // update
                if (group.getValue() != null) {
                    GroupRepository.getInstance().updateScheduleItem(group.getValue().getId().getId(), scheduleItem);
                    return true;
                } else {
                    Log.w(TAG, "updateElement: group is null");
                }
            } else {
                // add
                return addScheduleItem(scheduleItem);
            }

        }
        return false;
    }

    public void updateDataSet() {
        if (group.getValue() != null) {
            GroupRepository.getInstance().acquireSchedules(group.getValue().getId().getId());
        } else {
            Log.w(TAG, "updateDataSet: User data not registered.");
        }
    }

    @NonNull
    public final AbstractScheduleItem getItem(int index) {
        try {
            return itemArrayList.get(index);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            Log.e(TAG, "getItem: index out bounds");
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }
    }


    public void signOut() {
        UserRepository.getInstance().signOut();
        GroupRepository.getInstance().cleanData();
    }

    public boolean isUserSubscribed() {
        Boolean b = userSubscriptionMediatorLiveData.getValue();
        if (b != null) {
            return b;
        }
        return false;
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

    public void setUser(@Nullable String userId) {
        if (userId != null) {
            UserRepository.getInstance().setUserState(userId, user);
        } else {
            user.setValue(null);
        }
    }

    public LiveData<Boolean> getIsViewOnSchedule() {
        return isViewOnSchedule;
    }

    public void setIsViewOnSchedule(boolean isViewOnSchedule) {
        this.isViewOnSchedule.postValue(isViewOnSchedule);
    }

    public LiveData<ArrayList<AbstractScheduleItem>> getListLiveData() {
        return listMediatorLiveData;
    }

    public LiveData<UserRepository.UserAuthState> getUserState() {
        return userState;
    }

    public final void cleanRepository() {
        GroupRepository.getInstance().cleanData();
    }
}
