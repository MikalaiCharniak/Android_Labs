package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter adapter;
    private EditText nameText;
    private List<Note> notes;
    private CalendarView calendar;
    private String chosenDate;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText) findViewById(R.id.nameText);
        notes = new ArrayList<Note>();
        listView = (ListView) findViewById(R.id.list);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        chosenDate = format.format(date);


        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        adapter = new NoteAdapter(this,R.layout.item ,(ArrayList<Note>) notes);
        listView.setAdapter(adapter);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                chosenDate = year + "-" + month + "-" + dayOfMonth;
                openCurrentNotes();
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void openCurrentNotes() {
        ArrayList<Note> resultNotes = checkCurrentDate();
        if (resultNotes != null) {
            adapter = new NoteAdapter(this,R.layout.item ,(ArrayList<Note>) resultNotes);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Current notes", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Can not open", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Note> checkCurrentDate() {
        ArrayList<Note> resultNotes = new ArrayList<Note>();
        notes = JSONHelper.importFromJSON(this);
        for (Note temp : notes) {
            if (chosenDate.equals(temp.getDate())) {
                resultNotes.add(temp);
            }
        }
        return resultNotes;
    }

}

