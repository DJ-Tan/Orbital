package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {

    EditText firstname, lastname, email, password, password_retype;
    Button register;
    TextView login;
    ProgressBar progressBar;
    boolean isFirstNameValid, isLastNameValid, isEmailValid, isPasswordValid, isPasswordRetypeValid;
    TextInputLayout firstNameError, lastNameError, emailError, passError, matchError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.first_name);
        lastname = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password_retype = findViewById(R.id.password_retype);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        firstNameError = findViewById(R.id.first_name_error);
        lastNameError = findViewById(R.id.last_name_error);
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passError);
        matchError = findViewById(R.id.matchError);
        progressBar = findViewById(R.id.progress);

        register.setOnClickListener(v -> {
            if (SetValidation()) {
                String text1, text2, text3, text4;
                text1 = firstname.getText().toString();
                text2 = lastname.getText().toString();
                text3 = email.getText().toString();
                text4 = password.getText().toString();

                //Start ProgressBar first (Set visibility VISIBLE)
                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.post(() -> {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[4];
                    field[0] = "firstname";
                    field[1] = "lastname";
                    field[2] = "email";
                    field[3] = "password";
                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = text1;
                    data[1] = text2;
                    data[2] = text3;
                    data[3] = text4;
                    PutData putData = new PutData("http://192.168.50.13/LoginRegister/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Sign Up Success")) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(v -> {
            // redirect to LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public boolean SetValidation() {
        // Check for a valid first name.
        if (firstname.getText().toString().isEmpty()) {
            firstNameError.setError(getResources().getString(R.string.name_error));
            isFirstNameValid = false;
        } else  {
            isFirstNameValid = true;
            firstNameError.setErrorEnabled(false);
        }

        // Check for a valid last name.
        if (lastname.getText().toString().isEmpty()) {
            lastNameError.setError(getResources().getString(R.string.name_error));
            isLastNameValid = false;
        } else  {
            isLastNameValid = true;
            lastNameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 8) {
            passError.setError(getResources().getString(R.string.error_length));
            isPasswordValid = false;
        } else if (!checkUppercase(password)) {
            passError.setError(getResources().getString(R.string.error_uppercase));
            isPasswordValid = false;
        } else if (!checkLowercase(password)) {
            passError.setError(getResources().getString(R.string.error_lowercase));
            isPasswordValid = false;
        } else if (!checkDigit(password)) {
            passError.setError(getResources().getString(R.string.error_digit));
            isPasswordValid = false;
        } else if (!checkSymbol(password)) {
            passError.setError(getResources().getString(R.string.error_symbol));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        // Check for matching password.
        if (password.getText().toString().equals(password_retype.getText().toString())) {
            isPasswordRetypeValid = true;
            matchError.setErrorEnabled(false);
        } else {
            matchError.setError(getResources().getString(R.string.match_error));
            isPasswordRetypeValid = false;
        }

        // No errors
        return isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid && isPasswordRetypeValid;
    }

    public boolean checkUppercase(EditText text1) {
        String password = text1.getText().toString();
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLowercase(EditText text2) {
        String password = text2.getText().toString();
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDigit(EditText text3) {
        String password = text3.getText().toString();
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSymbol(EditText text4) {
        String password = text4.getText().toString();
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        for (int i = 0; i < password.length(); i++) {
            if (specialChars.contains(String.valueOf(password.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

}