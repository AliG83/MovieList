package com.example.movielist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class WatchList extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView watchList = (ListView) findViewById(R.id.watch_list);

        SQLiteOpenHelper watchListDatabaseHelper = new FavoritesDatabaseHelper(this);

        try{
            db = watchListDatabaseHelper.getReadableDatabase();
            cursor = db.query("WATCH", new String[] {"_id", "TITLE"}, null, null, null, null, null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {"TITLE"},
                                                                        new int[]{android.R.id.text1}, 0);

            watchList.setAdapter(listAdapter);
        }
        catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}