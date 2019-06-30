package com.entity;

public class Student {
    private String username;
    private String url;

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Student(String username, String url) {
        this.username = username;
        this.url = url;
    }

    public Student() {

    }
}
