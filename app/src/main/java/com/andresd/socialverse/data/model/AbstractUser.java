package com.andresd.socialverse.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Map;

public abstract class AbstractUser {

    private DocumentReference id;
    private String name;
    // TODO : define type for Map<String, Object>
    //  esta asi porque la lista de grupos tiene una referencia
    private List<Map<String, Object>> groups;

    public AbstractUser() {
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

    private void setName(String name) {
        this.name = name;
    }

    // TODO : define type for Map<String, Object>
    public List<Map<String, Object>> getGroups() {
        return groups;
    }

    // TODO : define type for List<T>
    protected void setGroups(List<Map<String, Object>> groups) {
        this.groups = groups;
    }
}
