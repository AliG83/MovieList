package com.example.movielist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_detail);


        ImageView imageView = findViewById(R.id.poster_image);
        TextView rating_tv = findViewById(R.id.mRating);
        TextView title_tv = findViewById(R.id.mTitle);
        TextView overview_tv = findViewById(R.id.movervie_tv);

        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("title");
        String mPoster = bundle.getString("poster");
        String mOverview = bundle.getString("overview");
        Double mRating = bundle.getDouble("rating");

        Glide.with(this).load(mPoster).into(imageView);
        rating_tv.setText(mRating.toString());
        title_tv.setText(mTitle);
        overview_tv.setText(mOverview);
    }
}
