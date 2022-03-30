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
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setTags(List<String> tags) {
        super.setTags(tags);
    }
}
