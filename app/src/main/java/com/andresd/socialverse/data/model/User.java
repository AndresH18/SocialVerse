package com.andresd.socialverse.data.model;

import java.util.List;
import java.util.Map;

public class User extends AbstractUser {

    public User() {
        super();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public void setGroups(List<Map<String, Object>> groups) {
        super.setGroups(groups);
    }
}
