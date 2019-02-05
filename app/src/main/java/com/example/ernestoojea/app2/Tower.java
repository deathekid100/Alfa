package com.example.ernestoojea.app2;

/**
 * Created by ernesto.ojea on 5/2/2019.
 */

public class Tower {
    int id;
    String date;
    int cid,lac;

    public Tower(int id, String date, int cid, int lac) {
        this.id = id;
        this.date = date;
        this.cid = cid;
        this.lac = lac;
    }
    public Tower(String date, int cid, int lac) {
        this.date = date;
        this.cid = cid;
        this.lac = lac;
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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }



}
