package com.example.dictionary.controller;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dictionary.R;
import com.example.dictionary.model.Repository;
import com.example.dictionary.model.Word;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertWordFragment extends DialogFragment {

    private EditText mEditTextEnglish;
    private EditText mEditTextPersian;

    NoticeDialogListenerEdit listener;

    public static InsertWordFragment newInstance() {

        Bundle args = new Bundle();
        InsertWordFragment fragment = new InsertWordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public InsertWordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListenerCreate so we can send events to the host
            listener = (InsertWordFragment.NoticeDialogListenerEdit) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListenerCreate");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_insert_word, null, false);

        mEditTextEnglish=view.findViewById(R.id.edit_text_english);
        mEditTextPersian=view.findViewById(R.id.edit_text_persian);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(getActivity())
                .setView(view)
                .setPositiveButton("Save", null)
                .create();

        alertDialog.setOnShowListener(dialog -> {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(v -> {
                Word word =new Word(mEditTextEnglish.getText().toString(),mEditTextPersian.getText().toString());
                Repository.getInstance(getActivity()).insertWord(word);
                listener.onDialogPositiveClickEditFragment(InsertWordFragment.this);
                alertDialog.dismiss();
            });

        });

        return alertDialog;
    }


    public interface NoticeDialogListenerEdit{
        void onDialogPositiveClickEditFragment(DialogFragment dialog);
    }
}
