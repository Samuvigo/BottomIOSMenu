package com.ssantos.bottomiosmenu.model;

/**
 * Created by ssantos on 11/10/2017.
 */

public class OptionMenu {

    private int position;
    private String name;

    public OptionMenu(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
