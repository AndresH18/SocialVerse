package com.andresd.socialverse.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post extends AbstractPost {

    @Override
    public Timestamp getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void setTimestamp(Timestamp timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }
}
