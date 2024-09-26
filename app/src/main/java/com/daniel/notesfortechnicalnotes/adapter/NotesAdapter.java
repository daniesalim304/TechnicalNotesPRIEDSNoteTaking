package com.daniel.notesfortechnicalnotes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.notesfortechnicalnotes.R;
import com.daniel.notesfortechnicalnotes.model.NotesModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<NotesModel> notesList;

    public NotesAdapter(List<NotesModel> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NotesModel note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());

        holder.itemView.setOnLongClickListener(v -> {
            Log.e("ada", "ada");
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotes(List<NotesModel> notes) {
        this.notesList = notes;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNotes);
            description = itemView.findViewById(R.id.contentNotes);
        }
    }

}
