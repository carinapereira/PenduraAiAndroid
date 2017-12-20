package com.example.carinadossantospereira.shoppingnotebook.models;

import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by carinadossantospereira on 28/11/17.
 */

public class Shop {
    private String key = "";
    private String name = "";
    private String cpfCnpj = "";
    private String phone = "";

    public Shop(){

    }

    public Shop(String key, String name, String cpfCnpj, String phone) {
        this.key = key;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.phone = phone;
    }

    public void salvar(){
        DatabaseReference referenceFirebase = ConfigurationFirebase.getFirebase();
        referenceFirebase.child("shop").child(getKey()).setValue(this);
    }

    //exclude para nao salvar no firebase o campo key
    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
