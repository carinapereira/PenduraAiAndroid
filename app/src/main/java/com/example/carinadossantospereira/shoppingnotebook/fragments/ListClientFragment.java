package com.example.carinadossantospereira.shoppingnotebook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.carinadossantospereira.shoppingnotebook.AddClientActivity;
import com.example.carinadossantospereira.shoppingnotebook.R;
import com.example.carinadossantospereira.shoppingnotebook.adapters.ShopClientAdapter;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListClientFragment extends Fragment {

    private ShopClientAdapter adapter;
    private ArrayList<ShopClient> shopClients;
    private View view;
    private RecyclerView recyclerViewShopClient;

    public ListClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_client, container, false);
        createActionFabAddClient();


        recyclerViewShopClient = view.findViewById(R.id.recyclerViewClientsFrag);

        shopClients = new ArrayList<>();
        adapter = new ShopClientAdapter(view.getContext(), shopClients);
        recyclerViewShopClient.setAdapter(adapter);
        recyclerViewShopClient.setHasFixedSize(true);
        recyclerViewShopClient.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FirebaseApp.initializeApp(view.getContext());
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

        return view;
    }

    private void createActionFabAddClient(){
        FloatingActionButton fab = view.findViewById(R.id.fab_add_client);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view.getContext(), AddClientActivity.class);
                startActivity(intent);
            }
        });
    }

}
