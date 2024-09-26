package com.daniel.notesfortechnicalnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.notesfortechnicalnotes.adapter.NotesAdapter;
import com.daniel.notesfortechnicalnotes.dbConnector.NotesConnector;
import com.daniel.notesfortechnicalnotes.model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesConnector notesConnector;
    private NotesAdapter notesAdapter;
    private ActivityResultLauncher<Intent> addNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(new ArrayList<>());
        rvNotes.setAdapter(notesAdapter);

        notesConnector = new ViewModelProvider(this).get(NotesConnector.class);
        notesConnector.getAllNotes().observe(this, notes -> notesAdapter.setNotes(notes));

        addNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Show confirmation toast
                        Toast.makeText(this, "Notes Added", Toast.LENGTH_SHORT).show();

                        // Optionally force refresh, though LiveData should handle this automatically
                        notesConnector.getAllNotes().observe(this, notes -> {
                            notesAdapter.setNotes(notes);  // Pass the updated list to the adapter
                            notesAdapter.notifyDataSetChanged();  // Explicitly notify the adapter
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