package com.example.lab123;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class Lab2_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab21);
        EditText editTextMin = findViewById(R.id.editTextNumberMin);
        EditText editTextMax = findViewById(R.id.editTextNumberMax);
        Button btnGenerate = findViewById(R.id.btnGenerate);
        TextView textViewResult = findViewById(R.id.textViewResult);
        btnGenerate.setOnClickListener(v -> {
            try {
                boolean isValid = true;
                String minStr = editTextMin.getText().toString();
                String maxStr = editTextMax.getText().toString();
                if (minStr.trim().isEmpty()) {
                    editTextMin.setError("Please enter a number");
                    isValid = false;
                }
                if (maxStr.trim().isEmpty()) {
                    editTextMax.setError("Please enter a number");
                    isValid = false;
                }
                if (!isValid) {
                    return;
                }
                int min = Integer.parseInt(minStr);
                int max = Integer.parseInt(maxStr);
                if (min >= max) {
                    Toast.makeText(this, "Max number must be greater than the min one.", Toast.LENGTH_SHORT).show();
                } else {
                    Random random = new Random();
                    int result = random.nextInt(max - min + 1) + min;
                    textViewResult.setText("Result: " + result);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}