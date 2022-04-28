package com.andresd.socialverse.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private static final String TAG = UserRepository.class.getName();

    private static final String COLLECTION_USERS = "users";
    private static volatile UserRepository instance;

    private final MutableLiveData<UserAuthState> mUserAuthState = new MutableLiveData<>();

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

    private void getUser(@NonNull String uid, @NonNull MutableLiveData<AbstractUser> userMutableLiveData) {
        if (user != null && user.getId().equals(uid)) {
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
                    } else {
                        Log.e(TAG, "onComplete: task was not successful");
                    }
                }
            });
        }
    }

    public final Task<com.google.firebase.auth.AuthResult> signInWithEmailAndPassword(@NonNull String username, @NonNull String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password);
    }

    public final void signOut() {
        FirebaseAuth.getInstance().signOut();
        // TODO : use auth listener
        mUserAuthState.setValue(UserAuthState.SIGNED_OUT);
    }

    public final void setUserState(@NonNull String uid, @NonNull MutableLiveData<AbstractUser> userMutableLiveData) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mUserAuthState.setValue(UserAuthState.VALID);
            getUser(uid, userMutableLiveData);
        } else {
            mUserAuthState.setValue(UserAuthState.NOT_LOGGED_IN);
        }
    }

    public final void setUserState(@NonNull MutableLiveData<AbstractUser> userMutableLiveData) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mUserAuthState.setValue(UserAuthState.VALID);
            getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), userMutableLiveData);
        } else {
            mUserAuthState.setValue(UserAuthState.NOT_LOGGED_IN);
        }
    }

    public LiveData<UserAuthState> getUserAuthState() {
        return mUserAuthState;
    }

    public enum UserAuthState {
        // TODO: use better states
        VALID,
        INVALID,
        SIGNED_IN,
        /**
         * <p>The user has signed out.</p>
         */
        SIGNED_OUT,
        /**
         * <p>The user has not been logged.</p>
         */
        NOT_LOGGED_IN,
        ;

        public static boolean checkStateIsValid(UserAuthState authState) {
            return authState == VALID || authState == SIGNED_IN;
        }

    }

}
