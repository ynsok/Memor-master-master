package com.example.android.memor.recylcerAdapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.memor.R;
import com.example.android.memor.data.fetchDataCursor;

/**
 * Created by Krzys on 14.01.2018.
 */

public class secondRecycler extends RecyclerView.Adapter<secondRecycler.ViewHolderr> {
    private Context ct;
    private Cursor cursor;
    private com.example.android.memor.data.fetchDataCursor fetchDataCursor;

    public secondRecycler(Context context) {
        ct = context;
        fetchDataCursor = new fetchDataCursor();

    }

    @Override
    public ViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.main_layout_horizontal, parent, false);

        return new ViewHolderr(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderr holder, int position) {

        String word = fetchDataCursor.getWord(position,cursor);
        String translateed = fetchDataCursor.getTranslated(position,cursor);
        holder.BindToSecond(word, translateed);


    }
    public Cursor swapCursro(Cursor c)
    {
        if(cursor == c)
        {
            return null;
        }
        Cursor temp = c;
        cursor = c;
        if(c !=null)
        {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public int getItemCount() {

        if(cursor == null)
        {
            return 0;
        }

        return cursor.getCount();
    }

    public class ViewHolderr extends RecyclerView.ViewHolder {
        private TextView secondView;
        private ImageView imageBackView;
        private String translated;
        private String word;
        private boolean check;

        public ViewHolderr(View itemView) {
            super(itemView);
            secondView = (TextView) itemView.findViewById(R.id.secondRecyclerTextView);

            imageBackView = (ImageView) itemView.findViewById(R.id.image_add_word);
            secondView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (check) {
                        check = false;
                        secondView.setText(word);
                        imageBackView.setImageResource(R.drawable.ic_kartka_vector_y);

                    } else {
                        check =true ;
                        secondView.setText(translated);
                        imageBackView.setImageResource(R.drawable.ic_kartka_vector_dark);
                    }
                }
            });

        }

        void BindToSecond(String word, String translated) {
            secondView.setText(word);
            this.word = word;
            this.translated = translated;
        }
    }
}
