package com.andresd.socialverse.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    private static final String GROUPS = "groups";
    private static final String TAGS = "tags";
    private static final String NAME = "name";

    private static volatile GroupRepository instance;

    private GroupRepository() {

    }

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    public void searchGroupsByTag(@NonNull List<String> groupTags, @NonNull MutableLiveData<List<Group>> liveData) {
        FirebaseFirestore.getInstance().collection(GROUPS).whereArrayContainsAny(TAGS, groupTags)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Group> groupList = new ArrayList<>();
                    Group group;
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        group = document.toObject(Group.class);
                        group.setId(document.getId());
                        groupList.add(group);
                    }
                    liveData.postValue(groupList);
                }
            }
        });
    }

    public void searchGroupByName(@NonNull String groupName, @NonNull MutableLiveData<List<Group>> liveData) {
        FirebaseFirestore.getInstance().collection(GROUPS).whereEqualTo(NAME, groupName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Group> groupList = new ArrayList<>();
                    Group group;
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        group = document.toObject(Group.class);
                        group.setId(document.getId());
                        groupList.add(group);
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

    public void getGroups(@NonNull List<String> groups) {
//        FirebaseFirestore.getInstance().collection(GROUPS).;
    }
}
