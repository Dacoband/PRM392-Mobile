package com.example.studentmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import com.example.studentmanagementapp.model.Student;
import java.util.ArrayList;
import java.util.List;
public class StudentsFragment extends Fragment {

    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        dbHelper = new DatabaseHelper(getContext());
        studentListView = view.findViewById(R.id.studentListView);
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter();

        studentListView.setAdapter(studentAdapter);
        loadStudents();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStudents();
    }

    private void loadStudents() {
        studentList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Student", null);
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setDate(cursor.getString(cursor.getColumnIndex("date")));
            student.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            student.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
            student.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
            student.setIdMajor(cursor.getInt(cursor.getColumnIndex("idMajor")));
            studentList.add(student);
        }
        cursor.close();
        studentAdapter.notifyDataSetChanged();
    }

    private class StudentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int position) {
            return studentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
            }

            Student student = studentList.get(position);

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView majorTextView = convertView.findViewById(R.id.majorTextView);
            TextView emailTextView = convertView.findViewById(R.id.emailTextView);
            TextView phoneTextView = convertView.findViewById(R.id.phoneTextView);
            TextView addressTextView = convertView.findViewById(R.id.addressTextView);
            Button updateButton = convertView.findViewById(R.id.updateButton);
            Button deleteButton = convertView.findViewById(R.id.deleteButton);
            Button mapButton = convertView.findViewById(R.id.mapButton);

            nameTextView.setText(student.getName());
            majorTextView.setText("Major: " + getMajorName(student.getIdMajor()));
            emailTextView.setText("Email: " + student.getEmail());
            phoneTextView.setText("Phone: " + student.getPhone());
            addressTextView.setText("Address: " + student.getAddress());

            updateButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UpdateStudentActivity.class);
                intent.putExtra("student_id", student.getId());
                startActivity(intent);
            });

            deleteButton.setOnClickListener(v -> {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Student", "ID = ?", new String[]{String.valueOf(student.getId())});
                loadStudents();
            });

            mapButton.setOnClickListener(v -> {
                String address = student.getAddress();
                if (address == null || address.isEmpty()) {
                    android.widget.Toast.makeText(getActivity(), "Address not found", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                String uri = "geo:0,0?q=" + Uri.encode(address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/" + Uri.encode(address)));
                    startActivity(browserIntent);
                }
            });

            return convertView;
        }

        private String getMajorName(int idMajor) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT nameMajor FROM Major WHERE IDMajor = ?", new String[]{String.valueOf(idMajor)});
            String majorName = "Unknown";
            if (cursor.moveToFirst()) {
                majorName = cursor.getString(cursor.getColumnIndex("nameMajor"));
            }
            cursor.close();
            return majorName;
        }
    }
}