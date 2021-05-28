package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView register;
    ProgressBar progressBar;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passError);
        progressBar = findViewById(R.id.progress);

        login.setOnClickListener(v -> {
            if (SetValidation()) {
                String text1, text2;
                text1 = email.getText().toString();
                text2 = password.getText().toString();

                //Start ProgressBar first (Set visibility VISIBLE)
                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.post(() -> {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "password";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = text1;
                    data[1] = text2;
                    PutData putData = new PutData("http://localhost/LoginRegister/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Email or Password wrong")) {
                                emailError.setError(getResources().getString(R.string.error_invalid_email));
                                passError.setError(getResources().getString(R.string.error_invalid_password));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                int userid = Integer.parseInt(result.trim());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userid", userid);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        register.setOnClickListener(v -> {
            // redirect to RegisterActivity
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public boolean SetValidation() {
        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        return isEmailValid && isPasswordValid;
    }

}
