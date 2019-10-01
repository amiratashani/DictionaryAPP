package com.example.dictionary.model;

import java.util.UUID;

public class Word {
    private UUID mId;
    private String mEnglishText;
    private String mPersianText;

    public Word( String englishText, String persianText) {
        mId = UUID.randomUUID();
        mEnglishText = englishText;
        mPersianText = persianText;
    }

    public Word(UUID id, String englishText, String persianText) {
        mId = id;
        mEnglishText = englishText;
        mPersianText = persianText;
    }

    public UUID getId() {
        return mId;
    }

    public String getEnglishText() {
        return mEnglishText;
    }

    public void setEnglishText(String englishText) {
        mEnglishText = englishText;
    }

    public String getPersianText() {
        return mPersianText;
    }

    public void setPersianText(String persianText) {
        mPersianText = persianText;
    }
}
