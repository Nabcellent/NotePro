package com.example.notepro;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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

        if(title == null || title.isEmpty()) {
            titleInput.setError("Title is required.");
            return;
        }
    }
}