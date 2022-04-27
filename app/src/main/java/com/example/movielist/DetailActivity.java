package com.example.movielist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    public static String mTitle;
    public static String mPoster;
    public static String mOverview;
    public static Double mRating;

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.poster_image);
        TextView rating_tv = findViewById(R.id.mRating);
        TextView title_tv = findViewById(R.id.mTitle);
        TextView overview_tv = findViewById(R.id.movie_tv);

        Bundle bundle = getIntent().getExtras();

        mTitle = bundle.getString("title");
        mPoster = bundle.getString("poster");
        mOverview = bundle.getString("overview");
        mRating = bundle.getDouble("rating");

        Glide.with(this).load(mPoster).into(imageView);
        rating_tv.setText(mRating.toString());
        title_tv.setText(mTitle);
        overview_tv.setText(mOverview);
    }

    public void onFavoriteClicked(View view){
        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        if(favorite.isChecked()){
            SQLiteOpenHelper favoritesDatabaseHelper = new FavoritesDatabaseHelper(this);
            ContentValues favoriteValues = new ContentValues();
            favoriteValues.put("TITLE", mTitle);
            favoriteValues.put("OVERVIEW", mOverview);
            favoriteValues.put("POSTER", mPoster);
            favoriteValues.put("RATING", mRating);

            try {
                SQLiteDatabase db = favoritesDatabaseHelper.getWritableDatabase();
                db.insert("WATCH", null, favoriteValues);
            }
            catch (SQLiteException e){
                Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }
}
