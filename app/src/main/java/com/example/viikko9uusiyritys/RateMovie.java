package com.example.viikko9uusiyritys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RateMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private TextView text_prompt;
    private Button submit_button;
    private EditText rating_box;


    private String username;
    private Movie movie;
    private UserScore user_score;

    private Intent resultIntent = new Intent();

    private int rating_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movie);


        spinner = (Spinner) findViewById(R.id.rate_movie_spinner);
        text_prompt = (TextView) findViewById(R.id.rate_movie_text_view);
        submit_button = (Button) findViewById(R.id.submit_rating_button);
        //rating_box = (EditText) findViewById(R.id.comment_edittext);


        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        movie = (Movie) extras.getSerializable("movie");
        text_prompt.setText(movie.getTitle());




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        movie = (Movie) extras.getSerializable("movie");
        text_prompt.setText(movie.getTitle());

    }

    public void setRating(){


        movie.addStar(rating_number);


        /*
        //checks which constructor should be used
        if(comment.isEmpty()){
            user_score = new UserScore(rating_number, username);

        } else {
            user_score = new UserScore(rating_number, username, comment);
        }
        movie.addRating(user_score);

         */
        resultIntent.putExtra("movie", movie);
        setResult(RESULT_OK, resultIntent);
        finish();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rating_number = i+1;
        //text_prompt.setText(""+ rating_number);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    @Override
    protected void onPause() {
        super.onPause();

        if (user_score != null){
            setResult(RESULT_OK, resultIntent);
        } else {
            setResult(RESULT_CANCELED, resultIntent);
        }



    }
}