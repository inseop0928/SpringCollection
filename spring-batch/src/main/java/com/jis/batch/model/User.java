package com.jis.batch.model;


import lombok.Data;
import lombok.Getter;

@Data
public class User {

    private int id;
    private String name;
    private int age;
    private String address;

    public User(int id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
