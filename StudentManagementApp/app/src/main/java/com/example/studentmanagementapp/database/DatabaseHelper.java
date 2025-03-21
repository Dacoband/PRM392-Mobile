package com.example.studentmanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Major
    private static final String TABLE_MAJOR = "Major";
    private static final String COLUMN_MAJOR_ID = "IDMajor";
    private static final String COLUMN_MAJOR_NAME = "nameMajor";

    // Bảng Student
    private static final String TABLE_STUDENT = "Student";
    private static final String COLUMN_STUDENT_ID = "ID";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_ID_MAJOR = "idMajor";

    private static final String CREATE_TABLE_MAJOR = "CREATE TABLE " + TABLE_MAJOR + " (" +
            COLUMN_MAJOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MAJOR_NAME + " TEXT)";

    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " (" +
            COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_GENDER + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PHONE + " TEXT, " +
            COLUMN_ID_MAJOR + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_ID_MAJOR + ") REFERENCES " + TABLE_MAJOR + "(" + COLUMN_MAJOR_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAJOR);
        db.execSQL(CREATE_TABLE_STUDENT);

        // Thêm dữ liệu mẫu
        ContentValues majorValues = new ContentValues();
        majorValues.put(COLUMN_MAJOR_NAME, "Computer Science");
        db.insert(TABLE_MAJOR, null, majorValues);

        majorValues.clear();
        majorValues.put(COLUMN_MAJOR_NAME, "Mathematics");
        db.insert(TABLE_MAJOR, null, majorValues);

        ContentValues studentValues = new ContentValues();
        studentValues.put(COLUMN_NAME, "John Doe");
        studentValues.put(COLUMN_DATE, "2000-01-01");
        studentValues.put(COLUMN_GENDER, "Male");
        studentValues.put(COLUMN_EMAIL, "john@example.com");
        studentValues.put(COLUMN_ADDRESS, "123 Main St, USA");
        studentValues.put(COLUMN_PHONE, "123456789");
        studentValues.put(COLUMN_ID_MAJOR, 1);
        db.insert(TABLE_STUDENT, null, studentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAJOR);
        onCreate(db);
    }
}
