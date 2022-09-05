package com.example.notepro.pages.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepro.R;
import com.example.notepro.models.Note;
import com.example.notepro.utils.Helpers;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class UpsertNoteActivity extends AppCompatActivity {
    EditText titleInput, contentInput;
    ImageButton saveBtn;
    TextView pageTitle, deleteBtn;
    String title, content, docId;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upsert_note);

        pageTitle = findViewById(R.id.page_title);

        titleInput = findViewById(R.id.title);
        contentInput = findViewById(R.id.content);
        saveBtn = findViewById(R.id.save_note_btn);
        deleteBtn = findViewById(R.id.delete_btn);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        //  Check if is an update action
        if (docId != null && !docId.isEmpty()) isUpdate = true;

        if (isUpdate) {
            titleInput.setText(title);
            contentInput.setText(content);

            pageTitle.setText("Update Note");

            deleteBtn.setVisibility(View.VISIBLE);
        }

        saveBtn.setOnClickListener(v -> saveNote());
        deleteBtn.setOnClickListener(v -> deleteNote());
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
        DocumentReference docRef;

        if (isUpdate) {
            docRef = Helpers.getCollectionReferenceForNotes().document(docId);
        } else {
            docRef = Helpers.getCollectionReferenceForNotes().document();
        }

        docRef.set(note).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String msgStatus = isUpdate ? "created" : "updated";
                Helpers.showToast(this, "Note " + msgStatus + " successfully!");
                finish();
            } else {
                String msgStatus = isUpdate ? "create" : "update";
                Helpers.showToast(this, "Failed to " + msgStatus + " note!");
            }
        });
    }

    void deleteNote() {
        DocumentReference docRef = Helpers.getCollectionReferenceForNotes().document(docId);

        docRef.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Helpers.showToast(this, "Note destroyed successfully!");
                finish();
            } else {
                Helpers.showToast(this, "Failed to destroy note!");
            }
        });
    }
}