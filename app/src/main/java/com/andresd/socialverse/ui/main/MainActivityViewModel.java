package com.andresd.socialverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractPost;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;
import com.andresd.socialverse.data.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class MainActivityViewModel extends ViewModel {
    // some sort of user object?
    //
    private final MutableLiveData<AbstractUser> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<AbstractGroup>> searchGroups = new MutableLiveData<>();

    private final LiveData<UserRepository.UserAuthState> userState;
//    private String lastUID;

    private final MutableLiveData<String> mHomeText = new MutableLiveData<>("This is Home Fragment");
    private final MediatorLiveData<SortedSet<AbstractPost>> universityPostsMediator = new MediatorLiveData<>();

    MainActivityViewModel() {
        userState = UserRepository.getInstance().getUserAuthState();
        // initialize values
        UserRepository.getInstance().setUserState(currentUser);
//            lastUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            currentUserLiveData = UserRepository.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // TODO: Add auth state change listener
//        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  // use lastUID to check if user changed
//            }
//        });

        universityPostsMediator.addSource(GroupRepository.getInstance().getUniversityPosts(),
                new Observer<Map<AbstractPost, AbstractPost>>() {
                    @Override
                    public void onChanged(Map<AbstractPost, AbstractPost> abstractPostAbstractPostTreeMap) {
                        if (abstractPostAbstractPostTreeMap != null) {
                            universityPostsMediator.setValue(new TreeSet<>(abstractPostAbstractPostTreeMap.keySet()));
                        }
                    }
                });

    }


    public void signOut() {
        UserRepository.getInstance().signOut();
        GroupRepository.getInstance().cleanData();
//        userState.setValue(UserRepository.UserAuthState.INVALID); // or SIGNED_OUT
    }

    public void searchGroupByName(@NonNull String groupName) {
        GroupRepository.getInstance().searchGroupByName(groupName, searchGroups);
    }

    public void searchGroupsByTags(@NonNull String tags) {
        List<String> tagsList = Arrays.asList(tags.replaceAll(" ", "").split(","));
        GroupRepository.getInstance().searchGroupsByTags(tagsList, searchGroups);
    }

    public LiveData<List<AbstractGroup>> getSearchGroups() {
        return searchGroups;
    }

    public LiveData<UserRepository.UserAuthState> getUserState() {
        return userState;
    }

    public LiveData<AbstractUser> getCurrentUser() {
        return currentUser;
    }

    public LiveData<String> getText() {
        return mHomeText;
    }


}
