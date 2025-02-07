package com.example.listview;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvFruit;
    ArrayList<Fruits> fruitsArrayList;
    FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        adapter = new FruitAdapter(this, R.layout.line_fruit,fruitsArrayList);
        lvFruit.setAdapter(adapter);
    }
    private void Mapping(){
        lvFruit = findViewById(R.id.listviewFruit);
        fruitsArrayList =new ArrayList<>();
        fruitsArrayList.add(new Fruits("Chuối già","Chuối già Đà Lạt",R.drawable.banana));
        fruitsArrayList.add(new Fruits("Thanh Long","Thanh Long Bình Thuận",R.drawable.pitaya));
        fruitsArrayList.add(new Fruits("Dâu Tây","Dâu tây Đà Lạt",R.drawable.strawberry));
        fruitsArrayList.add(new Fruits("Dưa Hấu","Dưa hấu Long An",R.drawable.watermelon));
        fruitsArrayList.add(new Fruits("Cam Vàng","Cam vàng mọng nước",R.drawable.orange));
    }
}