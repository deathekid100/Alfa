package com.example.ernestoojea.app2;

/**
 * Created by ernesto.ojea on 4/2/2019.
 */

public class Screen {
    int id;
    String date,type;
    int islock;

    public Screen(int id, String date, String type, int islock) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.islock = islock;
    }
    public Screen( String date, String type, int islock) {
        this.date = date;
        this.type = type;
        this.islock = islock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIslock() {
        return islock;
    }

    public void setIslock(int islock) {
        this.islock = islock;
    }

}
