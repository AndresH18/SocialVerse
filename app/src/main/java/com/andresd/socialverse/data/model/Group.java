package com.andresd.socialverse.data.model;

/**
 *
 */
public class Group {

    // attributes
    // TODO: Check if delete id field
    private String id;
    private String name;
    private String detail;

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

}
