package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dictionary.database.DictionaryDBSchema.*;

import androidx.annotation.Nullable;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;


    public DictionaryOpenHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Word.NAME + "(" +
                Word.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Word.Cols.UUID + ", " +
                Word.Cols.ENGLISH_TEXT + ", " +
                Word.Cols.PERSIAN_TEXT +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
