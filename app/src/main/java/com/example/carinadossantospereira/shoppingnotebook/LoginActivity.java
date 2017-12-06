package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carinadossantospereira.shoppingnotebook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etSenha;
    private Button btnEntrar;
    private Button btnCriarConta;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar progress;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editTextEmail);
        etSenha = findViewById(R.id.editTextSenha);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(this);

        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnCriarConta.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            FirebaseUser user = firebaseAuth.getCurrentUser();

            if(user != null){
                Toast.makeText(
                        getBaseContext(),
                        "Você já está logado!",
                        Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(
                        getBaseContext(),
                        "Você não está logado ainda!",
                        Toast.LENGTH_LONG).show();
            }
            }
        };
    }

    private void login(){
        if(!etEmail.getText().toString().isEmpty() &&
                !etSenha.getText().toString().isEmpty()){

            progress.setVisibility(View.VISIBLE);

            User u = new User(etEmail.getText().toString(), etSenha.getText().toString());

            Log.d("TAG","u: "+u.toString());

            mAuth.signInWithEmailAndPassword(u.getUid(),u.getEmail())
                .addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG","task: "+task.isSuccessful());
                    if(!task.isSuccessful()){
                        Toast.makeText(
                                getBaseContext(),
                                "Usuário NÃO autenticado!",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(
                                getBaseContext(),
                                "Usuário autenticado com sucesso!",
                                Toast.LENGTH_LONG).show();

                        //redirecionar para tela de abertura
                    }

                    progress.setVisibility(View.INVISIBLE);

                    }
                });
        }else{
            Toast.makeText(
                    getBaseContext(),
                    "Digite os dados para entrar no App",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void createAccount(){
        if(!etEmail.getText().toString().isEmpty() &&
                !etSenha.getText().toString().isEmpty()){

            progress.setVisibility(View.VISIBLE);

            User u = new User(etEmail.getText().toString(), etSenha.getText().toString());

            Log.d("TAG","u: "+u.toString());

            mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getUid())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG","task: "+task.isSuccessful());
                    if(!task.isSuccessful()){
                        Toast.makeText(
                                getBaseContext(),
                                "Erro ao criar conta!",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(
                                getBaseContext(),
                                "Conta criada com sucesso!",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this,ListClientActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    progress.setVisibility(View.INVISIBLE);
                    }
                });
        }else{
            Toast.makeText(
                    getBaseContext(),
                    "Digite os dados para criar a conta",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEntrar:
                login();
                break;

            case R.id.btnCriarConta:
                createAccount();
                break;
        }
    }
}