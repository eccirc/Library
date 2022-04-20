package com.postcourse;

import com.fasterxml.jackson.annotation.JsonAlias;

public abstract class User {

    @JsonAlias("name")
    private String name;
    @JsonAlias("type")
    private UserType type;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
