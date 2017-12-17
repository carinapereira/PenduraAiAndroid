package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserTypeActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button btnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        btnShop = findViewById(R.id.btnShop);
        btnShop.setOnClickListener(this);
    }

    private void OpenCreateAccount(){
        Intent intent = new Intent(UserTypeActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShop:
                OpenCreateAccount();
                break;
        }
    }
}
