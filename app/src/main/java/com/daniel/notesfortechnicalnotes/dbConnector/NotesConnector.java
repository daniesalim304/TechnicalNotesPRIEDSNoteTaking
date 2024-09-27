package com.daniel.notesfortechnicalnotes.dbConnector;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.daniel.notesfortechnicalnotes.model.NotesModel;

import java.util.List;

public class NotesConnector extends AndroidViewModel {
    private final notesDBConnector notesDatabase;
    private final LiveData<List<NotesModel>> allNotes;

    public NotesConnector(Application application) {
        super(application);
        notesDatabase = new notesDBConnector(application);
        allNotes = notesDatabase.getAllNotes();
    }

    public LiveData<List<NotesModel>> getAllNotes() {
        return notesDatabase.getAllNotes();
    }

    public void insert(String title, String description) {
        notesDatabase.insert(title, description);
    }

    public void update(int id, String title, String description) {
        notesDatabase.update(id, title, description);
    }

    public void delete(int id) {
        notesDatabase.delete(id);
    }
}