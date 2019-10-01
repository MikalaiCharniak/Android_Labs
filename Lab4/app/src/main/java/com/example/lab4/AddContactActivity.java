package com.example.lab4;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;

import com.example.lab4.Models.ContactInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactActivity extends AppCompatActivity  {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    Button addContactBtn;
    EditText input_name, input_email,input_phone,input_socialLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvitity_add_contact);

        initializeViews();
        initFirebase();

       addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.SetName(input_name.getText().toString());
                contactInfo.SetEmail(input_email.getText().toString());
                contactInfo.SetPhoneNumber(input_phone.getText().toString());
                contactInfo.SetScoialLink(input_socialLink.getText().toString());
                DatabaseReference contactsRef = mDatabaseReference.child("contacts");
                contactsRef.push().setValue(contactInfo);
                startActivity(new Intent(AddContactActivity.this,DashboardActivity.class));
            }
        });
    }

    private void initFirebase() {
        //инициализируем наше приложение для Firebase согласно параметрам в google-services.json
        // (google-services.json - файл, с настройками для firebase, кот. мы получили во время регистрации)
        FirebaseApp.initializeApp(this);
        //получаем точку входа для базы данных
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //получаем ссылку для работы с базой данных
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void initializeViews() {
        addContactBtn = findViewById(R.id.addContactSubmit);
        input_email = findViewById(R.id.Email);
        input_name = findViewById(R.id.Name);
        input_phone = findViewById(R.id.PhoneNumber);
        input_socialLink = findViewById(R.id.SocialLink);
    }
}
