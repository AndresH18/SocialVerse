package com.andresd.socialverse.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class User {

    private String id;
    private String name;
    private List<DocumentReference> groups;

    public User() {
        // required empty constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentReference> getGroups() {
        return groups;
    }

    public void setGroups(List<DocumentReference> groups) {
        this.groups = groups;
    }
}
