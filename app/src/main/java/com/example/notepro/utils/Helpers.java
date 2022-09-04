package com.example.notepro.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Helpers {
    public static void showToast(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid())
                                .collection("notes");
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(timestamp.toDate());
    }
}
