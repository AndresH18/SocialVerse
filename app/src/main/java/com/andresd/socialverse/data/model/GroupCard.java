package com.andresd.socialverse.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class GroupCard extends AbstractGroup {

    public GroupCard() {
        super();
    }

    @Override
    public void setId(DocumentReference id) {
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
