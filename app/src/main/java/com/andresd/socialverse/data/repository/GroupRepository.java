package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractPost;
import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.data.model.GroupCard;
import com.andresd.socialverse.data.model.MutableGroup;
import com.andresd.socialverse.data.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class GroupRepository {

    private static final String TAG = GroupRepository.class.getName();

    private static final String ROOT_COLLECTION_GROUPS = "groups";
    private static final String COLLECTION_SCHEDULES = "schedules";
    private static final String COLLECTION_POSTS = "posts";

    private static final String ROOT_COLLECTION_UNIVERSITY = "university";

    private static final String FIELD_TAGS = "tags";
    private static final String FIELD_NAME = "name";

    private static volatile GroupRepository instance;

    private CollectionReference universityPostsCollection = FirebaseFirestore.getInstance()
            .collection(ROOT_COLLECTION_UNIVERSITY).document(COLLECTION_POSTS)
            .collection(COLLECTION_POSTS);

    private final MutableLiveData<TreeSet<AbstractScheduleItem>> itemsTreeSetLiveData = new MutableLiveData<>(new TreeSet<>());

    private final MutableLiveData<Map<AbstractPost, AbstractPost>> universityPosts = new MutableLiveData<>(new HashMap<>());


    private GroupRepository() {
        universityPostsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "onEvent: listen failed.", error);
                }
                if (value != null) {
                    Map<AbstractPost, AbstractPost> map = universityPosts.getValue() != null ?
                            universityPosts.getValue() : new HashMap<>();

                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        try {
                            final Post post = documentChange.getDocument().toObject(Post.class);
                            post.setId(documentChange.getDocument().getId());

                            switch (documentChange.getType()) {
                                case ADDED:
                                    map.put(post, post);
                                    break;
                                case REMOVED:
                                    map.remove(post);
                                    break;
                                case MODIFIED:
                                    Post p = (Post) map.get(post);
                                    if (p != null) {
                                        p.setMessage(post.getMessage());
                                        p.setTitle(post.getTitle());
                                    } else {
                                        map.put(post, post);
                                    }
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            Log.w(TAG, "onEvent: Failed to Convert Object", e);
                        }
                    }
                    universityPosts.postValue(map);
                } else {
                    Log.w(TAG, "onEvent: listen Failed, QuerySnapshot is null");
                }

            }
        });
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
        FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).document(id)
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
                    } else {
                        Log.w(TAG, "onComplete: document doesn't exists");
                    }
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });
    }

   /* @Deprecated
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
                    } else {
                        Log.w(TAG, "onComplete: Document doesn't exist");
                    }
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });
    }*/

    /**
     * <p>Searches the {@link AbstractGroup}s that contain any of the groupTags, and posts <br>
     * the {@link List} on the {@link MutableLiveData}</p>
     *
     * @param groupTags       {@link List} of tags to search
     * @param mutableLiveData where to post the result
     */
    public void searchGroupsByTags(@NonNull List<String> groupTags, @NonNull MutableLiveData<List<AbstractGroup>> mutableLiveData) {
        FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).whereArrayContainsAny(FIELD_TAGS, groupTags)
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
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
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
        FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).whereEqualTo(FIELD_NAME, groupName)
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
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });
    }


    public void addScheduleItem(@NonNull String groupId, @NonNull AbstractScheduleItem item) {
        final DocumentReference doc = FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).document(groupId)
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
        final Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("dateTime", item.getDateTime());
        updateMap.put("details", item.getDetails());
        updateMap.put("title", item.getTitle());

        FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).document(groupId)
                .collection(COLLECTION_SCHEDULES).document(item.getId())
                .update(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    itemsTreeSetLiveData.postValue(itemsTreeSetLiveData.getValue());
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });

    }

    public void deleteScheduleItem(@NonNull String groupId, @NonNull final AbstractScheduleItem item) {
        final DocumentReference doc =
                FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).document(groupId)
                        .collection(COLLECTION_SCHEDULES).document(item.getId());

        doc.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TreeSet<AbstractScheduleItem> treeSet = itemsTreeSetLiveData.getValue();
                    if (treeSet != null) {
                        treeSet.remove(item);
                    } else {
                        treeSet = new TreeSet<>();
                    }
                    itemsTreeSetLiveData.postValue(treeSet);
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
                }
            }
        });
    }

    public void acquireSchedules(@NonNull String groupId) {
        FirebaseFirestore.getInstance().collection(ROOT_COLLECTION_GROUPS).document(groupId)
                .collection(COLLECTION_SCHEDULES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    TreeSet<AbstractScheduleItem> set = itemsTreeSetLiveData.getValue();
                    if (set == null) {
                        set = new TreeSet<>();
                    }
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        AbstractScheduleItem.MutableScheduleItem item = document.toObject(AbstractScheduleItem.MutableScheduleItem.class);
                        item.setId(document.getId());
                        set.add(item);
                    }
                    itemsTreeSetLiveData.postValue(set);
                } else {
                    Log.e(TAG, "onComplete: task failed", task.getException());
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

    public MutableLiveData<Map<AbstractPost, AbstractPost>> getUniversityPosts() {
        return universityPosts;
    }
}
