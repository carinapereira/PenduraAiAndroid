package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carinadossantospereira.shoppingnotebook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtPassword;
    private TextView txtConfirmPassword;
    private TextView txtEmail;
    private Button btnSave;
    private FirebaseAuth mAuth;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        txtEmail = findViewById(R.id.eTAccountEmail);
        txtPassword = findViewById(R.id.eTAccountPassword);
        txtConfirmPassword = findViewById(R.id.eTAccountConfPassword);
        btnSave = findViewById(R.id.btnAccountSave);
        progress = findViewById(R.id.progressBarAccount);

        progress.setVisibility(View.INVISIBLE);

    }

    private void CreateAccountShop(){
        if(!txtEmail.getText().toString().isEmpty() &&
                !txtPassword.getText().toString().isEmpty()){

            if(isIgualPassword()) {

                progress.setVisibility(View.VISIBLE);

                User u = new User(txtPassword.getText().toString(), txtEmail.getText().toString());


                mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getUid())
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(
                                            getBaseContext(),
                                            "Erro ao criar conta!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            "Conta criada com sucesso!",
                                            Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                progress.setVisibility(View.INVISIBLE);
                            }
                        });
            }else{
                Toast.makeText(getBaseContext(),"As senhas devem serem iguais!",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(
                    getBaseContext(),
                    "Digite os dados para criar a conta",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isIgualPassword(){
        if (txtPassword.getText() == txtConfirmPassword.getText())
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAccountSave:
                CreateAccountShop();
                break;
        }

    }
}
