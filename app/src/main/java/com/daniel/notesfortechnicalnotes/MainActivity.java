package com.daniel.notesfortechnicalnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.notesfortechnicalnotes.adapter.NotesAdapter;
import com.daniel.notesfortechnicalnotes.dbConnector.NotesConnector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NotesConnector notesConnector;
    private NotesAdapter notesAdapter;
    private ActivityResultLauncher<Intent> addNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesConnector = new ViewModelProvider(this).get(NotesConnector.class);

        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        TextView tvNoNotes = findViewById(R.id.tvNotesNotifications);

        notesAdapter = new NotesAdapter(new ArrayList<>(), this, notesConnector);
        rvNotes.setAdapter(notesAdapter);

        notesConnector.getAllNotes().observe(this, notes -> {
            notesAdapter.setNotes(notes);
            if (notes != null && !notes.isEmpty()) {
                rvNotes.setVisibility(View.VISIBLE);
                tvNoNotes.setVisibility(View.GONE);
            } else {
                rvNotes.setVisibility(View.GONE);
                tvNoNotes.setVisibility(View.VISIBLE);
            }
        });

        addNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Notes Added", Toast.LENGTH_SHORT).show();

                        notesConnector.getAllNotes().observe(this, notes -> {
                            notesAdapter.setNotes(notes);
                            notesAdapter.notifyDataSetChanged();
                            if (notes != null && !notes.isEmpty()) {
                                rvNotes.setVisibility(View.VISIBLE);
                                tvNoNotes.setVisibility(View.GONE);
                            } else {
                                rvNotes.setVisibility(View.GONE);
                                tvNoNotes.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
        );

        FloatingActionButton fabAdd = findViewById(R.id.btnAddNote);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNotes.class);
           addNoteLauncher.launch(intent);
        });
    }
}