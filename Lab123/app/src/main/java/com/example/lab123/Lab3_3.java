package com.example.lab123;

import static java.util.stream.Collectors.mapping;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Lab3_3 extends AppCompatActivity {
    ListView lvLegends;
    List<Legend> legends;
    LegendAdapter legendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab33);
        mapping();
        legendAdapter = new LegendAdapter(this, R.layout.legend_listview, legends);
        lvLegends.setAdapter(legendAdapter);
    }
    private void mapping() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lvLegends = findViewById(R.id.lvLegends);
        legends = new ArrayList<>();
        try {
            legends.add(new Legend("Pele", dateFormat.parse("23-10-1940"), R.drawable.ic_flag_brazil, R.drawable.pele));
            legends.add(new Legend("Lionel Messi", dateFormat.parse("24-06-1987"), R.drawable.ic_flag_argentina, R.drawable.messi));
            legends.add(new Legend("Neymar Santos Jersey", dateFormat.parse("22-09-1976"), R.drawable.ic_flag_brazil, R.drawable.neymar));
            legends.add(new Legend("MBappe", dateFormat.parse("30-10-1960"), R.drawable.ic_flag_france, R.drawable.mbappe));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}