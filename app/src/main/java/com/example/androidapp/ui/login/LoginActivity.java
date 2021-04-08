package com.example.androidapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;

public class LoginActivity extends AppCompatActivity {

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
                /*
                Boolean isLogged = false;
                LoginViewModel model = new ViewModelProvider(this).get(LoginViewModel.class);
                model.getLoginCondition().observe(this, new Observer<Boolean>(){
                    @Override
                    public void onChanged(@Nullable Boolean b) {
                        isLogged.setValue(b);
                    }
                });*/
            }
        });
    }


}