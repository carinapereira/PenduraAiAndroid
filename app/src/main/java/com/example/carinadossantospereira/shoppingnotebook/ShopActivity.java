package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.example.carinadossantospereira.shoppingnotebook.models.Shop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView etCompany;
    private TextView etCnpj;
    private TextView etPhone;
    private Button btnSave;
    private String userId;
    private ProgressBar progress;
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        etCompany = findViewById(R.id.etShopCompany);
        etCnpj = findViewById(R.id.etShopCnpj);
        etPhone = findViewById(R.id.etShopPhone);

        progress = findViewById(R.id.progressBarShop);
        progress.setVisibility(View.INVISIBLE);

        btnSave = findViewById(R.id.btnShopSave);
        btnSave.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userid");
        autentication = ConfigurationFirebase.getFirebaseAutentication();

    }

    private void saveShop(){
        String company = etCompany.getText().toString();
        String phone = etPhone.getText().toString();
        String cnpj = etCnpj.getText().toString();

        if(!company.isEmpty() && !phone.isEmpty() && !cnpj.isEmpty()){
            progress.setVisibility(View.VISIBLE);
            Shop shop = new Shop(userId,company,cnpj,phone);
            shop.salvar();
            autentication.signOut();
            Toast.makeText(ShopActivity.this, "Conta cadastrada com sucesso !", Toast.LENGTH_LONG);
            Intent intent = new Intent(ShopActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

            progress.setVisibility(View.INVISIBLE);
        }else{
            Toast.makeText(ShopActivity.this, "Preencha as informações !", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShopSave:
                saveShop();
                break;
        }
    }
}
