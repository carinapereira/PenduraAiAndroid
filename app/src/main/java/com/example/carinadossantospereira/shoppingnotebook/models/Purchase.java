package com.example.carinadossantospereira.shoppingnotebook.models;

/**
 * Created by carinadossantospereira on 30/11/17.
 */

public class Purchase {
    String key = "";
    Double value = 0.0;
    String date = "";
    String time = "";
    Boolean sum = false;

    public Purchase(String key, Double value, String date, String time, Boolean sum) {
        this.key = key;
        this.value = value;
        this.date = date;
        this.time = time;
        this.sum = sum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getSum() {
        return sum;
    }

    public void setSum(Boolean sum) {
        this.sum = sum;
    }



}
