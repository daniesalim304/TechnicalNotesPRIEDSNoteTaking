package com.daniel.notesfortechnicalnotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.notesfortechnicalnotes.AddNotes;
import com.daniel.notesfortechnicalnotes.R;
import com.daniel.notesfortechnicalnotes.dbConnector.NotesConnector;
import com.daniel.notesfortechnicalnotes.model.NotesModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<NotesModel> notesList;
    private Context context;
    private NotesConnector notesConnector;

    public NotesAdapter(List<NotesModel> notesList, Context context, NotesConnector notesConnector) {
        this.notesList = notesList;
        this.context = context;
        this.notesConnector = notesConnector;
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

        holder.itemView.setOnClickListener(v -> {
            showOptionsDialog(note, position);
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private void showOptionsDialog(NotesModel note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Option");

        String[] options = {"Edit", "Delete"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                // Edit option selected
                Intent intent = new Intent(context, AddNotes.class);
                intent.putExtra("noteId", note.getId());
                intent.putExtra("noteTitle", note.getTitle());
                intent.putExtra("noteDescription", note.getDescription());
                context.startActivity(intent);
            } else if (which == 1) {
                // Show confirmation dialog before deleting
                DialogInterface.OnClickListener yesNoDialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Delete the note using notesConnector
                                notesConnector.delete(note.getId());
                                // Optionally notify the adapter that the item is removed
                                notesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, notesList.size());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // No action on cancel
                                break;
                        }
                    }
                };

                AlertDialog.Builder builderDialogYesNo = new AlertDialog.Builder(context);
                builderDialogYesNo.setMessage("Are you sure?")
                        .setPositiveButton("Yes", yesNoDialog)
                        .setNegativeButton("No", yesNoDialog).show();
            }
        });

        builder.show();
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
