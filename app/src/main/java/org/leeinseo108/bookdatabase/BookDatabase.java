package org.leeinseo108.bookdatabase;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookDatabase{

    //TAG for debugging
    public static final String TAG = "BookDatabase";

    //Singleton instance
    private static BookDatabase database;

    //Database name
    public static String DATABASE_NAME = "book.db";

    //table name for BOOK_INFO
    public static String TABLE_BOOK_INFO = "BOOK_INFO";

    //version
    public static int DATABASE_VERSION = 1;

    //Helper class defined
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private Context context;

    private BookDatabase(Context context){
        this.context = context;
    }

    public static BookDatabase getInstance(Context context){
        if (database == null) {
            database = new BookDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + DATABASE_NAME + "]. ");

        dbHelper = new DatabaseHelper(context); //Mainactivity context
        db = dbHelper.getWritableDatabase(); //create database

        return true;
    }

    public void close(){
        println("closing database [" + DATABASE_NAME + "]. ");
        db.close();
        database = null;
    }

    private class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            //create table and initialize record.
            println("creating table [" + TABLE_BOOK_INFO + "]. ");

            //drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_BOOK_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            //create table
            String CREATE_SQL = "create table " + TABLE_BOOK_INFO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  NAME TEXT, "
                    + "  AUTHOR TEXT, "
                    + "  CONTENTS TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            insertRecord(_db, "The Catcher in the Rye", "Jerome David Salinger", "The Catcher in the Rye is a story by J. D. Salinger, first published in serial form in 1945-6 and as a novel in 1951");

        }
        @Override
        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "]. ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion +".");

            if (oldVersion < 2) {

            }
        }

        private void insertRecord(SQLiteDatabase _db, String name, String author, String contents){
            try {
                _db.execSQL( "insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }
    }

    public void insertRecord(String name, String author, String contents){
        try {
            db.execSQL( "insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<BookInfo> selectAll() {
        ArrayList<BookInfo> result = new ArrayList<BookInfo>();

        try{
            Cursor cursor = db.rawQuery("select NAME, AUTHOR, CONTENTS from " + TABLE_BOOK_INFO, null);
            for (int i = 0; i< cursor.getCount(); i++){
                cursor.moveToNext();
                String name = cursor.getString(0);
                String author = cursor.getString(1);
                String contents = cursor.getString(2);

                BookInfo info = new BookInfo(name, author, contents);
                result.add(info);
            }
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }

        return result;
    }

    private  void println(String msg) { Log.d(TAG, msg); };
}
