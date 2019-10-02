package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.Models.ContactInfo;

public class ContactInfoActivity extends AppCompatActivity {

    private ContactInfo selectedContact;
    private TextView contactName,contactEmail,contactPhone,contactSocialLink,contactLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        initializeUI();
        Intent intent = getIntent();
        selectedContact = (ContactInfo)intent.getSerializableExtra("ContactInfo");
        initializeUI();
        contactName.setText(selectedContact.GetName());
        contactEmail.setText(selectedContact.GetEmail());
        contactPhone.setText(selectedContact.GetPhoneNumber());
        contactSocialLink.setText(selectedContact.GetSocialLink());
        contactLocation.setText(selectedContact.GetLocation());
        Linkify.addLinks(contactEmail,  Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(contactPhone, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(contactSocialLink,Linkify.WEB_URLS);
        Linkify.addLinks(contactLocation, Linkify.MAP_ADDRESSES);
    }

    private void initializeUI() {
        contactName = findViewById(R.id.contactName);
        contactEmail = findViewById(R.id.contactEmail);
        contactPhone = findViewById(R.id.contactPhone);
        contactSocialLink = findViewById(R.id.contactSocialLink);
        contactLocation = findViewById(R.id.contactLocation);
    }

}
