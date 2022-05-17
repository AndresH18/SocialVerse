package com.andresd.socialverse.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

@IgnoreExtraProperties
public abstract class AbstractPost implements Comparable<AbstractPost> {

    @Exclude
    private String id;

    private String title;
    private String message;
    private String owner;
    @ServerTimestamp
    private Timestamp timestamp;

    public AbstractPost() {

    }

    public AbstractPost(String title, String message, String owner) {
        this.title = title;
        this.message = message;
        this.owner = owner;
        this.id = null;
        this.timestamp = null;
    }

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

    public String getOwner() {
        return owner;
    }

    protected void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int compareTo(AbstractPost o) {
        return id.equals(o.id) ? 0 : timestamp.compareTo(o.timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractPost that = (AbstractPost) o;

        if (!id.equals(that.id)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}
