package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.GroupCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    private static final String TAG = GroupRepository.class.getSimpleName();

    private static final String COLLECTION_GROUPS = "groups";
    private static final String FIELD_TAGS = "tags";
    private static final String FIELD_NAME = "name";

    private static volatile GroupRepository instance;

    private GroupRepository() {

    }

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    public void searchGroupsByTags(@NonNull List<String> groupTags, @NonNull MutableLiveData<List<AbstractGroup>> liveData) {
        FirebaseFirestore.getInstance().collection(COLLECTION_GROUPS).whereArrayContainsAny(FIELD_TAGS, groupTags)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<AbstractGroup> groupList = new ArrayList<>();
                    GroupCard group;
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        group = document.toObject(GroupCard.class);
                        if (group != null) {
                            group.setId(document.getId());
                            groupList.add(group);
                        } else {
                            Log.e(TAG, "onComplete: document.toObject() is null");
                        }
                    }
                    liveData.postValue(groupList);
                }
            }
        });
    }

    public void searchGroupByName(@NonNull String groupName, @NonNull MutableLiveData<List<AbstractGroup>> liveData) {
        FirebaseFirestore.getInstance().collection(COLLECTION_GROUPS).whereEqualTo(FIELD_NAME, groupName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<AbstractGroup> groupList = new ArrayList<>();
                    GroupCard group;
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        group = document.toObject(GroupCard.class);
                        if (group != null) {
                            group.setId(document.getId());
                            groupList.add(group);
                        } else {
                            Log.e(TAG, "onComplete: document.toObject() is null");
                        }
                    }
                    liveData.postValue(groupList);
                }
            }
        });
//        FirebaseFirestore.getInstance().collection(GROUPS).document(groupName)
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        liveData.postValue(document.toObject(Group.class));
//                    } else {
//                        liveData.postValue(null);
//                    }
//                }
//            }
//        });

//        return groupLiveData;
    }

//    public void getUserGroups(@NonNull )

    public void getGroups(@NonNull List<String> groupsIds) {
//        FirebaseFirestore.getInstance().collection(GROUPS).;
    }
}
