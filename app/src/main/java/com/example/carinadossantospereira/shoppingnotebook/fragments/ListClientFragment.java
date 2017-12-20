package com.example.carinadossantospereira.shoppingnotebook.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.carinadossantospereira.shoppingnotebook.R;
import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
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
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> clients;
    private DatabaseReference firebase;
    private FirebaseAuth mAuth;

    public ListClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        clients = new ArrayList<>();
        clients.add("carina pereira");
        clients.add("bruno campos");
        clients.add("silvano castro");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_client, container, false);

        listView = view.findViewById(R.id.lvClients);
        adapter = new ArrayAdapter(
            getActivity(),
            R.layout.list_client,
            clients
        );

        listView.setAdapter(adapter);
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

        return view;
    }

}
