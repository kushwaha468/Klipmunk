package com.example.klipmunk;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;


import java.util.HashMap;

import static android.util.Patterns.EMAIL_ADDRESS;

public class Login_Activity extends AppCompatActivity {
    Button btnsignin;
    TextView tv_signup;
    EditText edtUsername,edtPassword;
    UserService userService;
    ProgressBar bar12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnsignin=findViewById(R.id.btnsignin);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        tv_signup=findViewById(R.id.textv_signup);
        bar12=findViewById(R.id.bar12);
        userService=ApiUtils.getUserService();

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(validateLogin(username,password))
                {
                    //doLogin
                    doLogin(username,password);
                }
            }

        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa= new Intent(Login_Activity.this,Signup_Activity.class);
                startActivity(aa);
            }
        });


    }
    private boolean validateLogin(String username,String password)
    {
        if(username.isEmpty()||!EMAIL_ADDRESS.matcher(username).matches()){
            Toast.makeText(getApplicationContext(),"Please enter valid Email/Username",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.isEmpty()|| password.length()<6) {
            Toast.makeText(getApplicationContext(), "Please enter Password correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username,final String password)
    {
        bar12.setVisibility(View.VISIBLE);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("email",username);
        hashMap.put("password",password);

        Call<JsonObject> call=userService.login(hashMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                bar12.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Login_Activity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login_Activity.this, "Error! Please try again"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
