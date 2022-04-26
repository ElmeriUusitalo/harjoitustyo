package com.example.viikko9uusiyritys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner theatre;
    private ListView listview;



    private ArrayList<Theatres> theatresArrayList;
    private ArrayList<Movie> movie_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ReadXML readXML = new ReadXML();


        //Starting app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Setting id's
        listview = (ListView)findViewById(R.id.listview);
        theatre = findViewById(R.id.spinner);

        //fetches the arraylist of theatres
        theatresArrayList = readXML.readAreasXML();

        theatre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //valitseteatteri();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //putting theatres on the spinner
        ArrayAdapter<Theatres> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatresArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatre.setAdapter(adapter);


    }

    /*
    public void valitseteatteri() {
        Teatteri valittu = (Teatteri) teatteri.getSelectedItem();
        Teatterit.readXML2(valittu);
        ArrayAdapter<Esitys> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Teatterit.esitys_array);
        listview.setAdapter(adapter2);
    }
    */

}