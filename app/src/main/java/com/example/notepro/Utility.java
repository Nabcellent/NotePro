package com.example.notepro;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void showToast(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
