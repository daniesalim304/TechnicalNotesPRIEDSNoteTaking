package com.daniel.notesfortechnicalnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.daniel.notesfortechnicalnotes.dbConnector.NotesConnector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNotes extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private NotesConnector notesConnector;
    private int noteId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        notesConnector = new ViewModelProvider(this).get(NotesConnector.class);

        if (getIntent().hasExtra("noteId")) {
            noteId = getIntent().getIntExtra("noteId", -1);
            etTitle.setText(getIntent().getStringExtra("noteTitle"));
            etDescription.setText(getIntent().getStringExtra("noteDescription"));
        }

        FloatingActionButton fabSave = findViewById(R.id.fab_save_note);
        fabSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty()) {
            Intent resultIntent = new Intent();

            if (noteId == -1) {
                notesConnector.insert(title, description);
            } else {
                notesConnector.update(noteId, title, description);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            if (title.isEmpty()) {
                etTitle.setError("Title is required");
            }
            if (description.isEmpty()) {
                etDescription.setError("Description is required");
            }
        }
    }



}
