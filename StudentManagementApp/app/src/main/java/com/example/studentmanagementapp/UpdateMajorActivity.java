package com.example.studentmanagementapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagementapp.database.DatabaseHelper;

public class UpdateMajorActivity extends AppCompatActivity {
    private EditText nameEditText;
    private DatabaseHelper dbHelper;
    private int majorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_major);

        dbHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.nameEditText);

        majorId = getIntent().getIntExtra("major_id", -1);
        if (majorId == -1) {
            Toast.makeText(this, "Invalid major ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadMajorData();

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> updateMajor());
    }

    private void loadMajorData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Major WHERE IDMajor = ?", new String[]{String.valueOf(majorId)});
        if (cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndex("nameMajor")));
        } else {
            Toast.makeText(this, "Major not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        cursor.close();
    }

    private void updateMajor() {
        String name = nameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter major name", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameMajor", name);

        int result = db.update("Major", values, "IDMajor = ?", new String[]{String.valueOf(majorId)});
        if (result > 0) {
            Toast.makeText(this, "Major updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update major", Toast.LENGTH_SHORT).show();
        }
    }
}