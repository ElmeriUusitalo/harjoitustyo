package com.example.viikko9uusiyritys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
//import android.icu.util.Calendar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    enum SpinnerState{
        MOVIES,
        THEATRES,
    }

    SpinnerState spinner_state = SpinnerState.MOVIES;

    private Spinner main_spinner;
    private TextView date_text;
    private TextView start_time_text;
    private TextView end_time_text;
    private Button open_user_activity_button;
    private Button switch_spinner;
    private Button give_rating;
    private Button read_rating;
    private TextView prompt_text;

    private ArrayList<Theatres> theatres_array_list;
    private ArrayList<Movie> movies_by_time = new ArrayList();
    private ArrayList<Movie> all_movies_list = new ArrayList();
    private ArrayList<User> user_list;

    private int selected_spinner_index;
    private String date_given_by_user;
    private int timer_selected;
    private int start_time = 9999;
    private int end_time = 9999;





    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {





        //Starting app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("MovieApp");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Setting id's

        main_spinner = findViewById(R.id.theatre_spinner);

        date_text = (TextView) findViewById(R.id.date);
        start_time_text = (TextView) findViewById(R.id.start_time_text);
        end_time_text = (TextView) findViewById(R.id.end_time_text);
        prompt_text = (TextView) findViewById(R.id.main_prompt);

        open_user_activity_button = (Button) findViewById(R.id.user_button);
        switch_spinner = (Button) findViewById(R.id.switch_button);
        give_rating = (Button) findViewById(R.id.give_rating);
        read_rating = (Button) findViewById(R.id.read_rating);

        ReadXML readXML = new ReadXML();

        user_list = loadUserList(user_list);


        prompt_text.setText("MOVIES:");
        //loadMovies();
        getAllMovies();
        //saveMovies();
        setMovieSpinner();






        //fetches the arraylist of theatres
        theatres_array_list = readXML.readAreasXML();

        main_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //valitseteatteri();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        give_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null){
                    prompt_text.setText("Please log in first!");
                } else if (spinner_state == SpinnerState.MOVIES) {
                    openActivityRateMovie();
                } else {
                    prompt_text.setText("Please select a movie first!");
                }
            }
        });


        read_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityReadRating();
            }
        });

        open_user_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityUserData();
            }
        });

        switch_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSpinner();
            }
        });

        //setTheatre_spinner();


    }
    //opens the rating giving activity
    private void openActivityRateMovie(){
        Intent intent2 = new Intent(this, RateMovie.class);

        intent2.putExtra("username", user.getUsername());

        intent2.putExtra("movie", all_movies_list.get(selected_spinner_index));

        startActivityForResult(intent2, 2);
    }

    //opens activity for reading ratings
    private void openActivityReadRating(){
        Intent intent3 = new Intent(this, ReadRatings.class);
        intent3.putExtra("movie", all_movies_list.get(selected_spinner_index));
        startActivity(intent3);
    }

    //opens activity for logging and signing in
    public void openActivityUserData(){

        Intent intent = new Intent(this, UserData.class);

        startActivityForResult(intent, 1);
    }


    //information from the activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //User activity
        if(requestCode == 1) {
            user = (User) data.getSerializableExtra("user");
            prompt_text.setText(user.getUsername() + "has logged in.");
        }

        //Rating activity
        if(requestCode == 2){
            if(/*resultCode == RESULT_OK*/true){
                try{
                Movie movie = (Movie) data.getSerializableExtra("movie");
                all_movies_list.set(selected_spinner_index, movie);
                prompt_text.setText("Movie has been rated!");}

                catch (NullPointerException e)  {e.printStackTrace();}
            }
            if(resultCode == RESULT_CANCELED){
                prompt_text.setText("Rating failed");
            }

        }


    }


    private void changeSpinner(){
        if(spinner_state == SpinnerState.MOVIES){
            setTheatre_spinner();
            prompt_text.setText("THEATRES:");
            switch_spinner.setText("Show movies");
            spinner_state = SpinnerState.THEATRES;
        } else {
            setMovieSpinner();
            prompt_text.setText("MOVIES:");
            switch_spinner.setText("Show theatres");
            spinner_state = SpinnerState.MOVIES;
        }
    }

    private void setMovieSpinner(){
        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, all_movies_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_spinner.setAdapter(adapter);
    }

    private void setTheatre_spinner(){
        //putting theatres on the spinner
        ArrayAdapter<Theatres> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatres_array_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_spinner.setAdapter(adapter);
    }





    //loads users into a arraylist
    public ArrayList<User> loadUserList(ArrayList<User> user_list){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user list", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        user_list = gson.fromJson(json, type);
        return user_list;
    }

    //load all movies from gson
    public void loadMovies(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("all movies list", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        all_movies_list = gson.fromJson(json, type);
    }

    //saves all movies to gson
    public void saveMovies() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(all_movies_list);
        editor.putString("all movies list", json);
        editor.apply();
    }

    //Gets all current movies from finnkino's website
    public void getAllMovies() {
        String url = "https://www.finnkino.fi/xml/Events/";
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(url);

            doc.getDocumentElement().normalize();
            //date_text.setText(doc.getDocumentElement().getNodeName());

            NodeList node_list = doc.getElementsByTagName("Event");


            for (int i = 0; i < node_list.getLength();i++){
                boolean found = false;
                Node node = node_list.item(i);
                System.out.println(node.getNodeName());
                if(node.getNodeType() == node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    for(Movie movieWithTime : all_movies_list){
                        String name = movieWithTime.getTitle();
                        //System.out.println("Listalta löytyy nimi: " + name);
                        //System.out.println("Ja xml löytyy nimi: " + element.getElementsByTagName("Title").item(0).getTextContent());
                        if(name.equals(element.getElementsByTagName("Title").item(0).getTextContent())){
                            found = true;
                        }
                    }

                    if (!found){
                        String name_of_movie = element.getElementsByTagName("Title").item(0).getTextContent();
                        String age_limit = element.getElementsByTagName("Rating").item(0).getTextContent();
                        String lengthInMinutes = element.getElementsByTagName("LengthInMinutes").item(0).getTextContent();
                        Movie movie = new Movie(name_of_movie, lengthInMinutes, age_limit);
                        //Movie movie = new Movie(name);
                        all_movies_list.add(movie);
                        //System.out.println(name_of_movie + age_limit + lengthInMinutes);

                    }

                }
            }




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }


    //searching for the movies based on theatres
    public void movieSearchByTheatre(View v){
        //date_text.setText(theatre_list.get(selectedTheatreIndex).getId());
        String id = theatres_array_list.get(selected_spinner_index).getId();
        String title;
        String time;


        prompt_text.setText(theatres_array_list.get(selected_spinner_index).getName());



        movies_by_time.clear();

        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();





            String url = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + date_given_by_user;
            //date_text.setText(url);
            Document doc = builder.parse(url);

            doc.getDocumentElement().normalize();
            //date_text.setText(doc.getDocumentElement().getNodeName());

            NodeList node_list = doc.getElementsByTagName("Show");




            //adding movies to movie_list
            for (int i = 0 ; i < node_list.getLength() ; i++){
                Node node = (Node) node_list.item(i);
                if(node.getNodeType() == node.ELEMENT_NODE){

                    Element element = (Element) node;
                    if(element.getElementsByTagName("dttmShowStart").item(0) != null && element.getElementsByTagName("Title").item(0) != null) {



                        time = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                        title = element.getElementsByTagName("Title").item(0).getTextContent();
                        Movie movieWithTime = new Movie(title, time);



                        if(start_time != 9999){
                            if(start_time > movieWithTime.getHrs()){
                                //start_time_text.setText(Integer.toString(start_time));
                                continue;

                            }
                        }

                        if(end_time != 9999){
                            if(end_time < movieWithTime.getHrs()){
                                //end_time_text.setText(Integer.toString(end_time));
                                continue;
                            }
                        }



                        movies_by_time.add(movieWithTime);
                    }
                }
            }
            ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movies_by_time);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            main_spinner.setAdapter(adapter);
            /*
            ArrayAdapter<Movie> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieArrayList);

            movieListView.setAdapter(arrayAdapter);
            */



        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //spinner index
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_spinner_index = position;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    //setting date
    public void pickDate(View v){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, this, year, month, day);
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String m;
        String d;
        month++;
        if(month < 10){
            m = "0" + month;
        } else {
            m = Integer.toString(month);
        }

        if(dayOfMonth < 10){
            d = "0" + dayOfMonth;
        } else {
            d = Integer.toString(dayOfMonth);
        }

        //date_text.setText(dayOfMonth  + "." + month + "." + year);

        date_given_by_user = d + "." + m + "." + year;
    }

    public void startTime(View v){
        timer_selected = 0;
        this.pickTime();

    }

    public void endTime(View v){
        timer_selected = 1;
        this.pickTime();

    }

    //setting time
    public void pickTime(){
        TimePickerDialog tpd = new TimePickerDialog(this, this, 12, 12, true);
        tpd.show();
    }


    //this gives the time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String formatter = "";
        String formatter2 = "";

        String output, output2;

        if (hourOfDay < 10){
            formatter = "0";
        }
        if (minute < 10){
            formatter2 = "0";
        }

        output = formatter + hourOfDay + ":" + formatter2 + minute;
        output2 = formatter + hourOfDay + formatter2 + minute;

        //if 0, sets start_time, if 1, sets end_time
        if(timer_selected == 0){
            start_time = Integer.parseInt(output2);
            start_time_text.setText(output);

        } else {
            end_time = Integer.parseInt(output2);
            end_time_text.setText(output);
        }
    }
}