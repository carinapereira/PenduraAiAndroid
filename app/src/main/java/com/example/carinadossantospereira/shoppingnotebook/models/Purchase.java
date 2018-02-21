package com.example.carinadossantospereira.shoppingnotebook.models;

import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

/**
 * Created by carinadossantospereira on 30/11/17.
 */

public class Purchase implements Serializable {
    String key = "";
    Double value = 0.0;
    Boolean sum = false;

    public Purchase() {

    }

    public Purchase(String key, Double value, Boolean sum) {
        this.key = key;
        this.value = value;
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

    public Boolean getSum() {
        return sum;
    }

    public void setSum(Boolean sum) {
        this.sum = sum;
    }



}
