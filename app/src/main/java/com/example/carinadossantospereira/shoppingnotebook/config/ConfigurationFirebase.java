package com.example.carinadossantospereira.shoppingnotebook.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by carinadossantospereira on 16/12/17.
 */

public final class ConfigurationFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth autentication;
    private static FirebaseStorage firebaseStorage;


    public static DatabaseReference getFirebase(){

        if(referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceFirebase;
    }

    public static FirebaseAuth getFirebaseAutentication(){
        if(autentication == null){
            autentication = FirebaseAuth.getInstance();
        }

        return  autentication;
    }

    public static FirebaseStorage getFirebaseStorage(){
        if(firebaseStorage == null){
            firebaseStorage = FirebaseStorage.getInstance();
        }

        return  firebaseStorage;
    }
}
