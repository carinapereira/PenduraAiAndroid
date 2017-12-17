package com.example.carinadossantospereira.shoppingnotebook.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by carinadossantospereira on 16/12/17.
 */

public final class ConfigurationFirebase {

    private static DatabaseReference referenceFirebase;


    public DatabaseReference getFirebase(){

        if(referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceFirebase;
    }
}
