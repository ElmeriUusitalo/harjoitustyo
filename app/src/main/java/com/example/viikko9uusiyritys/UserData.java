package com.example.viikko9uusiyritys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserData extends AppCompatActivity {
    private Button login;
    private Button sign_up;
    private EditText username;
    private EditText password;
    private TextView prompt_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        //setting up id's
        login = (Button) findViewById(R.id.login_button);
        sign_up = (Button) findViewById(R.id.sign_up_button);
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        prompt_text = (TextView) findViewById(R.id.user_prompt);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToAccount();

            }
        });

    }

    private void loginToAccount() {
        String name, pass;
        name = username.getText().toString();
        pass = password.getText().toString();
        //TODO for-looppi joka kaivaa käyttäjät tiedostosta, ja käy ne läpi tsekaten jokaisen usernamen, ja siihen käyvän passwordin
        

    }
}