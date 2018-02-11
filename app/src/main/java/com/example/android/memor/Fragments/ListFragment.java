package com.example.android.memor.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.memor.R;
import com.example.android.memor.SettingsActivity;
import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;
import com.example.android.memor.recylcerAdapter.mRecyler;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private RecyclerView recyclerView;
    private mRecyler recyler;
    private SQLiteDatabase db;
    private SQLhelper sqLhelper;
    private int tag;
    private startFetchNewCursor startFetchNewCursor;


    public ListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyler);
        startFetchNewCursor = (ListFragment.startFetchNewCursor) getActivity();
        sqLhelper = new SQLhelper(getActivity());
        db = sqLhelper.getWritableDatabase();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                tag = (int) viewHolder.itemView.getTag();
                String arra = Integer.toString(tag);
                String[] id = new String[]{arra};
                int position = db.delete(Contract.WordsContainer.TABLE_NAME, Contract.WordsContainer._ID + "=?", id);
                if (position > 0) {
                    Log.d("start fetch", "start");

                    //here I'm calling to fetch new Data 1#

                    startFetchNewCursor.startFetch();


                }
                recyler.notifyDataSetChanged();


            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    public void takeCursor(Cursor cursor) {
        recyler.swapCursor(cursor);


    }

    public void takeContext(Context context) {
        recyler = new mRecyler(context);
    }



    public interface startFetchNewCursor {
        void startFetch();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getItem = item.getItemId();
        switch (getItem){
            case R.id.onlySettings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.ListInfo:
                Toast.makeText(getActivity(), R.string.swipe_to_delete, Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}





