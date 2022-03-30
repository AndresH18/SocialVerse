package com.andresd.socialverse.data.model;

import java.util.List;

public abstract class AbstractGroup {

    // attributes
    private String id;
    private String name;
    private String detail;
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
