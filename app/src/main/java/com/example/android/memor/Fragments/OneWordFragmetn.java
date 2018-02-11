package com.example.android.memor.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.memor.R;
import com.example.android.memor.SettingsActivity;
import com.example.android.memor.recylcerAdapter.secondRecycler;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneWordFragmetn extends Fragment {


    private RecyclerView recyclerView;
    private secondRecycler secondRecycler;

    private SnapHelper mSnapHelper;

    public OneWordFragmetn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_word_fragmetn, container, false);
        setHasOptionsMenu(true);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOneWordpogf);

        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(secondRecycler);

        return view;


    }

    public void SecondCursor(Cursor cursor, Context context) {
        secondRecycler = new secondRecycler(context);
        secondRecycler.swapCursro(cursor);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getItem = item.getItemId();
        switch (getItem) {
            case R.id.onlySettings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.ListInfo:
                Toast.makeText(getActivity(), R.string.swipe_to_browse, Toast.LENGTH_LONG).show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

