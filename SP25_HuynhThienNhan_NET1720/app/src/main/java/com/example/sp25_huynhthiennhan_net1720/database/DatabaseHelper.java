package com.example.sp25_huynhthiennhan_net1720.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookManagement.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    private static final String TABLE_AUTHOR = "Author";
    private static final String COLUMN_AUTHOR_ID = "Author_ID";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";


    private static final String TABLE_BOOK = "Book";
    private static final String COLUMN_BOOK_ID = "Book_ID";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_PUBLICATION_DATE = "publication_date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_BOOK_AUTHOR_ID = "Author_ID";

    private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE " + TABLE_AUTHOR + " (" +
            COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PHONE + " TEXT, " +
            COLUMN_ADDRESS + " TEXT)";

    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + " (" +
            COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BOOK_TITLE + " TEXT, " +
            COLUMN_PUBLICATION_DATE + " TEXT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_BOOK_AUTHOR_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BOOK_AUTHOR_ID + ") REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AUTHOR);
        db.execSQL(CREATE_TABLE_BOOK);

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        long[] authorIds = new long[10];
        authorIds[0] = insertAuthor(db, "Nguyen Nhat Anh", "nguyennhatanh@example.com", "0901234567", "Quận 9,Ho Chi Minh City");
        authorIds[1] = insertAuthor(db, "To Hoai", "tohoai@example.com", "0902345678", "Hàng Than, Hà Nội");
        authorIds[2] = insertAuthor(db, "Nam Cao", "namcao@example.com", "0903456789", "Hà Nam");
        authorIds[3] = insertAuthor(db, "Xuan Dieu", "xuandieu@example.com", "0904567890", "Bình Định");
        authorIds[4] = insertAuthor(db, "Ho Chi Minh", "hochiminh@example.com", "0905678901", "Nghệ An");
        authorIds[5] = insertAuthor(db, "Kim Lan", "kimlan@example.com", "0906789012", "Bắc Giang");
        authorIds[6] = insertAuthor(db, "Nguyen Tuan", "nguyentuan@example.com", "0907890123", "Hà Đông");
        authorIds[7] = insertAuthor(db, "Che Lan Vien", "chelanvien@example.com", "0908901234", "Quảng Tri");
        authorIds[8] = insertAuthor(db, "Lu Xun", "luxun@example.com", "0909012345", "Thẩm Quyến, Trung Quốc");
        authorIds[9] = insertAuthor(db, "Victor Hugo", "victorhugo@example.com", "0900123456", "France");

        insertBook(db, "Cho Toi Xin Mot Ve Di Tuoi Tho", "2010-01-15", "Fiction", authorIds[0]);
        insertBook(db, "Mat Biec", "2012-03-20", "Fiction", authorIds[0]);
        insertBook(db, "De Men Phieu Luu Ky", "1941-05-10", "Fiction", authorIds[1]);
        insertBook(db, "Kim Dong", "1950-07-25", "Fiction", authorIds[1]);
        insertBook(db, "Chiec Luoc Nga", "1943-09-12", "Fiction", authorIds[2]);
        insertBook(db, "Lao Hac", "1943-11-30", "Fiction", authorIds[2]);
        insertBook(db, "Tho Tinh Xuan Dieu", "1938-02-14", "Poetry", authorIds[3]);
        insertBook(db, "Huong Tham", "1940-04-18", "Poetry", authorIds[3]);
        insertBook(db, "Nhat Ky Trong Tu", "1933-06-22", "Memoir", authorIds[4]);
        insertBook(db, "Duong Cach Mang", "1947-08-15", "Political", authorIds[4]);
        insertBook(db, "Lang Vu Dai Ngay Ay", "1944-10-05", "Fiction", authorIds[5]);
        insertBook(db, "Con Trau", "1942-12-20", "Fiction", authorIds[5]);
        insertBook(db, "Chiec Thuyen Xa", "1940-01-30", "Fiction", authorIds[6]);
        insertBook(db, "Vuot Côn Đao", "1950-03-10", "Fiction", authorIds[6]);
        insertBook(db, "Đoi Cat", "1960-05-25", "Poetry", authorIds[7]);
        insertBook(db, "Anh Trang Ram", "1958-07-15", "Poetry", authorIds[7]);
        insertBook(db, "AQ Chinh Truyen", "1921-09-10", "Fiction", authorIds[8]);
        insertBook(db, "Nhat Ky Dien", "1912-11-20", "Fiction", authorIds[8]);
        insertBook(db, "Nhung Nguoi Khon Kho", "1862-06-30", "Fiction", authorIds[9]);
        insertBook(db, "Nha Tho Duc Ba Paris", "1831-03-15", "Fiction", authorIds[9]);
    }

    private long insertAuthor(SQLiteDatabase db, String name, String email, String phone, String address) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ADDRESS, address);
        return db.insert(TABLE_AUTHOR, null, values);
    }

    private long insertBook(SQLiteDatabase db, String title, String publicationDate, String type, long authorId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_PUBLICATION_DATE, publicationDate);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_BOOK_AUTHOR_ID, authorId);
        return db.insert(TABLE_BOOK, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);
    }

    public Context getContext() {
        return context;
    }
}
