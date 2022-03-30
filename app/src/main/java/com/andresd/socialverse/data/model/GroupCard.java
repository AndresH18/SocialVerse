package com.andresd.socialverse.data.model;

import java.util.List;

public class GroupCard extends AbstractGroup {

    public GroupCard() {
        super();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
    }

    @Override
    public void setTags(List<String> tags) {
        super.setTags(tags);
    }
}
