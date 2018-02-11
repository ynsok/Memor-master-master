package com.example.android.memor;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.memor.Fragments.ListFragment;
import com.example.android.memor.Fragments.OneWordFragmetn;
import com.example.android.memor.Fragments.addFragmetn;
import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;
import com.example.android.memor.services.MemorUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ListFragment.startFetchNewCursor {

    private SQLhelper sqLhelper;
    private SQLiteDatabase db;
    private int LOADE_ID = 111;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ListFragment listFragment;
    public MemorUtils memorUtils;
    private OneWordFragmetn oneWordFragmetn;
    private Toolbar toolbar;
    private int count = 0;
    private String PREF_NAME = "PREF_NAME";
    private Boolean mValue = true;
    private String mPrefName = "prefName";
    private Boolean mFalseValue = false;
    private String mPrefValue = "prefValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app__bar_layout);
        //        initialize The DataBase
        sqLhelper = new SQLhelper(this);
        //        get access to write/read items to/from Database
        db = sqLhelper.getWritableDatabase();
        implementMockData(this);
        listFragment = new ListFragment();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        memorUtils = new MemorUtils();
        oneWordFragmetn = new OneWordFragmetn();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerr, new addFragmetn()).commit();


        getSupportLoaderManager().initLoader(LOADE_ID, null, this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        getSupportLoaderManager().restartLoader(LOADE_ID, null, MainActivity.this);

                        switch (item.getItemId()) {
                            case R.id.action_add:
                                fragmentTransaction.replace(R.id.containerr, new addFragmetn()).commit();
                                return true;

                            case R.id.scrollWords:
                                fragmentTransaction.replace(R.id.containerr, listFragment).commit();
                                break;
                            case R.id.action_my_words:
                                fragmentTransaction.replace(R.id.containerr, oneWordFragmetn).commit();
                                break;


                        }
                        return false;
                    }
                });

        //   memorUtils.scheduleFetchData(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADE_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOADE_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor cursor;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (cursor != null) {
                    deliverResult(cursor);
                } else {
                    forceLoad();
                }

            }

            @Override
            public Cursor loadInBackground() {
                cursor = db.query(Contract.WordsContainer.TABLE_NAME, null, null, null, null, null, null, null);
                return cursor;
            }

            @Override
            public void deliverResult(Cursor data) {
                cursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (count == 0) {
            listFragment.takeContext(this);
            count = 1;
        }
        //send cursor to fragment
        listFragment.takeCursor(data);
        //send cursors to fragment
        oneWordFragmetn.SecondCursor(data, this);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void startFetch() {
        getSupportLoaderManager().restartLoader(LOADE_ID, null, MainActivity.this);


    }

    private void implementMockData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        Boolean takeValue = sharedPreferences.getBoolean(mPrefName, true);
        if (takeValue) {
            ContentValues contentValues = new ContentValues();
            int countFakeWord = MockData.word().length;
            int countFakeTranslate = MockData.translated().length;
            if (countFakeWord == countFakeTranslate) {
                for (int x = 0; x < countFakeTranslate; x++) {
                    String fakeWord = MockData.word()[x];
                    String fakeTranslated = MockData.translated()[x];
                    contentValues.put(Contract.WordsContainer._WORD, fakeWord);
                    contentValues.put(Contract.WordsContainer._TRANSLATED, fakeTranslated);
                    db.insert(Contract.WordsContainer.TABLE_NAME, null, contentValues);

                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(mPrefName, mFalseValue);
            editor.apply();
        }
    }


}

