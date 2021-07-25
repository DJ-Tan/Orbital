package com.example.loginpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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

    // Variables for password change
    EditText oldPass, newPass, retypePass;
    LinearLayout passLayout;
    Button changePass, confirmPass;
    TextView passError;
    boolean passMatch;

    // Variables for flexible time change
    EditText m_hr, m_min, a_hr, a_min, e_hr, e_min, n_hr, n_min;
    LinearLayout flexLayout, enableLayout, disableLayout;
    Button flexButton, enableButton, disableButton, eReturnButton, dReturnButton;
    boolean flexibleTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userid = bundle.getInt("userid");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Binding all variables to the corresponding fields

        // Fields for password change
        oldPass = view.findViewById(R.id.setting_oldPassword);
        newPass = view.findViewById(R.id.setting_newPassword);
        retypePass = view.findViewById(R.id.setting_retypePassword);
        passLayout = view.findViewById(R.id.setting_passChange);
        changePass = view.findViewById(R.id.setting_openPass);
        confirmPass = view.findViewById(R.id.setting_confirmPass);
        passError = view.findViewById(R.id.setting_passError);

        // Fields for flexible time change
        m_hr = view.findViewById(R.id.setting_mornHour);
        m_min = view.findViewById(R.id.setting_mornMinute);
        a_hr = view.findViewById(R.id.setting_afternoonHour);
        a_min = view.findViewById(R.id.setting_afternoonMinute);
        e_hr = view.findViewById(R.id.setting_eveningHour);
        e_min = view.findViewById(R.id.setting_eveningMinute);
        n_hr = view.findViewById(R.id.setting_nightHour);
        n_min = view.findViewById(R.id.setting_nightMinute);

        flexLayout = view.findViewById(R.id.setting_flexChange);
        enableLayout = view.findViewById(R.id.setting_flexEnableLayout);
        disableLayout = view.findViewById(R.id.setting_flexDisableLayout);

        flexButton = view.findViewById(R.id.setting_openFlexible);
        enableButton = view.findViewById(R.id.setting_flexEnableButton);
        disableButton = view.findViewById(R.id.setting_flexDisableButton);
        eReturnButton = view.findViewById(R.id.setting_flexEnableReturnButton);
        dReturnButton = view.findViewById(R.id.setting_flexDisableReturnButton);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set default view

        passLayout.setVisibility(View.GONE);
        flexLayout.setVisibility(View.GONE);
        enableLayout.setVisibility(View.GONE);
        disableLayout.setVisibility(View.GONE);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set on click listeners

        // Set on click listener to open up password change options
        changePass.setOnClickListener(v -> togglePassView());

        // Set on click listener to confirm change in password
        confirmPass.setOnClickListener(v -> changePassword());

        // Set on click listener to open up time flexibility options
        flexButton.setOnClickListener(v -> toggleFlexView());

        // Set on click listener to confirm change in settings
        enableButton.setOnClickListener(v -> changeFlex());
        disableButton.setOnClickListener(v -> changeFlex());

        // Set on click listener to close time flexibility options
        eReturnButton.setOnClickListener(v -> toggleFlexView());
        dReturnButton.setOnClickListener(v -> toggleFlexView());

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Password change functions

    // Updates password on the database
    void changePassword()
    {
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
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/checkPass.php", "POST", field, data);
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
                            PutData putData2 = new PutData("https://orbital-cygnus.herokuapp.com/updatePass.php", "POST", field2, data2);
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
    }

    // Toggles view of the password change options
    void togglePassView()
    {
        resetText();
        if (passLayout.getVisibility() == View.GONE) {
            passLayout.setVisibility(View.VISIBLE);
        } else {
            passLayout.setVisibility(View.GONE);
        }
    }

    // Checks if string has uppercase letters
    public boolean checkUppercase(String password)
    {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // Checks if string has lowercase letters
    public boolean checkLowercase(String password)
    {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // Checks if string has numerals
    public boolean checkDigit(String password)
    {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // Checks if string has symbols
    public boolean checkSymbol(String password)
    {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        for (int i = 0; i < password.length(); i++) {
            if (specialChars.contains(String.valueOf(password.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    // Reset editText fields for password settings
    void resetText()
    {
        oldPass.setText("");
        newPass.setText("");
        retypePass.setText("");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Time flexibility functions

    // Set original values of time fields in time flexibility options
    @SuppressLint("SetTextI18n")
    void setFlexFields()
    {
        m_hr.setText("06");
        m_min.setText("00");
        a_hr.setText("12");
        a_min.setText("00");
        e_hr.setText("18");
        e_min.setText("00");
        n_hr.setText("00");
        n_min.setText("00");
    }

    // Checks if the user has the flexible time setting turned on
    void checkFlexi()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "id";
            String[] data = new String[1];
            data[0] = Integer.toString(userid);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/checkFlexi.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Flexible time on")) {
                        flexibleTime = true;
                        setFlexFields();
                        enableLayout.setVisibility(View.GONE);
                        disableLayout.setVisibility(View.VISIBLE);
                    } else if (result.equals("Flexible time off")) {
                        flexibleTime = false;
                        enableLayout.setVisibility(View.VISIBLE);
                        disableLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    // Checks if time fields fall within the correct ranges
    Boolean checkFlexTime()
    {
        int mHourTime = Integer.parseInt(m_hr.getText().toString());
        int mMinTime = Integer.parseInt(m_min.getText().toString());
        int aHourTime = Integer.parseInt(a_hr.getText().toString());
        int aMinTime = Integer.parseInt(a_min.getText().toString());
        int eHourTime = Integer.parseInt(e_hr.getText().toString());
        int eMinTime = Integer.parseInt(e_min.getText().toString());
        int nHourTime = Integer.parseInt(n_hr.getText().toString());
        int nMinTime = Integer.parseInt(n_min.getText().toString());

        return (nHourTime >= 0 && nHourTime <= 5) && (mHourTime >= 6 && mHourTime <= 11) &&
                (aHourTime >= 12 && aHourTime <= 17) && (eHourTime >= 18 && eHourTime <= 23) &&
                (mMinTime >= 0 && mMinTime <= 59) && (aMinTime >= 0 && aMinTime <= 59) &&
                (eMinTime >= 0 && eMinTime <= 59) && (nMinTime >= 0 && nMinTime <= 59);
    }

    // Update flex settings in the database
    void changeFlex()
    {
        if (!flexibleTime) {
            onFlexTime();
        } else {
            if (!checkFlexTime()) {
                Toast.makeText(getActivity(), "Invalid times chosen", Toast.LENGTH_SHORT).show();
            } else {
                offFlexTime();
            }
        }
    }

    // Turns off flexible time setting
    void onFlexTime()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "id";
            String[] data = new String[1];
            data[0] = Integer.toString(userid);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/toggleFlexi.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Toggled Successfully")) {
                        flexibleTime = false;
                        Toast.makeText(getActivity(), "Changed Successfully", Toast.LENGTH_SHORT).show();
                        toggleFlexView();
                    } else if (result.equals("Failed to toggle Off to On")) {
                        Toast.makeText(getActivity(), "Error: Failed to change settings", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Turns on flexible time setting
    void offFlexTime()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[5];
            field[0] = "id";
            field[1] = "mTime";
            field[2] = "aTime";
            field[3] = "eTime";
            field[4] = "nTime";
            String[] data = new String[5];
            data[0] = Integer.toString(userid);
            data[1] = m_hr.getText().toString() + ':' + m_min.getText().toString();
            data[2] = a_hr.getText().toString() + ':' + a_min.getText().toString();
            data[3] = e_hr.getText().toString() + ':' + e_min.getText().toString();
            data[4] = n_hr.getText().toString() + ':' + n_min.getText().toString();
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/toggleFlexi.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Toggled Successfully")) {
                        flexibleTime = true;
                        Toast.makeText(getActivity(), "Changed Successfully", Toast.LENGTH_SHORT).show();
                        toggleFlexView();
                    } else if (result.equals("Failed to toggle On to Off")) {
                        Toast.makeText(getActivity(), "Error: Failed to change settings", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Toggles view of the time flexibility options
    void toggleFlexView()
    {
        if (flexLayout.getVisibility() == View.GONE) {
            checkFlexi();
            flexLayout.setVisibility(View.VISIBLE);
        } else {
            flexLayout.setVisibility(View.GONE);
        }
    }


}
