package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4.Models.ContactInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


class ContactInfoAdapter extends ArrayAdapter<ContactInfo> {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<ContactInfo> noteList;


    ContactInfoAdapter(Context context, int resource, ArrayList<ContactInfo> notes,
                       FirebaseDatabase mFirebaseDatabase, DatabaseReference mDatabaseReference) {
        super(context, resource, notes);
        this.noteList = notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.mFirebaseDatabase = mFirebaseDatabase;
        this.mDatabaseReference = mDatabaseReference;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ContactInfo note = noteList.get(position);

        viewHolder.nameView.setText(note.GetName());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteList.remove(note);
                notifyDataSetChanged();
                mDatabaseReference.child("contacts")
                        .child(note.GetId())
                        .removeValue();
                Toast.makeText(getContext(), "Removed", Toast.LENGTH_LONG).show();
            }
        });
//        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                note.setText(viewHolder.nameView.getText().toString());
//                boolean result = JSONHelper.exportToJSON(getContext(), noteList);
//                if (result) {
//                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        return convertView;
    }

    private String formatValue(int count, String unit) {
        return String.valueOf(count) + " " + unit;
    }

    private class ViewHolder {
        final Button addButton, removeButton;
        final TextView nameView;

        ViewHolder(View view) {
            addButton = (Button) view.findViewById(R.id.addButton);
            removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
        }
    }
}
