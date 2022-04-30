package com.example.movielist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class WatchList extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    private ShareActionProvider shareActionProvider;


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

        watchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int listItem, long l) {

                new AlertDialog.Builder(WatchList.this)
                        .setTitle("Do you want to remove this movie from the list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                watchList.removeViewAt(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_watch, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ArrayList<String> shareList = readDB();
        String shareText = "Check Out These Movies to Watch!\n";
        for(int i = 0; i< shareList.size(); i++){
            shareText = shareText + shareList.get(i) + "\n";
        }
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent(shareText);
        return super.onCreateOptionsMenu(menu);
    }

    public ArrayList<String> readDB(){
        SQLiteOpenHelper favoritesDatabaseHelper = new FavoritesDatabaseHelper(this);
        ArrayList<String> shareList = new ArrayList<>();
        try{
            SQLiteDatabase  db = favoritesDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM WATCH", null);
            if(cursor.moveToFirst()){
                do{
                    shareList.add(cursor.getString(1));
                }while(cursor.moveToNext());
            }

        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return shareList;
    }

    private void setShareActionIntent(String shareText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareActionProvider.setShareIntent(intent);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}