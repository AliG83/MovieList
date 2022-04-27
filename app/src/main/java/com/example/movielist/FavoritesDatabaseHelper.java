package com.example.movielist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "favorites";
    private static final int DB_VERSION = 1;

    FavoritesDatabaseHelper(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static void insertMovie(SQLiteDatabase db, String title, String overview, String poster, Double rating){
        ContentValues movieValues = new ContentValues();
        movieValues.put("TITLE", title);
        movieValues.put("OVERVIEW", overview);
        movieValues.put("POSTER", poster);
        movieValues.put("RATING", rating);
        db.insert("WATCH", null, movieValues);
    }

    private void updateMyDatabase (SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE WATCH (_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, OVERVIEW TEXT, POSTER TEXT, RATING DOUBLE)" );
            insertMovie(db, "TestTitle", "Test Overview", "Test URL", 1.00);
        }
    }
}
