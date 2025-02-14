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

public class MainActivity extends AppCompatActivity {

    Button btnLab1;
    Button btnLab2;
    Button btnLab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnLab1 = (Button) findViewById(R.id.btnLab1);
        btnLab2 = (Button) findViewById(R.id.btnLab2);
        btnLab3 = (Button) findViewById(R.id.btnLab3);
        btnLab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab1.class);
                startActivity(intent);
            }
        });
        btnLab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab2.class);
                startActivity(intent);
            }
        });
        btnLab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab3.class);
                startActivity(intent);
            }
        });

    }
}