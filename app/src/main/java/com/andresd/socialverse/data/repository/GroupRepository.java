package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.GroupCard;
import com.andresd.socialverse.data.model.MutableGroup;
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

    /**
     * <p>Returns the instance of {@link GroupRepository}. If it doesn't exist, it creates it.</p>
     *
     * @return the instance of {@link GroupRepository}
     */
    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    /**
     * <p>Gets the {@link AbstractGroup} from the Firestore groups' collection that corresponds to the id, and
     * posts it on the {@link MutableLiveData}.</p>
     *
     * @param id              the id of the group
     * @param mutableLiveData where to post the group
     */
    public void getGroup(@NonNull String id, @NonNull MutableLiveData<AbstractGroup> mutableLiveData) {
        FirebaseFirestore.getInstance().collection(COLLECTION_GROUPS).document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        AbstractGroup group = documentSnapshot.toObject(MutableGroup.class);
                        if (group != null) {
                            mutableLiveData.postValue(group);
                        }
                    }
                }
            }
        });
    }

    /**
     * <p>Searches the {@link AbstractGroup}s that contain any of the groupTags, and posts <br>
     * the {@link List} on the {@link MutableLiveData}</p>
     *
     * @param groupTags       {@link List} of tags to search
     * @param mutableLiveData where to post the result
     */
    public void searchGroupsByTags(@NonNull List<String> groupTags, @NonNull MutableLiveData<List<AbstractGroup>> mutableLiveData) {
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
                            group.setId(document.getReference());
                            groupList.add(group);
                        } else {
                            Log.e(TAG, "onComplete: document.toObject() is null");
                        }
                    }
                    mutableLiveData.postValue(groupList);
                }
            }
        });
    }

    /**
     * <p>Searches the {@link AbstractGroup} that matches the groupName, and posts <br>
     * the result on the {@link MutableLiveData}</p>
     *
     * @param groupName       name of the group to search
     * @param mutableLiveData where to post the result
     */
    public void searchGroupByName(@NonNull String groupName, @NonNull MutableLiveData<List<AbstractGroup>> mutableLiveData) {
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
                            group.setId(document.getReference());
                            groupList.add(group);
                        } else {
                            Log.e(TAG, "onComplete: document.toObject() is null");
                        }
                    }
                    mutableLiveData.postValue(groupList);
                }
            }
        });
    }

}
