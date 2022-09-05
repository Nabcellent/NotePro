package com.example.notepro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepro.models.Note;
import com.example.notepro.pages.notes.UpsertNoteActivity;
import com.example.notepro.utils.Helpers;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleText.setText(note.title);
        holder.contentText.setText(note.content);
        holder.timestampText.setText(Helpers.timestampToString(note.timestamp));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpsertNoteActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);

            String uid = this.getSnapshots().getSnapshot(position).getId();

            intent.putExtra("docId", uid);

            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_note_item, parent, false);

        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, contentText, timestampText;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.title);
            contentText = itemView.findViewById(R.id.content);
            timestampText = itemView.findViewById(R.id.timestamp);
        }
    }
}
