package com.example.notepro.pages.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepro.R;
import com.example.notepro.utils.Helpers;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText emailInput, passwordInput, passwordConfirmationInput;
    Button registerBtn;
    ProgressBar progressBar;
    TextView viewLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        passwordConfirmationInput = findViewById(R.id.password_confirmation);
        registerBtn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progress_bar);
        viewLoginBtn = findViewById(R.id.view_login_btn);

        registerBtn.setOnClickListener(view -> register());
        viewLoginBtn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    void register() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordConfirmation = passwordConfirmationInput.getText().toString();

        boolean isValidated = validate(email, password, passwordConfirmation);

        if (!isValidated) return;

        createFirebaseAccount(email, password);
    }

    void createFirebaseAccount(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Helpers.showToast(this, "Account created successfully! Verify email.");

                firebaseAuth.getCurrentUser().sendEmailVerification();
                firebaseAuth.signOut();
                finish();
            } else {
                Helpers.showToast(this, task.getException().getLocalizedMessage());
            }

            changeInProgress(false);
        });
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            registerBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            registerBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validate(String email, String password, String passwordConfirmation) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Invalid email.");
            return false;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be more than 6 characters.");
            return false;
        }

        if (!password.equals(passwordConfirmation)) {
            passwordConfirmationInput.setError("Passwords do not match.");
            return false;
        }

        return true;
    }
}