package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserTypeActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button btnClient;
    private Button btnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        btnShop = findViewById(R.id.btnShop);
        btnShop.setOnClickListener(this);

        btnClient = findViewById(R.id.btnClient);
        btnClient.setOnClickListener(this);
    }

    private void OpenCreateAccount(){
        Intent intent = new Intent(UserTypeActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    private void OpenCreateAccountClient(){
        Toast.makeText(UserTypeActivity.this, "Disponível na próxima versão !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShop:
                OpenCreateAccount();
                break;

            case R.id.btnClient:
                OpenCreateAccountClient();
                break;
        }
    }
}
