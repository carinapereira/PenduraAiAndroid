package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.example.carinadossantospereira.shoppingnotebook.models.Shop;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.example.carinadossantospereira.shoppingnotebook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.*;

public class AddClientActivity extends AppCompatActivity {
    private TextInputEditText etName;
    private TextInputEditText etCpfCnpj;
    private TextInputEditText etPhone;
    private TextInputEditText etCreditLimit;
    private Button btnSave;
    private ImageView ivClient;
    private ImageButton ibPickPhoto;
    private ProgressBar progress;
    private static final int REQUEST_PICK_IMAGE = 110;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private User user;
    private Shop shop;
    private ValueEventListener valueEventListenerMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        createActionBar();
        createBtnSave();
        createBtnPickPhoto();
        setReferencingComponents();
        getCurrentUser();

    }

    private void getCurrentUser(){
        mAuth = ConfigurationFirebase.getFirebaseAutentication();

        if(mAuth != null){
            FirebaseUser userFire = mAuth.getCurrentUser();

            user = new User(userFire.getUid(), userFire.getEmail());
            getCurrentShop();
        }else{
            Toast.makeText(AddClientActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddClientActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getCurrentShop(){

        firebase = ConfigurationFirebase.getFirebase()
                .child("shop")
                .child( user.getUid());

        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shop = dataSnapshot.getValue( Shop.class );
                shop.setKey(dataSnapshot.getKey());
                if (shop == null){
                    Toast.makeText(AddClientActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddClientActivity.this, LoginActivity.class);
                    startActivity(intent);
                    mAuth.signOut();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddClientActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddClientActivity.this, LoginActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();
            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );

    }

    private void createActionBar(){
        Toolbar toolbar = findViewById(R.id.toolbar_aux);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Novo Cliente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setReferencingComponents(){
        etName = findViewById(R.id.etName);
        etCpfCnpj = findViewById(R.id.etCpfCnpj);
        etPhone = findViewById(R.id.etPhone);
        etCreditLimit = findViewById(R.id.etCreditLimit);
        ivClient = findViewById(R.id.ivClient);
        progress = findViewById(R.id.pbNewClient);
        progress.setVisibility(View.INVISIBLE);
    }

    private void createBtnSave(){
        btnSave = findViewById(R.id.btnSaveNewCli);
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewClient();
            }
        });
    }

    private void createBtnPickPhoto(){
        ibPickPhoto = findViewById(R.id.ibPickPhoto);
        ibPickPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });
    }

    private void saveNewClient(){

        etName.setError(null);
        etCpfCnpj.setError(null);
        etPhone.setError(null);
        etCreditLimit.setError(null);

        String name = etName.getText().toString();
        String cpfCnpj = etCpfCnpj.getText().toString();
        String phone = etPhone.getText().toString();
        String sCreditLimit = etCreditLimit.getText().toString();
        Double creditLimit = Double.parseDouble(sCreditLimit);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            etName.setError("Informe o nome!");
            focusView = etName;
            cancel = true;
        }

        if (TextUtils.isEmpty(cpfCnpj)) {
            etCpfCnpj.setError("Informe o CPF/CNPJ!");
            focusView = etCpfCnpj;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Informe o celular!");
            focusView = etPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(sCreditLimit)) {
            etCreditLimit.setError("Informe o limite de crédito!");
            focusView = etCreditLimit;
            cancel = true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            progress.setVisibility(View.VISIBLE);
            ShopClient shopClient = new ShopClient(shop.getKey(),name,cpfCnpj,phone,creditLimit);
            shopClient.salvar();
            Toast.makeText(AddClientActivity.this, "Cliente cadastrado com sucesso !", Toast.LENGTH_LONG);
            finish();
            progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
