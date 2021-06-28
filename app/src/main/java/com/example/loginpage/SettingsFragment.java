package com.example.loginpage;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SettingsFragment extends Fragment {

    int userid;
    EditText oldPass, newPass, retypePass;
    LinearLayout passLayout;
    Button changePass, confirmPass;
    TextView passError;
    boolean passMatch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userid = bundle.getInt("userid");
        }

        oldPass = view.findViewById(R.id.setting_oldPassword);
        newPass = view.findViewById(R.id.setting_newPassword);
        retypePass = view.findViewById(R.id.setting_retypePassword);
        passLayout = view.findViewById(R.id.setting_passChange);
        changePass = (Button)view.findViewById(R.id.setting_openPass);
        confirmPass = (Button)view.findViewById(R.id.setting_confirmPass);
        passError = view.findViewById(R.id.setting_passError);

        // Set on click listener to open up password change options
        changePass.setOnClickListener(v -> {
            resetText();
            if (passLayout.getVisibility() == View.GONE) {
                passLayout.setVisibility(View.VISIBLE);
            } else {
                passLayout.setVisibility(View.GONE);
            }
        });

        // Set on click listener to confirm change in password
        confirmPass.setOnClickListener(v -> {
            String pass1, pass2, pass3;
            pass1 = oldPass.getText().toString();
            pass2 = newPass.getText().toString();
            pass3 = retypePass.getText().toString();

            passError.setText("");

            Handler handler1 = new Handler();
            handler1.post(() -> {
                String[] field = new String[2];
                field[0] = "id";
                field[1] = "password";
                String[] data = new String[2];
                data[0] = Integer.toString(userid);
                data[1] = pass1;
                PutData putData = new PutData("http://192.168.50.13/LoginRegister/checkPass.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        passMatch = result.equals("Password Match");

                        // Performs the following error checking:
                        // (1) Checks if any field is left blank
                        // (2) Checks if old password matches that of database
                        // (3) Checks if new password is the same as the old password
                        // (4) Checks if new password has the minimum number of characters
                        // (5) Checks if new password has uppercase letters
                        // (6) Checks if new password has lowercase letters
                        // (7) Checks if new password has numeric values
                        // (8) Checks if new password has symbols
                        // (9) Checks if retyped password matches new password

                        if (pass1.isEmpty() || pass2.isEmpty() || pass3.isEmpty()) {
                            passError.setText(getResources().getString(R.string.error_blank));
                        } else if (!passMatch) {
                            passError.setText(getResources().getString(R.string.error_matchDB));
                        } else if (pass1.equals(pass2)) {
                            passError.setText(getResources().getString(R.string.error_old_new_match));
                        } else if (pass2.length() < 8) {
                            passError.setText(getResources().getString(R.string.error_length));
                        } else if (!checkUppercase(pass2)) {
                            passError.setText(getResources().getString(R.string.error_uppercase));
                        } else if (!checkLowercase(pass2)) {
                            passError.setText(getResources().getString(R.string.error_lowercase));
                        } else if (!checkDigit(pass2)) {
                            passError.setText(getResources().getString(R.string.error_digit));
                        } else if (!checkSymbol(pass2)) {
                            passError.setText(getResources().getString(R.string.error_symbol));
                        } else if (!pass2.equals(pass3)) {
                            passError.setText(getResources().getString(R.string.match_error));
                        } else {
                            Handler handler2 = new Handler();
                            handler2.post(() -> {
                                String[] field2 = new String[2];
                                field2[0] = "id";
                                field2[1] = "password";
                                String[] data2 = new String[2];
                                data2[0] = Integer.toString(userid);
                                data2[1] = pass2;
                                PutData putData2 = new PutData("http://192.168.50.13/LoginRegister/updatePass.php", "POST", field2, data2);
                                if (putData2.startPut()) {
                                    if (putData2.onComplete()) {
                                        String result2 = putData2.getResult();
                                        if (result2.equals("Changed Successfully")) {
                                            passError.setText("");
                                            passLayout.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }

                    }
                }
            });


        });


        return view;
    }

    public boolean checkUppercase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLowercase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSymbol(String password) {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        for (int i = 0; i < password.length(); i++) {
            if (specialChars.contains(String.valueOf(password.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    void resetText() {
        oldPass.setText("");
        newPass.setText("");
        retypePass.setText("");
    }
}
