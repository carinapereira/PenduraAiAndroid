package com.example.carinadossantospereira.shoppingnotebook.models;

/**
 * Created by carinadossantospereira on 30/11/17.
 */

public class ShopClient {
    String key = "";
    String userKey = "";
    String shopKey = "";
    String name = "";
    String cpf = "";
    String phone = "";
    Double creditLimit = 0.0;
    String urlImage = "";

    public ShopClient(){

    }

    public ShopClient(String key, String userKey, String shopKey, String name, String cpf, String phone, Double creditLimit, String urlImage) {
        this.key = key;
        this.userKey = userKey;
        this.shopKey = shopKey;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.creditLimit = creditLimit;
        this.urlImage = urlImage;
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

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }


}
