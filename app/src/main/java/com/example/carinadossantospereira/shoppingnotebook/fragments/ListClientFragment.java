package com.example.carinadossantospereira.shoppingnotebook.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carinadossantospereira.shoppingnotebook.AddClientActivity;
import com.example.carinadossantospereira.shoppingnotebook.ListClientActivity;
import com.example.carinadossantospereira.shoppingnotebook.R;
import com.example.carinadossantospereira.shoppingnotebook.adapters.ShopClientAdapter;
import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    //private ListView listView;
    //private ArrayAdapter adapter;
   // private ArrayList<String> clients;
   // private DatabaseReference firebase;
    //private FirebaseAuth mAuth;

    private RecyclerView recyclerViewShopClient;
    private ShopClientAdapter adapter;
    private ArrayList<ShopClient> shopClients;
    private View view;


    public ListClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_client, container, false);
        createActionFabAddClient();
       /* clients = new ArrayList<>();
        clients.add("carina pereira");
        clients.add("bruno campos");
        clients.add("silvano castro");


        listView = view.findViewById(R.id.lvClients);
        adapter = new ArrayAdapter(
            getActivity(),
            R.layout.list_client,
            clients
        );

        listView.setAdapter(adapter);*/
        /*FirebaseUser user =  mAuth.getCurrentUser();

        //Preferencias preferencias = Preferencias(getActivity());
        //String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfigurationFirebase.getFirebase().child("shopClient").child("teste");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clients.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    ShopClient client = dados.getValue(ShopClient.class);
                    clients.add(client.getName());
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


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
