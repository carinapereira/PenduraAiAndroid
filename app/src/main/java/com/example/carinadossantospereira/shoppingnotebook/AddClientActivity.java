package com.example.carinadossantospereira.shoppingnotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
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
import com.example.carinadossantospereira.shoppingnotebook.helper.BitmapUtils;
import com.example.carinadossantospereira.shoppingnotebook.models.Shop;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;
import com.example.carinadossantospereira.shoppingnotebook.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;

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
    private ShopClient shopClient;
    private String idShopClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        createActionBar();
        createBtnSave();
        createBtnPickPhoto();
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

    }

    private void loadDataClient(ShopClient shopClient){
        etName.setText(shopClient.getName());
        etCpfCnpj.setText(shopClient.getCpf());
        etPhone.setText(shopClient.getPhone());
        etCreditLimit.setText(shopClient.getCreditLimit().toString());


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

        final String name = etName.getText().toString();
        final String cpfCnpj = etCpfCnpj.getText().toString();
        final String phone = etPhone.getText().toString();
        String sCreditLimit = etCreditLimit.getText().toString();
        final Double creditLimit = Double.parseDouble(sCreditLimit);

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

            if (ivClient != null && ivClient.getDrawable() != null) {
               /* Drawable imgLogo = ivClient.getDrawable();
                Drawable imgPlaceHolder = ContextCompat.getDrawable(this, R.drawable.ic_avatar);

                Bitmap bitmapSol = ((BitmapDrawable) imgLogo).getBitmap();
                Bitmap bitmapHolder = ((BitmapDrawable) imgPlaceHolder).getBitmap();

                String imgStr = BitmapUtils.bitMapToString(bitmapSol);
                String imgStrHolder = BitmapUtils.bitMapToString(bitmapHolder);*/

                // Get the data from an ImageView as bytes
                ivClient.setDrawingCacheEnabled(true);
                ivClient.buildDrawingCache();
                Bitmap bitmap = ivClient.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();


                FirebaseStorage referenceStorage = ConfigurationFirebase.getFirebaseStorage();
                StorageReference storageRef = referenceStorage.getReferenceFromUrl("gs://shopnotebookdroid.appspot.com/");

                // Create a reference to "mountains.jpg"
                String nomeimagem = name + ".jpg";
                StorageReference mountainsRef = storageRef.child(nomeimagem);

                // Create a reference to 'images/mountains.jpg'
                StorageReference mountainImagesRef = storageRef.child("images/" + nomeimagem);

                // While the file names are the same, the references point to different files
                mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false


                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                        saveShopClient(name,cpfCnpj,phone,creditLimit,downloadUrl);
                    }
                });


            }else{
                saveShopClient(name,cpfCnpj,phone,creditLimit, "");
            }

            progress.setVisibility(View.INVISIBLE);
        }
    }

    public void saveShopClient(String name, String cpfCnpj, String phone, Double creditLimit, String urlImage){
        ShopClient shopClient = new ShopClient(shop.getKey(),name,cpfCnpj,phone,creditLimit, urlImage);
        shopClient.salvar();
        Toast.makeText(AddClientActivity.this, "Cliente cadastrado com sucesso !", Toast.LENGTH_LONG);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            Picasso.with(AddClientActivity.this).load(selectedImage).into(ivClient);
        }
    }
}
