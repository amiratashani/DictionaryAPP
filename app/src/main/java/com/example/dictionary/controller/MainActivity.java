package com.example.dictionary.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.Repository;
import com.example.dictionary.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
        , InsertWordFragment.NoticeDialogListenerEdit {

    public static final String INSERT_WORD_DIALOG_FRAGMENT = "insertWordDialogFragment";
    private RecyclerView mRecyclerViewListWord;
    private DictionaryAdapter mDictionaryAdapter;
    private FloatingActionButton mFloatingActionButtonInsert;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        mRecyclerViewListWord = findViewById(R.id.recycle_view_words);
        mFloatingActionButtonInsert = findViewById(R.id.floating_action_button_insert);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerViewListWord.setLayoutManager(gridLayoutManager);

        updateUi();

        mFloatingActionButtonInsert.setOnClickListener(v -> {

            InsertWordFragment insertWordFragment = InsertWordFragment.newInstance();

            insertWordFragment.show(getSupportFragmentManager(), INSERT_WORD_DIALOG_FRAGMENT);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Word> words = Repository.getInstance(this).selectWords();
        List<Word> newWords = new ArrayList<>();

        if (userInput.matches("^[a-zA-Z]*$")) {
            words.stream()
                    .filter(word -> word.getEnglishText().toLowerCase().startsWith(userInput))
                    .forEach(newWords::add);
        } else {
            words.stream()
                    .filter(word -> word.getPersianText().toLowerCase().startsWith(userInput))
                    .forEach(newWords::add);
        }

        mDictionaryAdapter.setWords(newWords);
        mDictionaryAdapter.notifyDataSetChanged();

        return true;
    }

    @Override
    public void onDialogPositiveClickEditFragment(DialogFragment dialog) {
        updateUi();
    }

    private class DictionaryHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewEnglish;
        private TextView mTextViewPersian;
        private Word mWord;

        public DictionaryHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewEnglish = itemView.findViewById(R.id.text_view_english);
            mTextViewPersian = itemView.findViewById(R.id.text_view_persian);
        }

        private void bindWord(Word word) {
            mTextViewEnglish.setText(word.getEnglishText());
            mTextViewPersian.setText(word.getPersianText());
            mWord = word;
        }
    }


    private class DictionaryAdapter extends RecyclerView.Adapter<DictionaryHolder> {
        private List<Word> mWords;

        public void setWords(List<Word> words) {
            mWords = words;
        }

        public DictionaryAdapter(List<Word> words) {
            mWords = words;
        }

        @NonNull
        @Override
        public DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.word_item_holder, parent, false);
            return new DictionaryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
            holder.bindWord(mWords.get(position));
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }


    private void updateUi() {
        List<Word> words = Repository.getInstance(this).selectWords();
        if (mDictionaryAdapter == null) {
            mDictionaryAdapter = new DictionaryAdapter(words);
            mRecyclerViewListWord.setAdapter(mDictionaryAdapter);
        } else {
            mDictionaryAdapter.setWords(words);
            mDictionaryAdapter.notifyDataSetChanged();
        }
    }
}
