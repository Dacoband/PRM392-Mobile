package com.example.bookmanagementapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Author;

public class UpdateAuthorActivity extends AppCompatActivity {
    private EditText nameEditText, addressEditText;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    private int authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_author);

        // Khởi tạo các view
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        saveButton = findViewById(R.id.saveButton);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Lấy authorId từ Intent
        authorId = getIntent().getIntExtra("author_id", -1);
        if (authorId == -1) {
            Toast.makeText(this, "Invalid author ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load thông tin tác giả hiện tại
        loadAuthorData();

        // Xử lý sự kiện nhấn nút Save
        saveButton.setOnClickListener(v -> updateAuthor());
    }

    private void loadAuthorData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Author", null, "Author_ID = ?", new String[]{String.valueOf(authorId)}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            nameEditText.setText(name);
            addressEditText.setText(address);
        } else {
            Toast.makeText(this, "Author not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        cursor.close();
    }

    private void updateAuthor() {
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            return;
        }
        if (address.isEmpty()) {
            addressEditText.setError("Address is required");
            return;
        }

        // Cập nhật thông tin tác giả trong cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);

        int result = db.update("Author", values, "Author_ID = ?", new String[]{String.valueOf(authorId)});
        if (result > 0) {
            Toast.makeText(this, "Author updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại AuthorsFragment
        } else {
            Toast.makeText(this, "Failed to update author", Toast.LENGTH_SHORT).show();
        }
    }
}