package com.soap.franxi.model;

import java.io.Serializable;

/**
 * Created by Admin on 27/12/2016.
 */

public class Manager implements Serializable {
    public int ID;
    public String Avatar;
    public String fullName;

    public Manager(){

    }
    public Manager(int ID, String avatar, String fullName) {
        this.ID = ID;
        Avatar = avatar;
        this.fullName = fullName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
