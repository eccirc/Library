package com.postcourse;

public class Admin extends User{

    private final String login = "Admin";
    private final String password = "password";

    public Admin(String name) {
        super(name);
        super.setType(UserType.ADMIN);
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
