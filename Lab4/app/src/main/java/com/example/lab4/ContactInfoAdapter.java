package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4.Models.ContactInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


class ContactInfoAdapter extends ArrayAdapter<ContactInfo> {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<ContactInfo> noteList;
    private Context currentContext;


    ContactInfoAdapter(Context context, int resource, ArrayList<ContactInfo> notes,
                       FirebaseDatabase mFirebaseDatabase, DatabaseReference mDatabaseReference) {
        super(context, resource, notes);
        this.noteList = notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.mFirebaseDatabase = mFirebaseDatabase;
        this.mDatabaseReference = mDatabaseReference;
        this.currentContext = context;
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
                mDatabaseReference
                        .child(note.GetId())
                        .removeValue();
                Toast.makeText(getContext(), "Removed" + note.GetId(), Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentContext, UpdateContactActivity.class);
                intent.putExtra("ContactInfo",note);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                currentContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private String formatValue(int count, String unit) {
        return String.valueOf(count) + " " + unit;
    }

    private class ViewHolder {
        final Button updateButton, removeButton;
        final TextView nameView;

        ViewHolder(View view) {
            updateButton = (Button) view.findViewById(R.id.updateButton);
            removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
        }
    }
}
