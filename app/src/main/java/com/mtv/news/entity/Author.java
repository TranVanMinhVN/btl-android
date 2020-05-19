package com.mtv.news.entity;

import java.util.Set;

public class Author {

    private int authorId;
    private String name;
    private String address;

    private String phoneNumber;

    public Author(int authorId, String name, String address, String phoneNumber) {
        this.authorId = authorId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getAuthorId() {
        return authorId;
    }


    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
