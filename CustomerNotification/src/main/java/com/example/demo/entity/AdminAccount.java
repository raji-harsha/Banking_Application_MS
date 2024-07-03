package com.example.demo.entity;

public class AdminAccount {
    private String username = "admin";
    private String password = "admin";

    public AdminAccount() {
    }

    public AdminAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AdminAccount username(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminAccount password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
