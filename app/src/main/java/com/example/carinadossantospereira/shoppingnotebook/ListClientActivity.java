package com.example.carinadossantospereira.shoppingnotebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.carinadossantospereira.shoppingnotebook.adapters.ShopClientAdapter;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListClientActivity extends AppCompatActivity {
    private RecyclerView recyclerViewShopClient;
    private ShopClientAdapter adapter;
    private ArrayList<ShopClient> shopClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

        recyclerViewShopClient = findViewById(R.id.recyclerViewClients);

        shopClients = new ArrayList<>();
        adapter = new ShopClientAdapter(ListClientActivity.this, shopClients);
        recyclerViewShopClient.setAdapter(adapter);
        recyclerViewShopClient.setHasFixedSize(true);
        recyclerViewShopClient.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(ListClientActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("shopClient");

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                shopClients.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    ShopClient cli = data.getValue(ShopClient.class);
                    cli.setKey(data.getKey()); //Colocando key manualmente no objeto
                    shopClients.add(cli);
                }
                
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }
}
