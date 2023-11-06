package com.example.ansiedapp;

public class Item {
    private String name;
    private int concernLevel;

    public Item(String name, int concernLevel) {
        this.name = name;
        this.concernLevel = concernLevel;
    }

    public String getName() {
        return name;
    }

    public int getConcernLevel() {
        return concernLevel;
    }
}