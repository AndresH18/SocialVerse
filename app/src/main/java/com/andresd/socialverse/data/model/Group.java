package com.andresd.socialverse.data.model;

import java.util.List;

/**
 *
 */
public class Group {

    // attributes
    private String id;
    private String name;
    private String detail;
    private List<Object> tags;

    // required Empty constructor
    public Group() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }
}
