package com.example.dictionary.database;


public class DictionaryDBSchema {

    public static final String NAME = "dictionary.db";

    public static final class Word {

        public static final String NAME = "Word";

       public static class Cols {
            public static final String _ID = "_id";
            public static final String UUID = "uuid";
            public static final String ENGLISH_TEXT = "englishText";
            public static final String PERSIAN_TEXT = "persianText";
        }
    }


}
