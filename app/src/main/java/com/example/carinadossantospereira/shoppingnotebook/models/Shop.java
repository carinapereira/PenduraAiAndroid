package com.example.carinadossantospereira.shoppingnotebook.models;

/**
 * Created by carinadossantospereira on 28/11/17.
 */

public class Shop {
    private String key = "";
    private String userKey = "";
    private String name = "";
    private String cpfCnpj = "";
    private String phone = "";

    public Shop(String key, String userKey, String name, String cpfCnpj, String phone) {
        this.key = key;
        this.userKey = userKey;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
