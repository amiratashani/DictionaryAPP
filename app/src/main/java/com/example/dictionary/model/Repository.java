package com.example.dictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.dictionary.database.DictionaryDBSchema;
import com.example.dictionary.database.DictionaryOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository sRepository;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Repository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DictionaryOpenHelper(mContext).getWritableDatabase();
    }

    public static Repository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new Repository(context);
        }
        return sRepository;
    }

    public List<Word> selectWords() {
        List<Word> words = new ArrayList<>();

        WordCursorWrapper cursor = querySelectWord(null, null);


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                words.add(cursor.getWord());

                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }
        return words;
    }

    public void insertWord(Word word) {
        ContentValues values = getContentValues(word);
        mDatabase.insertOrThrow(DictionaryDBSchema.Word.NAME, null, values);
    }

    public List<String> getEnglishText() {
        List<String> englishTexts = new ArrayList<>();
        for (Word item : selectWords()) {
            englishTexts.add(item.getEnglishText());
        }
        return englishTexts;
    }

    private ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();

        values.put(DictionaryDBSchema.Word.Cols.UUID, word.getId().toString());
        values.put(DictionaryDBSchema.Word.Cols.ENGLISH_TEXT, word.getEnglishText());
        values.put(DictionaryDBSchema.Word.Cols.PERSIAN_TEXT, word.getPersianText());

        return values;
    }

    private WordCursorWrapper querySelectWord(String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(DictionaryDBSchema.Word.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
        return new WordCursorWrapper(cursor);

    }

    private class WordCursorWrapper extends CursorWrapper {
        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public WordCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Word getWord() {
            String uuid = getString(getColumnIndex(DictionaryDBSchema.Word.Cols.UUID));
            String englishText = getString(getColumnIndex(DictionaryDBSchema.Word.Cols.ENGLISH_TEXT));
            String persianText = getString(getColumnIndex(DictionaryDBSchema.Word.Cols.PERSIAN_TEXT));

            Word word = new Word(UUID.fromString(uuid), englishText, persianText);
            return word;
        }
    }

}
