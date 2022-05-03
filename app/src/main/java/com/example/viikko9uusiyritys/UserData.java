package com.example.viikko9uusiyritys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserData extends AppCompatActivity {
    private Button login;
    private Button sign_up;
    private EditText username;
    private EditText password;
    private TextView prompt_text;
    private ArrayList<User> user_list = new ArrayList<>();
    Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        setTitle("kirjaudu sisään.");
        //setting up id's
        login = (Button) findViewById(R.id.login_button);
        sign_up = (Button) findViewById(R.id.sign_up_button);
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        prompt_text = (TextView) findViewById(R.id.user_prompt);


        //loads user list
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("user list", null);
            Type type = new TypeToken<ArrayList<User>>() {}.getType();
            user_list = gson.fromJson(json, type);
        } catch (NullPointerException e) {e.printStackTrace();
        }

        if (user_list == null) {
            user_list = new ArrayList<>();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToAccount();

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAccount();
            }
        });

    }



    private void loginToAccount() {

        String name, pass;
        boolean name_found = false;
        boolean pass_correct = false;
        name = username.getText().toString();
        pass = password.getText().toString();

        for(final User user : user_list){
            if(user.isSameUsername(name)){
                if(user.checkPassword(pass)){

                    resultIntent.putExtra("user", user);



                    pass_correct = true;
                }
                if(!user.checkPassword(pass)){
                    pass_correct = false;
                }
                name_found = true;
            }
        }
        if (name_found == false){
            prompt_text.setText("User not found. Please sign up!");
        }
        if (name_found == true && pass_correct == true){

            prompt_text.setText("Welcome " + name + "!");
            setResult(RESULT_OK, resultIntent);
        }
        if (name_found == true && pass_correct == false){
            prompt_text.setText("Password is not correct!");
        }
        //prompt_text.setText("" + user_list.size());

    }

    private void registerAccount(){

        for(User i : user_list){

            if(i.isSameUsername(username.getText().toString())){
                prompt_text.setText("Username is already taken!");
                return;
            }

        }



        User user = new User(username.getText().toString(), password.getText().toString());
        //System.out.println(username.getText().toString() + password.getText().toString());


        user_list.add(user);


    }


    //Saves all users to gson
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user_list);
        editor.putString("user list", json);
        editor.apply();
    }






}