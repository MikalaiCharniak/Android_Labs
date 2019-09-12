package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


class NoteAdapter extends ArrayAdapter<Note> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Note> noteList;

    NoteAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
        this.noteList = notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Note note = noteList.get(position);

        viewHolder.nameView.setText(note.getText());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteList.remove(note);
                notifyDataSetChanged();
                boolean result = JSONHelper.exportToJSON(getContext(), noteList);
                if (result) {
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Note remove", Toast.LENGTH_LONG).show();
                }
            }
        });
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setText(viewHolder.nameView.getText().toString());
                boolean result = JSONHelper.exportToJSON(getContext(), noteList);
                if (result) {
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }

    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }
    private class ViewHolder {
        final Button addButton, removeButton;
        final TextView nameView;
        ViewHolder(View view){
            addButton = (Button) view.findViewById(R.id.addButton);
            removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
        }
    }
}
