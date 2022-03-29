package com.andresd.socialverse.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    public static final String USERS = "users";
    private static UserRepository instance;

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

    public void getUser(@NonNull String uid, @NonNull MutableLiveData<User> userMutableLiveData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User u = document.toObject(User.class);
                        u.setId(document.getId());
                        userMutableLiveData.postValue(u);
                    }
                }
            }
        });

    }

}
