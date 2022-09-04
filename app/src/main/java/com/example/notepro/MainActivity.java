package com.example.notepro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepro.models.Note;
import com.example.notepro.pages.notes.ShowNoteActivity;
import com.example.notepro.utils.Helpers;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recycler_view);
        menuBtn = findViewById(R.id.menu_btn);

        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(this, ShowNoteActivity.class)));
        menuBtn.setOnClickListener(v -> showMenu());

        setupRecyclerView();
    }

    void showMenu() {

    }

    void setupRecyclerView() {
        Query qry = Helpers.getCollectionReferenceForNotes()
                           .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(qry, Note.class)
                                                                                             .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteAdapter.notifyDataSetChanged();
    }
}