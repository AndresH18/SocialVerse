package com.andresd.socialverse.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupRepository {

    public static final String GROUPS = "groups";

    private static volatile GroupRepository instance;

    private GroupRepository() {

    }

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    public void searchGroup(@NonNull String documentString, MutableLiveData<Group> liveData) {
        FirebaseFirestore.getInstance().collection(GROUPS).document(documentString)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        liveData.postValue(document.toObject(Group.class));
                    } else {
                        liveData.postValue(null);
                    }
                }
            }
        });

//        return groupLiveData;
    }
}
