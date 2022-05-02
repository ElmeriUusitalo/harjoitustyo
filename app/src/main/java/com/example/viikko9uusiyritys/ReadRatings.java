package com.example.viikko9uusiyritys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ReadRatings extends AppCompatActivity {

    private TextView movie_name;
    private TextView stars;

    private Spinner comment_spinner;

    private ArrayList<UserScore> user_scores;
    private ArrayList<String> comments = new ArrayList<>();


    private Movie movie;

    private UserScore one_score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_ratings);

        movie_name = (TextView) findViewById(R.id.movie_name_read_ratings);
        comment_spinner = (Spinner) findViewById(R.id.comment_spinner);

        Bundle extras = getIntent().getExtras();

        movie = (Movie) extras.getSerializable("movie");
        user_scores = movie.getRatings();
        movie_name.setText(movie.getTitle());
        one_score = user_scores.get(0);
        movie_name.setText(one_score.getComment());



        for(UserScore rating : user_scores){
            comments.add(rating.getComment());
        }




        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comment_spinner.setAdapter(adapter);


    }
}