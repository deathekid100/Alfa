package com.example.ernestoojea.app2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ernesto.ojea on 30/1/2019.
 */

public class Sms {
    private int id;
    private String body,address,type,date;

    public Sms(String body, String address, String type) {
        this.body = body;
        this.address = address;
        this.type = type;
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm");
        String dateString = formater.format(new Date());
        this.date = dateString;
    }

    public Sms(int id, String body, String address, String type, String date) {
        this.id = id;
        this.body = body;
        this.address = address;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
