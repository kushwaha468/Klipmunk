package com.example.klipmunk;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.util.Patterns.EMAIL_ADDRESS;

public class Signup_Activity extends AppCompatActivity {

    EditText edituser,editemail,editpass,editpass2;
    Button btnsignup;
    UserService userService;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        edituser=findViewById(R.id.edituser);
        editemail=findViewById(R.id.editemail);
        editpass = findViewById(R.id.editpass);
        editpass2 = findViewById(R.id.editpass2);
        btnsignup = findViewById(R.id.btnsingup);
        bar=findViewById(R.id.bar);

        userService = ApiUtils.getUserService();

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username =edituser.getText().toString().trim();
                String email = editemail.getText().toString().trim();
                String password =editpass.getText().toString().trim();
                String password2 = editpass2.getText().toString().trim();
                    // To validate
                if( validate(username,email,password,password2)){
                        // do Signup
                        doSignup(username,email,password,password2);
                }
            }

        });

    }

    private boolean validate(String username, String email, String password, String password2) {

        if(username.trim().length() == 0){
            Toast.makeText(this, "Username has to be entered", Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(email.trim().length()==0 ||!EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter valid Email/Username",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(password.trim().length() == 0)
        {
            Toast.makeText(this, "Password has to be entered", Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(password.trim().length() <6){

            Toast.makeText(this, "Password has atleast 6 characters", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(!password.equals(password2)){

                Toast.makeText(this, "Passwords have to be matching", Toast.LENGTH_SHORT).show();
                return false;
            }



        return true;
    }



    private void doSignup(final String username,final String email,final String password,final String password2) {


        bar.setVisibility(View.VISIBLE);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name",username);
        hashMap.put("email",email);
        hashMap.put("password",password);
        hashMap.put("password2",password2);


        Call<JsonObject> call=userService.signup(hashMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null) {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(Signup_Activity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Signup_Activity.this, "Error! Please try again"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Signup_Activity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }



}
