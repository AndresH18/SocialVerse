package com.andresd.socialverse.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public abstract class AbstractGroup {

    // attributes
    private DocumentReference id;
    private String name;
    private String detail;
    private List<String> tags;

    public AbstractGroup() {
    }

    public DocumentReference getId() {
        return id;
    }

    protected void setId(DocumentReference id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    protected void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getTags() {
        return tags;
    }

    protected void setTags(List<String> tags) {
        this.tags = tags;
    }
}
