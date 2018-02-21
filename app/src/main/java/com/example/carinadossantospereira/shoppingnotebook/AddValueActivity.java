package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.example.carinadossantospereira.shoppingnotebook.config.ConfigurationFirebase;
import com.example.carinadossantospereira.shoppingnotebook.models.Purchase;
import com.example.carinadossantospereira.shoppingnotebook.models.Shop;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.example.carinadossantospereira.shoppingnotebook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import org.apache.commons.lang3.StringUtils;
import java.util.Date;

public class AddValueActivity extends AppCompatActivity  {
    private TextView tvName;
    private TextView tvDate;
    private EditText edValorCompra;
    private TextView tvLimiteCredito;
    private TextView tvTotalCompras;
    private Button btnSave;
    private FirebaseAuth mAuth;
    private DatabaseReference firebase;
    private User user;
    private Shop shop;
    private ValueEventListener valueEventListenerMensagem;
    private String idShopClient;
    private ShopClient shopClient;
    private ImageView ivClient;
    private Date currentTime;
    private boolean sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_value);

        createActionBar();
        createBtnSave();
        setReferencingComponents();
        getCurrentUser();

        Intent intent = getIntent();
        shopClient  = (ShopClient) intent.getSerializableExtra("shopClient");
        if(shopClient != null) {
            idShopClient = shopClient.getKey();
            loadDataClient(shopClient);
        }else{
            idShopClient = "";
        }

        currentTime = Calendar.getInstance().getTime();
        tvDate.setText(currentTime.toString());
        sum = true;
    }

    private void createActionBar(){
        Toolbar toolbar = findViewById(R.id.toolbar_aux);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compra");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void createBtnSave(){
        btnSave = findViewById(R.id.btnSaveSales);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSaleClient();
            }
        });
    }

    private void saveSaleClient(){

        edValorCompra.setError(null);

        String sValor = edValorCompra.getText().toString();
        sValor = sValor.replace(",",".");

        boolean cancel = false;

        View focusView = null;

        if (TextUtils.isEmpty(sValor)) {
            edValorCompra.setError("Informe um valor!");
            focusView = edValorCompra;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            final Double valor = Double.parseDouble(sValor);
            Purchase purchase = new Purchase(shop.getKey(), valor, sum);

            DatabaseReference referenceFirebase = ConfigurationFirebase.getFirebase();
            DatabaseReference refeFirebaseChild = referenceFirebase.child("shopClient").child(idShopClient);
            String nextId = refeFirebaseChild.push().getKey();
            refeFirebaseChild.child("shopClientValue").child(nextId).setValue(purchase);


            Toast.makeText(AddValueActivity.this, "Venda registrada com sucesso !", Toast.LENGTH_LONG);
            finish();
        }
    }

    private void setReferencingComponents(){
        tvName = findViewById(R.id.tvNameCliSales);
        tvDate = findViewById(R.id.tvDateSales);
        edValorCompra = findViewById(R.id.edValueSales);
        tvLimiteCredito = findViewById(R.id.tvLimitCreditSales);
        tvTotalCompras = findViewById(R.id.tvTotalSales);
        btnSave = findViewById(R.id.btnSaveSales);
        ivClient = findViewById(R.id.ivClienteSales);
    }

    private void getCurrentUser(){
        mAuth = ConfigurationFirebase.getFirebaseAutentication();

        if(mAuth != null){
            FirebaseUser userFire = mAuth.getCurrentUser();

            user = new User(userFire.getUid(), userFire.getEmail());
            getCurrentShop();
        }else{
            Toast.makeText(AddValueActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddValueActivity.this, LoginActivity.class);
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
                    Toast.makeText(AddValueActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddValueActivity.this, LoginActivity.class);
                    startActivity(intent);
                    mAuth.signOut();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddValueActivity.this, "Usuário não está logado!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddValueActivity.this, LoginActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();
            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );

    }

    private void loadDataClient(ShopClient shopClient){
        tvName.setText(shopClient.getName());
        tvLimiteCredito.setText(shopClient.getCreditLimit().toString());

        loadDataClientSales();

        Drawable imgCli = ContextCompat.getDrawable(this, R.drawable.ic_avatar);

        String urlGetImage = shopClient.getUrlImage();

        if (!StringUtils.isBlank(urlGetImage)) {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            builder.build().load(urlGetImage).into(ivClient);

            Picasso.with(this).load(urlGetImage)
                    .error(R.drawable.ic_avatar)
                    .into(ivClient);
        } else {
            ivClient.setImageDrawable(imgCli);
        }

    }

    private void loadDataClientSales(){
        DatabaseReference referenceFirebase = ConfigurationFirebase.getFirebase();
        DatabaseReference refeFirebaseChild = referenceFirebase.child("shopClient").child(idShopClient).child("shopClientValue");


        refeFirebaseChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Double valor = 0.0;
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Purchase purchase = data.getValue(Purchase.class);
                    if(purchase.getSum()){
                        valor = valor + purchase.getValue();
                    }else{
                        valor = valor - purchase.getValue();
                    }
                }

                tvTotalCompras.setText(valor.toString());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.rbMais:
                if(checked)
                    sum = true;

                break;
            case R.id.rbMenos:
                if(checked)
                    sum = false;

                break;
        }
    }


}
