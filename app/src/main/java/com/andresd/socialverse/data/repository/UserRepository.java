package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private static final String TAG = UserRepository.class.getSimpleName();

    private static final String COLLECTION_USERS = "users";
    private static UserRepository instance;

    private User user;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    //
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private MutableLiveData<User> user = new MutableLiveData<>();
//    @Deprecated
//    public MutableLiveData<User> finduser(String uId) {
//        db.collection("users").document(uId)
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        User u = document.toObject(User.class);
//                        user.postValue(u);
//                    }
//                }
//            }
//        });
//        return user;
//    }

    public void getUser(@NonNull String uid, @NonNull MutableLiveData<AbstractUser> userMutableLiveData) {
        if (uid.equals(user.getId())) {
            userMutableLiveData.setValue(user);
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(COLLECTION_USERS).document(uid)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user = document.toObject(User.class);
                            if (user != null) {
                                user.setId(document.getId());
                                userMutableLiveData.postValue(user);
                            } else {
                                Log.e(TAG, "onComplete: document.toObject() is null");
                            }
                        }
                    }
                }
            });
        }
    }

}
