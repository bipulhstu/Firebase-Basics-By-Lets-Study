package com.bipulhstu.firebasebasicsbyletsstudy;

public class UserData {
    String name;
    int age;

    public UserData() {
    }

    public UserData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
