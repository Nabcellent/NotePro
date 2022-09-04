package com.example.notepro.pages.notes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepro.R;
import com.example.notepro.models.Note;
import com.example.notepro.utils.Helpers;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ShowNoteActivity extends AppCompatActivity {
    EditText titleInput, contentInput;
    ImageButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        titleInput = findViewById(R.id.title);
        contentInput = findViewById(R.id.content);
        saveBtn = findViewById(R.id.save_note_btn);

        saveBtn.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();

        if (title == null || title.isEmpty()) {
            titleInput.setError("Title is required.");
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setTimestamp(Timestamp.now());

        saveToFirebase(note);
    }

    void saveToFirebase(Note note) {
        DocumentReference documentReference = Helpers.getCollectionReferenceForNotes().document();

        documentReference.set(note).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Helpers.showToast(this, "Note added successfully!");
                finish();
            } else {
                Helpers.showToast(this, "Failed to add note!");
            }
        });
    }
}