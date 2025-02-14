package com.example.lab123;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lab2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab2);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button btnLab2_1 = findViewById(R.id.btnLab2_1);
        btnLab2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab2.this, Lab2_1.class);
                startActivity(intent);
            }
        });

        Button btnLab2_2 = findViewById(R.id.btnLab2_2);
        btnLab2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab2.this, Lab2_2.class);
                startActivity(intent);
            }
        });

        Button btnLab2_3 = findViewById(R.id.btnLab2_3);
        btnLab2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab2.this, Lab2_3.class);
                startActivity(intent);
            }
        });

    }
}