package com.example.lab4;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab4.Models.ContactInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateContactActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    ContactInfo updatingContact;
    Button updateContactBtn;
    private TextView contactName,contactEmail,contactPhone,contactSocialLink,contactLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        initializeViews();
        initFirebase();
        Intent intent = getIntent();
        updatingContact = (ContactInfo)intent.getSerializableExtra("ContactInfo");
        contactName.setText(updatingContact.GetName());
        contactEmail.setText(updatingContact.GetEmail());
        contactPhone.setText(updatingContact.GetPhoneNumber());
        contactSocialLink.setText(updatingContact.GetSocialLink());
        contactLocation.setText(updatingContact.GetLocation());

        updateContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.SetName(contactName.getText().toString());
                contactInfo.SetEmail(contactEmail.getText().toString());
                contactInfo.SetPhoneNumber(contactPhone.getText().toString());
                contactInfo.SetScoialLink(contactSocialLink.getText().toString());
                contactInfo.SetLocation(contactLocation.getText().toString());
                DatabaseReference contactsRef = mDatabaseReference.child(updatingContact.GetId());
                contactsRef.child("name").setValue(contactInfo.GetName());
                contactsRef.child("location").setValue(contactInfo.GetLocation());
                contactsRef.child("email").setValue(contactInfo.GetEmail());
                contactsRef.child("phoneNumber").setValue(contactInfo.GetPhoneNumber());
                contactsRef.child("socialLink").setValue(contactInfo.GetSocialLink());
                startActivity(new Intent(UpdateContactActivity.this,DashboardActivity.class));
            }
        });

    }

    private void initializeViews() {
        updateContactBtn = findViewById(R.id.updateContactSubmit);
        contactName = findViewById(R.id.selectName);
        contactEmail = findViewById(R.id.selectEmail);
        contactPhone = findViewById(R.id.selectPhoneNumber);
        contactSocialLink = findViewById(R.id.selectSocialLink);
        contactLocation = findViewById(R.id.selectLocation);
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
}