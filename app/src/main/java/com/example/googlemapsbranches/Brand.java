package com.example.googlemapsbranches;

import java.util.ArrayList;

public class Brand {

    private int id;
    private String name;

    ArrayList<Brand> branches = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Brand> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Brand> branches) {
        this.branches = branches;
    }
}
