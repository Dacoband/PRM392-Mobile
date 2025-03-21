package com.example.studentmanagementapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagementapp.database.DatabaseHelper;
import com.example.studentmanagementapp.model.Major;
import java.util.ArrayList;
import java.util.List;
import com.example.studentmanagementapp.databinding.ActivityUpdateStudentBinding;

public class UpdateStudentActivity extends AppCompatActivity {
    private EditText nameEditText, dateEditText, genderEditText, emailEditText, addressEditText, phoneEditText;
    private Spinner majorSpinner;
    private DatabaseHelper dbHelper;
    private int studentId;
    private List<Major> majorList;
    private ArrayAdapter<String> majorAdapter;
    private List<String> majorNames;
    private int selectedMajorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        dbHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.nameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        genderEditText = findViewById(R.id.genderEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        majorSpinner = findViewById(R.id.majorSpinner);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        majorList = new ArrayList<>();
        majorNames = new ArrayList<>();
        loadMajors();

        majorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMajorId = majorList.get(position).getIdMajor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMajorId = -1;
            }
        });

        loadStudentData();

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> updateStudent());
    }

    private void loadMajors() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Major", null);
        while (cursor.moveToNext()) {
            Major major = new Major();
            major.setIdMajor(cursor.getInt(cursor.getColumnIndex("IDMajor")));
            major.setNameMajor(cursor.getString(cursor.getColumnIndex("nameMajor")));
            majorList.add(major);
            majorNames.add(major.getNameMajor());
        }
        cursor.close();
    }

    private void loadStudentData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Student WHERE ID = ?", new String[]{String.valueOf(studentId)});
        if (cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
            dateEditText.setText(cursor.getString(cursor.getColumnIndex("date")));
            genderEditText.setText(cursor.getString(cursor.getColumnIndex("gender")));
            emailEditText.setText(cursor.getString(cursor.getColumnIndex("email")));
            addressEditText.setText(cursor.getString(cursor.getColumnIndex("Address")));
            phoneEditText.setText(cursor.getString(cursor.getColumnIndex("Phone")));
            int idMajor = cursor.getInt(cursor.getColumnIndex("idMajor"));

            for (int i = 0; i < majorList.size(); i++) {
                if (majorList.get(i).getIdMajor() == idMajor) {
                    majorSpinner.setSelection(i);
                    selectedMajorId = idMajor;
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        cursor.close();
    }

    private void updateStudent() {
        String name = nameEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty() || gender.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedMajorId == -1) {
            Toast.makeText(this, "Please select a major", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("date", date);
        values.put("gender", gender);
        values.put("email", email);
        values.put("Address", address);
        values.put("Phone", phone);
        values.put("idMajor", selectedMajorId);

        int result = db.update("Student", values, "ID = ?", new String[]{String.valueOf(studentId)});
        if (result > 0) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
        }
    }
}