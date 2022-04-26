package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.data.model.GroupCard;
import com.andresd.socialverse.data.model.MutableGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class GroupRepository {

    private static final String TAG = GroupRepository.class.getSimpleName();

    private static final String COLLECTION_GROUPS = "groups";
    private static final String COLLECTION_SCHEDULES = "schedules";

    private static final String FIELD_TAGS = "tags";
    private static final String FIELD_NAME = "name";

    private static volatile GroupRepository instance;

    private final MutableLiveData<TreeSet<AbstractScheduleItem>> itemsTreeSetLiveData;

    private GroupRepository() {
        itemsTreeSetLiveData = new MutableLiveData<>(new TreeSet<>());
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
                        MutableGroup group = documentSnapshot.toObject(MutableGroup.class);
                        if (group != null) {
                            group.setId(documentSnapshot.getReference());
                            mutableLiveData.postValue(group);
                        }
                    }
                }
            }
        });
    }

    @Deprecated
    public void getGroup(@NonNull DocumentReference reference, @NonNull MutableLiveData<AbstractGroup> mutableLiveData) {
        FirebaseFirestore.getInstance().document(reference.getPath())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        MutableGroup group = documentSnapshot.toObject(MutableGroup.class);
                        if (group != null) {
                            group.setId(documentSnapshot.getReference());
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


    public void addScheduleItem(@NonNull String groupId, @NonNull AbstractScheduleItem item) {
        final DocumentReference doc = FirebaseFirestore.getInstance().collection(COLLECTION_GROUPS).document(groupId)
                .collection(COLLECTION_SCHEDULES).document();
        ((AbstractScheduleItem.MutableScheduleItem) item).setId(doc.getId());
        doc.set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TreeSet<AbstractScheduleItem> treeSet = itemsTreeSetLiveData.getValue();
                    if (treeSet != null) {
                        treeSet.add(item);
                        itemsTreeSetLiveData.postValue(treeSet);
                    } else {
                        Log.w(TAG, "onComplete: set is null");
                    }
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });
    }

    public void updateScheduleItem(@NonNull String groupId, @NonNull AbstractScheduleItem item) {
        // TODO:
    }

    public void deleteScheduleItem(@NonNull String groupId, @NonNull AbstractScheduleItem item) {
        // TODO:
    }

    public void acquireSchedules(@NonNull String groupId) {
        FirebaseFirestore.getInstance().collection(COLLECTION_GROUPS).document(groupId)
                .collection(COLLECTION_SCHEDULES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    TreeSet<AbstractScheduleItem> set = itemsTreeSetLiveData.getValue();
                    if (set != null) {
                        for (DocumentSnapshot document : snapshot.getDocuments()) {
                            AbstractScheduleItem.MutableScheduleItem item = document.toObject(AbstractScheduleItem.MutableScheduleItem.class);
                            set.add(item);
                        }
                        itemsTreeSetLiveData.postValue(set);
                    } else {
                        Log.w(TAG, "onComplete: Set is null");
                    }
                }
            }
        });
    }

    public void cleanData() {
        // TODO : CLEAN DATA FROM GROUP_REPOSITORY
        itemsTreeSetLiveData.setValue(null);

    }

    public MutableLiveData<TreeSet<AbstractScheduleItem>> getItemsTreeSetLiveData() {
        return itemsTreeSetLiveData;
    }
}
