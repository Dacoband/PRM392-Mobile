package com.example.studentmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.studentmanagementapp.database.DatabaseHelper;
import com.example.studentmanagementapp.model.Major;
import java.util.ArrayList;
import java.util.List;


public class MajorsFragment extends Fragment {
    private ListView majorListView;
    private MajorAdapter majorAdapter;
    private List<Major> majorList;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_majors, container, false);

        dbHelper = new DatabaseHelper(getContext());
        majorListView = view.findViewById(R.id.majorListView);
        majorList = new ArrayList<>();
        majorAdapter = new MajorAdapter();

        majorListView.setAdapter(majorAdapter);
        loadMajors();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMajors();
    }

    private void loadMajors() {
        majorList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Major", null);
        while (cursor.moveToNext()) {
            Major major = new Major();
            major.setIdMajor(cursor.getInt(cursor.getColumnIndex("IDMajor")));
            major.setNameMajor(cursor.getString(cursor.getColumnIndex("nameMajor")));
            majorList.add(major);
        }
        cursor.close();
        majorAdapter.notifyDataSetChanged();
    }

    private class MajorAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return majorList.size();
        }

        @Override
        public Object getItem(int position) {
            return majorList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_major, parent, false);
            }

            Major major = majorList.get(position);

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            Button updateButton = convertView.findViewById(R.id.updateButton);
            Button deleteButton = convertView.findViewById(R.id.deleteButton);

            nameTextView.setText(major.getNameMajor());

            updateButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UpdateMajorActivity.class);
                intent.putExtra("major_id", major.getIdMajor());
                startActivity(intent);
            });

            deleteButton.setOnClickListener(v -> {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Major", "IDMajor = ?", new String[]{String.valueOf(major.getIdMajor())});
                loadMajors();
            });

            return convertView;
        }
    }
}