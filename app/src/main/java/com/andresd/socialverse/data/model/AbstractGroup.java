package com.andresd.socialverse.data.model;

import java.util.List;

public abstract class AbstractGroup {

    // attributes
    private String id;
    private String name;
    private String description;
    private List<String> tags;

    public AbstractGroup() {
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

    protected void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    protected void setTags(List<String> tags) {
        this.tags = tags;
    }
}
