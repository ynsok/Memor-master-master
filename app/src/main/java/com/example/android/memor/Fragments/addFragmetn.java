package com.example.android.memor.Fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.memor.R;
import com.example.android.memor.SettingsActivity;
import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class addFragmetn extends Fragment {


    private EditText word, translate;
    private SQLiteDatabase db;
    private SQLhelper sqLhelper;
    private Button button;

    public addFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_fragmetn, container, false);
        setHasOptionsMenu(true);
        word = (EditText)view.findViewById(R.id.word);
        translate = (EditText)view.findViewById(R.id.translate);
        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonForTake();
            }
        });

/*
        ImageButton buttonInfo = (ImageButton) view.findViewById(R.id.button_info);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInfo();
            }
        });

        ImageButton buttonHelpAdd = (ImageButton) view.findViewById(R.id.button_help_Add);
        buttonHelpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHelpAdd();
            }
        });
*/

        sqLhelper = new SQLhelper(getActivity());
        db = sqLhelper.getWritableDatabase();



        return view;
    }
    public void ButtonForTake() {
        String Word = word.getText().toString();
        String translated = translate.getText().toString();
        if (!TextUtils.isEmpty(Word) && !TextUtils.isEmpty(translated)) {

            long id = insert(Word, translated);
            if (id == -1) {
                Toast.makeText(getActivity(), R.string.error_inserting, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), R.string.word_added, Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getActivity(), R.string.all_fields_used, Toast.LENGTH_SHORT).show();
        }
        word.getText().clear();
        translate.getText().clear();
    }

    private long insert(String word, String translate) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.WordsContainer._WORD, word);
        contentValues.put(Contract.WordsContainer._TRANSLATED, translate);
        return db.insert(Contract.WordsContainer.TABLE_NAME, null, contentValues);
    }


/*    public void buttonHelpAdd() {
        Toast.makeText(getActivity(), R.string.intro_text, Toast.LENGTH_LONG).show();
    }

    public void buttonInfo() {
        Toast.makeText(getActivity(), R.string.info, Toast.LENGTH_LONG).show();
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_frag_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getAction = item.getItemId();

        switch (getAction){
            case R.id.settings_menu:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.infoMenu:
            Toast.makeText(getActivity(), R.string.info, Toast.LENGTH_LONG).show();
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}

