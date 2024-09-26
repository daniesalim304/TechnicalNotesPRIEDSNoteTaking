package com.daniel.notesfortechnicalnotes.dbConnector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseConnector extends SQLiteOpenHelper {
    private static final String DatabaseName = "notesDatabase";
    private static final String TableNotes = "notes";
    private static final String NotesId = "notesId", NotesTitle = "notesTitle", NotesDescription = "notesDescription";

    public databaseConnector(Context context){
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TableNotes + "("
                + NotesId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NotesTitle + " TEXT,"
                + NotesDescription + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableNotes);
        onCreate(db);
    }

    public long insertNote(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTitle, title);
        values.put(NotesDescription, description);
        long id = db.insert(TableNotes, null, values);
        db.close();
        return id;
    }

    public int updateNote(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTitle, title);
        values.put(NotesDescription, description);
        int rowsAffected = db.update(TableNotes, values, NotesId + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableNotes, NotesId + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TableNotes + " ORDER BY " + NotesId + " DESC", null);
    }
}
