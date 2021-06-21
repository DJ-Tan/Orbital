package com.example.loginpage;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ProfileFragment extends Fragment {

    int userid;
    String username, gender, bloodtype, allergy;
    int wait = 0;
    TextView textUsername, noEditGender, noEditBlood;
    EditText editAllergy;
    Spinner spinGender, spinBlood;
    Button edit, confirm;
    TextInputLayout allergyError;
    ProgressBar profileProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userid = bundle.getInt("userid");
            username = bundle.getString("username");
        }

        textUsername = view.findViewById(R.id.profile_username);
        editAllergy = view.findViewById(R.id.profile_allergy);
        spinGender = view.findViewById(R.id.profile_gender);
        spinBlood = view.findViewById(R.id.profile_bloodtype);
        edit = view.findViewById(R.id.profile_edit);
        confirm = view.findViewById(R.id.profile_confirm);
        allergyError = view.findViewById(R.id.profile_allergyError);
        profileProgress = view.findViewById(R.id.profile_progress);
        noEditGender = view.findViewById(R.id.profile_gender_noEdit);
        noEditBlood = view.findViewById(R.id.profile_blood_noEdit);

        textUsername.setText(username);

        toggleEdit(false);

        // Set options for blood group drop down menu
        ArrayAdapter<CharSequence> adapterBlood = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.blood_group_array, android.R.layout.simple_spinner_item);
        adapterBlood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBlood.setAdapter(adapterBlood);

        // Set options for gender drop down menu
        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(adapterGender);

        // Set on click listener for edit button
        edit.setOnClickListener(v -> {
            toggleEdit(true);
        });

        // Set on click listener for confirm button (update details on SQL server)
        confirm.setOnClickListener(v -> {
            // Check if the allergy field is blank
            String dataAllergy = editAllergy.getText().toString();
            if (dataAllergy.isEmpty()) {
                allergyError.setError("Indicate NIL if there is no allergy");
            } else {
                profileProgress.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(() -> {
                    String[] field = new String[4];
                    field[0] = "id";
                    field[1] = "gender";
                    field[2] = "bloodtype";
                    field[3] = "allergy";
                    String[] data = new String[4];
                    data[0] = Integer.toString(userid);
                    data[1] = spinGender.getSelectedItem().toString();
                    data[2] = spinBlood.getSelectedItem().toString();
                    data[3] = editAllergy.getText().toString();
                    PutData putData = new PutData("http://192.168.50.13/LoginRegister/updatePersonal.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            allergyError.setError(null);
                            profileProgress.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Updated Successfully")) {
                                noEditGender.setText(spinGender.getSelectedItem().toString());
                                noEditBlood.setText(spinBlood.getSelectedItem().toString());
                                toggleEdit(false);
                            } else {
                                Log.d("orbital", "fail to update details");
                            }
                        }
                    }
                });
            }
        });

        // Retrieve personal information from the SQL server
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "id";
            String[] data = new String[1];
            data[0] = Integer.toString(userid);
            PutData putData = new PutData("http://192.168.50.13/LoginRegister/retrievePersonal.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Error: Database connection")) {
                        Log.d("orbital", "fail to connect");
                    } else {
                        String[] words = result.split("#");
                        gender = words[0];
                        bloodtype = words[1];
                        allergy = words[2];
                        noEditGender.setText(gender);
                        noEditBlood.setText(bloodtype);
                        editAllergy.setText(allergy);
                    }
                }
            }
        });
        return view;
    }

    void toggleEdit(boolean toEdit) {
        if (toEdit) {
            spinGender.setVisibility(View.VISIBLE);
            spinBlood.setVisibility(View.VISIBLE);
            noEditBlood.setVisibility(View.GONE);
            noEditGender.setVisibility(View.GONE);
            editAllergy.setEnabled(true);
            spinGender.setEnabled(true);
            spinBlood.setEnabled(true);
            edit.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
        } else {
            spinGender.setVisibility(View.GONE);
            spinBlood.setVisibility(View.GONE);
            noEditBlood.setVisibility(View.VISIBLE);
            noEditGender.setVisibility(View.VISIBLE);
            editAllergy.setEnabled(false);
            spinGender.setEnabled(false);
            spinBlood.setEnabled(false);
            edit.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
        }
    }

}
