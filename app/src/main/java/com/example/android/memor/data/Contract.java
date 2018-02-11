package com.example.android.memor.data;

import android.provider.BaseColumns;

/**
 * Created by Krzys on 06.01.2018.
 */

public class Contract {
    private Contract() {
    }

    public class WordsContainer implements BaseColumns {

        public static final String _WORD = "word";
        public static final String TABLE_NAME = "translateTable";
        public static final String _TRANSLATED = "translated";

    }
}
