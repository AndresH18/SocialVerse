package com.andresd.socialverse.data.model;

import java.util.List;

public abstract class AbstractUser {

    private String id;
    private String name;
    // TODO : define type for List<T>
    private List groups;

    public AbstractUser() {
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    // TODO : define type for List<T>
    public List getGroups() {
        return groups;
    }

    // TODO : define type for List<T>
    protected void setGroups(List groups) {
        this.groups = groups;
    }
}
