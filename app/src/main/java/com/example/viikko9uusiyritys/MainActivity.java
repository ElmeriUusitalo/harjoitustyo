package com.example.viikko9uusiyritys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner theatre;
    ListView listview;


    //moi
    //testiline
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        listview = (ListView)findViewById(R.id.listview);
        theatre = findViewById(R.id.spinner);
        ReadXML.readAreasXML();

        theatre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            valitseteatteri();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<Theatres> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ReadXML.theatre_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatre.setAdapter(adapter);


    }


    public void valitseteatteri() {
        Theatres valittu = (Theatres) theatre.getSelectedItem();
        ReadXML.readscheduleXML(valittu);
        ArrayAdapter<Movie> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ReadXML.shows_array);
        listview.setAdapter(adapter2);
    }


}