package com.example.studentmanagementapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagementapp.database.DatabaseHelper;

public class AddMajorActivity extends AppCompatActivity {
    private EditText nameEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);

        dbHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.nameEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveMajor());
    }

    private void saveMajor() {
        String name = nameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter major name", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameMajor", name);

        long result = db.insert("Major", null, values);
        if (result != -1) {
            Toast.makeText(this, "Major added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add major", Toast.LENGTH_SHORT).show();
        }
    }
}