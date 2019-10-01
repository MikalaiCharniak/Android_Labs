package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab4.Models.ContactInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    public ContactInfo selectedItem;
    private Button addContact;
    private Button exitBtn;
    private ListView listView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private List<ContactInfo> contacts = new ArrayList<ContactInfo>();
    ContactInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializeUI();
        initFirebase();
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddContactActivity.class));
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedItem = contacts.get(position);
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this, ContactInfoActivity.class);
                intent.putExtra("ContactInfo",selectedItem);
                startActivity(intent);
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
        addEventFirebaseListener();
    }

    private void initializeUI() {
        addContact = findViewById(R.id.addContact);
        exitBtn = findViewById(R.id.exitBtn);
        listView = findViewById(R.id.list);
    }

    private void addEventFirebaseListener() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    //если данные в БД меняются
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (contacts.size() > 0) {
                            contacts.clear();
                        }
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            ContactInfo user = postSnapshot.getValue(ContactInfo.class);
                            contacts.add(user);
                        }

                        adapter = new ContactInfoAdapter(DashboardActivity.this,R.layout.item, (ArrayList<ContactInfo>) contacts,
                                mFirebaseDatabase,mDatabaseReference);
                        listView.setAdapter(adapter);
                        Toast.makeText(
                                getApplicationContext(), "" +
                                        contacts.size(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });
    }
}
