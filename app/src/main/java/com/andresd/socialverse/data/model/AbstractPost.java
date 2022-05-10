package com.andresd.socialverse.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class AbstractPost implements Comparable<AbstractPost> {
    @Exclude
    private String id;

    private String title;
    private String message;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    protected void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(AbstractPost o) {
        return id.equals(o.id) ? 0 : timestamp.compareTo(o.timestamp);
    }
}
