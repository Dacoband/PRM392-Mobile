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

public class Lab1 extends AppCompatActivity {
    Button btnFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab1);
        Button btnBack = findViewById(R.id.btnBack);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab1.this, facebook.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lab1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnRelativeLayout = findViewById(R.id.btnRelativeLayout);
        btnRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lab1.this, RelativeLayout.class);
                startActivity(intent);
            }
        });

        Button btnLinearLayout = findViewById(R.id.btnLinearLayout);
        btnLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lab1.this, LinearLayout.class);
                startActivity(intent);
            }
        });
    }
}