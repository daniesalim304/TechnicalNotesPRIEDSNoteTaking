package com.daniel.notesfortechnicalnotes.dbConnector;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.daniel.notesfortechnicalnotes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class notesDBConnector {
    private final databaseConnector notesDatabase;

    public notesDBConnector(Application application){
        notesDatabase = new databaseConnector(application);
    }

    public LiveData<List<NotesModel>> getAllNotes() {
        MutableLiveData<List<NotesModel>> notesLiveData = new MutableLiveData<>();
        Cursor cursor = notesDatabase.getAllNotes();
        List<NotesModel> notesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("notesId"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("notesTitle"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("notesDescription"));
                notesList.add(new NotesModel(id, title, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        notesLiveData.postValue(notesList);
        return notesLiveData;
    }

    public void insert(String title, String description) {
        notesDatabase.insertNote(title, description);
    }

    public void update(int id, String title, String description) {
        notesDatabase.updateNote(id, title, description);
    }

    public void delete(int id) {
        notesDatabase.deleteNote(id);
    }
}