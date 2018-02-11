package com.example.android.memor.recylcerAdapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.memor.R;
import com.example.android.memor.data.fetchDataCursor;

/**
 * Created by Krzys on 08.01.2018.
 */

public class mRecyler extends RecyclerView.Adapter<mRecyler.ViewHolder> {
    private Context ct;
    private Cursor cursor;
    private com.example.android.memor.data.fetchDataCursor fetchDataCursor;


    public mRecyler(Context contex) {
        ct = contex;
        fetchDataCursor = new fetchDataCursor();


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(ct).inflate(R.layout.word_list_item, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        int id = fetchDataCursor.getID(position, cursor);
        String word = fetchDataCursor.getWord(position, cursor);
        String tra = fetchDataCursor.getTranslated(position, cursor);

        holder.itemView.setTag(id);
        holder.BintTo(word, tra);


    }


    public Cursor swapCursor(Cursor cursorr) {
        if (cursor == cursorr) {
            return null;
        }
        Cursor temp = cursorr;
        this.cursor = cursorr;
        if (cursorr != null) {
            this.notifyDataSetChanged();

        }

        return temp;

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textShow, TranslateShow;

        public ViewHolder(View itemView) {
            super(itemView);
            textShow = (TextView) itemView.findViewById(R.id.word_recycler);

            TranslateShow = (TextView) itemView.findViewById(R.id.translate_recycler);

        }

        void BintTo(String mText, String mTranslate) {
            textShow.setText(mText);
            TranslateShow.setText(mTranslate);


        }
    }
}

