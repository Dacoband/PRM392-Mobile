package com.example.lab123;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Lab3_1 extends AppCompatActivity {
    private static int CURRENT_POS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab31);
        ListView listView = findViewById(R.id.listView);
        EditText editText = findViewById(R.id.editText);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);

        List<String> list = new ArrayList<>();
        list.add("JavaScript");
        list.add("Java");
        list.add("Swift");
        list.add("Go");
        list.add("C#");
        list.add("Kotlin");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Lab3_1.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        buttonAdd.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (text.isEmpty()) {
                editText.setError("Blank value is not allowed");
                editText.requestFocus();
                return;
            }
            if (list.contains(text)) {
                editText.setError("Item already exists");
                editText.requestFocus();
                return;
            }
            list.add(text);
            arrayAdapter.notifyDataSetChanged();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            list.remove(position);
            editText.setText("");
            CURRENT_POS = -1;
            arrayAdapter.notifyDataSetChanged();
            return true;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            editText.setText(list.get(position));
            CURRENT_POS = position;
        });

        buttonUpdate.setOnClickListener(v -> {
            String updateText = editText.getText().toString().trim();
            if (list.isEmpty()) {
                editText.setError("List is empty");
                editText.requestFocus();
                return;
            }
            if (CURRENT_POS == -1) {
                editText.setError("Please select an item first");
                editText.requestFocus();
                return;
            }
            if (updateText.isEmpty()) {
                editText.setError("Blank value is not allowed");
                editText.requestFocus();
                return;
            }
            if (updateText.equals(list.get(CURRENT_POS))) {
                editText.setError("You have not changed anything");
                editText.requestFocus();
                return;
            }
            if (list.contains(updateText)) {
                editText.setError("Item already exists");
                editText.requestFocus();
                return;
            }
            String oldText = list.get(CURRENT_POS);
            list.set(CURRENT_POS, updateText);
            arrayAdapter.notifyDataSetChanged();
            editText.setText("");
            CURRENT_POS = -1;
            Toast.makeText(Lab3_1.this, oldText + " has been updated to " + updateText, Toast.LENGTH_SHORT).show();
        });

    }
}