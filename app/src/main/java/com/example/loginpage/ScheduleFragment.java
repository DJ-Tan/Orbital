package com.example.loginpage;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ScheduleFragment extends Fragment {

    int userid, storage_num, dosageAmount = 0, dosageMax;
    Boolean flexibleTime;
    String dosageDays = "0000000", dosageTimes = "0000", mTime = "06:00", aTime = "12:00", eTime = "18:00", nTime = "00:00";
    String pillName1, pillName2, pillName3, pillName4, pillName5, pillName6;
    LinearLayout doseInformation;
    LinearLayout mLayout, aLayout, eLayout, nLayout;
    Button storage_1, storage_2, storage_3, storage_4, storage_5, storage_6;
    Button mon, tue, wed, thu, fri, sat, sun;
    Button confirmSelect, cancelSelect;
    Button m_add, a_add, e_add, n_add;
    Button m_remove, a_remove, e_remove, n_remove;
    EditText m_hour, a_hour, e_hour, n_hour;
    EditText m_min, a_min, e_min, n_min;
    EditText dosageQty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userid = bundle.getInt("userid");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Binding all variables to the corresponding fields

        doseInformation = view.findViewById(R.id.schedule_details);

        mLayout = view.findViewById(R.id.schedule_mornTime);
        aLayout = view.findViewById(R.id.schedule_afternoonTime);
        eLayout = view.findViewById(R.id.schedule_eveningTime);
        nLayout = view.findViewById(R.id.schedule_nightTime);

        storage_1 = view.findViewById(R.id.schedule_storage1);
        storage_2 = view.findViewById(R.id.schedule_storage2);
        storage_3 = view.findViewById(R.id.schedule_storage3);
        storage_4 = view.findViewById(R.id.schedule_storage4);
        storage_5 = view.findViewById(R.id.schedule_storage5);
        storage_6 = view.findViewById(R.id.schedule_storage6);

        mon = view.findViewById(R.id.schedule_monday);
        tue = view.findViewById(R.id.schedule_tuesday);
        wed = view.findViewById(R.id.schedule_wednesday);
        thu = view.findViewById(R.id.schedule_thursday);
        fri = view.findViewById(R.id.schedule_friday);
        sat = view.findViewById(R.id.schedule_saturday);
        sun = view.findViewById(R.id.schedule_sunday);

        m_add = view.findViewById(R.id.schedule_mornAdd);
        a_add = view.findViewById(R.id.schedule_afternoonAdd);
        e_add = view.findViewById(R.id.schedule_eveningAdd);
        n_add = view.findViewById(R.id.schedule_nightAdd);

        m_remove = view.findViewById(R.id.schedule_mornRemove);
        a_remove = view.findViewById(R.id.schedule_afternoonRemove);
        e_remove = view.findViewById(R.id.schedule_eveningRemove);
        n_remove = view.findViewById(R.id.schedule_nightRemove);

        m_hour = view.findViewById(R.id.schedule_mornHour);
        a_hour = view.findViewById(R.id.schedule_afternoonHour);
        e_hour = view.findViewById(R.id.schedule_eveningHour);
        n_hour = view.findViewById(R.id.schedule_nightHour);

        m_min = view.findViewById(R.id.schedule_mornMinute);
        a_min = view.findViewById(R.id.schedule_afternoonMinute);
        e_min = view.findViewById(R.id.schedule_eveningMinute);
        n_min = view.findViewById(R.id.schedule_nightMinute);

        dosageQty = view.findViewById(R.id.schedule_quantity);
        confirmSelect = view.findViewById(R.id.schedule_confirm);
        cancelSelect = view.findViewById(R.id.schedule_return);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set the default view of the screen

        doseInformation.setVisibility(View.GONE);
        getPillName();

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set on click listeners for storage buttons

        storage_1.setOnClickListener(v -> toggleStorageButtons(1));
        storage_2.setOnClickListener(v -> toggleStorageButtons(2));
        storage_3.setOnClickListener(v -> toggleStorageButtons(3));
        storage_4.setOnClickListener(v -> toggleStorageButtons(4));
        storage_5.setOnClickListener(v -> toggleStorageButtons(5));
        storage_6.setOnClickListener(v -> toggleStorageButtons(6));

        // Set on click listeners for day buttons

        mon.setOnClickListener(v -> updateDayFields(1));
        tue.setOnClickListener(v -> updateDayFields(2));
        wed.setOnClickListener(v -> updateDayFields(3));
        thu.setOnClickListener(v -> updateDayFields(4));
        fri.setOnClickListener(v -> updateDayFields(5));
        sat.setOnClickListener(v -> updateDayFields(6));
        sun.setOnClickListener(v -> updateDayFields(7));

        // Set on click listeners for add/remove buttons

        m_add.setOnClickListener(v -> toggleAddRemove(1, 1));
        a_add.setOnClickListener(v -> toggleAddRemove(2, 1));
        e_add.setOnClickListener(v -> toggleAddRemove(3, 1));
        n_add.setOnClickListener(v -> toggleAddRemove(4, 1));

        m_remove.setOnClickListener(v -> toggleAddRemove(1, 2));
        a_remove.setOnClickListener(v -> toggleAddRemove(2, 2));
        e_remove.setOnClickListener(v -> toggleAddRemove(3, 2));
        n_remove.setOnClickListener(v -> toggleAddRemove(4, 2));

        // Set on click listeners for confirm/cancel buttons

        confirmSelect.setOnClickListener(v -> confirmSelection());
        cancelSelect.setOnClickListener(v -> cancelSelection());

        return view;
    }

    // Obtain pill names for the six storage containers and sets the names
    void getPillName()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "id";
            String[] data = new String[1];
            data[0] = Integer.toString(userid);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/pillName.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String[] words = result.split("#");
                    pillName1 = "  Storage 1: " + words[0];
                    pillName2 = "  Storage 2: " + words[1];
                    pillName3 = "  Storage 3: " + words[2];
                    pillName4 = "  Storage 4: " + words[3];
                    pillName5 = "  Storage 5: " + words[4];
                    pillName6 = "  Storage 6: " + words[5];

                    storage_1.setText(pillName1);
                    storage_2.setText(pillName2);
                    storage_3.setText(pillName3);
                    storage_4.setText(pillName4);
                    storage_5.setText(pillName5);
                    storage_6.setText(pillName6);

                    setButtonClickable();
                }
            }
        });
    }

    // Queries pill information from SQL database
    void getDosage()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[2];
            field[0] = "id";
            field[1] = "container";
            String[] data = new String[2];
            data[0] = Integer.toString(userid);
            data[1] = Integer.toString(storage_num);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/getDosage.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String[] words = result.split("#");
                    dosageDays = intToBinary(Integer.parseInt(words[0]), 7);
                    dosageTimes = intToBinary(Integer.parseInt(words[1]), 4);
                    dosageAmount = Integer.parseInt(words[2]);
                    mTime = words[3];
                    aTime = words[4];
                    eTime = words[5];
                    nTime = words[6];
                    buttonsAddRemove();
                    getQuantity();
                }
            }
        });
    }

    // Obtains number of pills stored in that container
    void getQuantity()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[2];
            field[0] = "id";
            field[1] = "container";
            String[] data = new String[2];
            data[0] = Integer.toString(userid);
            data[1] = Integer.toString(storage_num);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/pillDetails.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String[] words = result.split("#");
                    dosageMax = Integer.parseInt(words[1]);
                    setDayFields();
                    setTimeFields();
                    checkFlexi();
                    doseInformation.setVisibility(View.VISIBLE);
                }
            }
        });
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
                    } else if (result.equals("Flexible time off")) {
                        flexibleTime = false;
                    }
                }
            }
        });
    }

    void setButtonClickable()
    {
        storage_1.setEnabled(true);
        storage_2.setEnabled(true);
        storage_3.setEnabled(true);
        storage_4.setEnabled(true);
        storage_5.setEnabled(true);
        storage_6.setEnabled(true);

        if (pillName1.equals("  Storage 1: NIL")) {
            storage_1.setEnabled(false);
        }
        if (pillName2.equals("  Storage 2: NIL")) {
            storage_2.setEnabled(false);
        }
        if (pillName3.equals("  Storage 3: NIL")) {
            storage_3.setEnabled(false);
        }
        if (pillName4.equals("  Storage 4: NIL")) {
            storage_4.setEnabled(false);
        }
        if (pillName5.equals("  Storage 5: NIL")) {
            storage_5.setEnabled(false);
        }
        if (pillName6.equals("  Storage 6: NIL")) {
            storage_6.setEnabled(false);
        }
    }

    // Checks if the times input into the fields are valid and within the range
    Boolean checkTime()
    {
        int mHourTime = Integer.parseInt(mTime.substring(0,2));
        int mMinTime = Integer.parseInt(mTime.substring(3));
        int aHourTime = Integer.parseInt(aTime.substring(0,2));
        int aMinTime = Integer.parseInt(aTime.substring(3));
        int eHourTime = Integer.parseInt(eTime.substring(0,2));
        int eMinTime = Integer.parseInt(eTime.substring(3));
        int nHourTime = Integer.parseInt(nTime.substring(0,2));
        int nMinTime = Integer.parseInt(nTime.substring(3));

        return (nHourTime >= 0 && nHourTime <= 5) && (mHourTime >= 6 && mHourTime <= 11) &&
                (aHourTime >= 12 && aHourTime <= 17) && (eHourTime >= 18 && eHourTime <= 23) &&
                (mMinTime >= 0 && mMinTime <= 59) && (aMinTime >= 0 && aMinTime <= 59) &&
                (eMinTime >= 0 && eMinTime <= 59) && (nMinTime >= 0 && nMinTime <= 59);
    }

    // Checks if the dosage is within the number of pills in the container
    Boolean checkMaxQty()
    {
        int selectedQty = Integer.parseInt(dosageQty.getText().toString());
        return dosageMax >= selectedQty;
    }

    // Opens the options to change the dosage details
    void toggleStorageButtons(int storage)
    {
        storage_num = storage;

        storage_1.setVisibility(View.GONE);
        storage_2.setVisibility(View.GONE);
        storage_3.setVisibility(View.GONE);
        storage_4.setVisibility(View.GONE);
        storage_5.setVisibility(View.GONE);
        storage_6.setVisibility(View.GONE);

        if (storage == 1) {
            storage_1.setVisibility(View.VISIBLE);
        } else if (storage == 2) {
            storage_2.setVisibility(View.VISIBLE);
        } else if (storage == 3) {
            storage_3.setVisibility(View.VISIBLE);
        } else if (storage == 4) {
            storage_4.setVisibility(View.VISIBLE);
        } else if (storage == 5) {
            storage_5.setVisibility(View.VISIBLE);
        } else if (storage == 6) {
            storage_6.setVisibility(View.VISIBLE);
        }

        storage_1.setEnabled(false);
        storage_2.setEnabled(false);
        storage_3.setEnabled(false);
        storage_4.setEnabled(false);
        storage_5.setEnabled(false);
        storage_6.setEnabled(false);

        getDosage();
    }

    // Toggles display when day add/remove buttons and updates dosageTimes
    void toggleAddRemove(int time_of_day, int add_remove)
    {
        if (add_remove == 1) {
            if (time_of_day == 1) {
                mLayout.setVisibility(View.VISIBLE);
                m_add.setVisibility(View.GONE);
                m_remove.setVisibility(View.VISIBLE);
                dosageTimes = '1' + dosageTimes.substring(1);
            } else if (time_of_day == 2) {
                aLayout.setVisibility(View.VISIBLE);
                a_add.setVisibility(View.GONE);
                a_remove.setVisibility(View.VISIBLE);
                dosageTimes = dosageTimes.substring(0,1) + '1' + dosageTimes.substring(2);
            } else if (time_of_day == 3) {
                eLayout.setVisibility(View.VISIBLE);
                e_add.setVisibility(View.GONE);
                e_remove.setVisibility(View.VISIBLE);
                dosageTimes = dosageTimes.substring(0,2) + '1' + dosageTimes.substring(3);
            } else if (time_of_day == 4) {
                nLayout.setVisibility(View.VISIBLE);
                n_add.setVisibility(View.GONE);
                n_remove.setVisibility(View.VISIBLE);
                dosageTimes = dosageTimes.substring(0,3) + '1';
            }
        } else if (add_remove == 2) {
            if (time_of_day == 1) {
                mLayout.setVisibility(View.GONE);
                m_add.setVisibility(View.VISIBLE);
                m_remove.setVisibility(View.GONE);
                dosageTimes = '0' + dosageTimes.substring(1);
            } else if (time_of_day == 2) {
                aLayout.setVisibility(View.GONE);
                a_add.setVisibility(View.VISIBLE);
                a_remove.setVisibility(View.GONE);
                dosageTimes = dosageTimes.substring(0,1) + '0' + dosageTimes.substring(2);
            } else if (time_of_day == 3) {
                eLayout.setVisibility(View.GONE);
                e_add.setVisibility(View.VISIBLE);
                e_remove.setVisibility(View.GONE);
                dosageTimes = dosageTimes.substring(0,2) + '0' + dosageTimes.substring(3);
            } else if (time_of_day == 4) {
                nLayout.setVisibility(View.GONE);
                n_add.setVisibility(View.VISIBLE);
                n_remove.setVisibility(View.GONE);
                dosageTimes = dosageTimes.substring(0,3) + '0';
            }
        }
    }

    // Set visibility of add/remove buttons for times of day
    void buttonsAddRemove()
    {
        if (dosageTimes.charAt(0) == '0') {
            mLayout.setVisibility(View.GONE);
            m_add.setVisibility(View.VISIBLE);
            m_remove.setVisibility(View.GONE);
        } else {
            mLayout.setVisibility(View.VISIBLE);
            m_add.setVisibility(View.GONE);
            m_remove.setVisibility(View.VISIBLE);
        }

        if (dosageTimes.charAt(1) == '0') {
            aLayout.setVisibility(View.GONE);
            a_add.setVisibility(View.VISIBLE);
            a_remove.setVisibility(View.GONE);
        } else {
            aLayout.setVisibility(View.VISIBLE);
            a_add.setVisibility(View.GONE);
            a_remove.setVisibility(View.VISIBLE);
        }

        if (dosageTimes.charAt(2) == '0') {
            eLayout.setVisibility(View.GONE);
            e_add.setVisibility(View.VISIBLE);
            e_remove.setVisibility(View.GONE);
        } else {
            eLayout.setVisibility(View.VISIBLE);
            e_add.setVisibility(View.GONE);
            e_remove.setVisibility(View.VISIBLE);
        }

        if (dosageTimes.charAt(3) == '0') {
            nLayout.setVisibility(View.GONE);
            n_add.setVisibility(View.VISIBLE);
            n_remove.setVisibility(View.GONE);
        } else {
            nLayout.setVisibility(View.VISIBLE);
            n_add.setVisibility(View.GONE);
            n_remove.setVisibility(View.VISIBLE);
        }
    }

    // Set the background of the day buttons based on dosageDays
    void setDayFields()
    {
        if (dosageDays.charAt(0) == '0') {
            mon.setBackgroundResource(R.drawable.bttn_release);
        } else {
            mon.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(1) == '0') {
            tue.setBackgroundResource(R.drawable.bttn_release);
        } else {
            tue.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(2) == '0') {
            wed.setBackgroundResource(R.drawable.bttn_release);
        } else {
            wed.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(3) == '0') {
            thu.setBackgroundResource(R.drawable.bttn_release);
        } else {
            thu.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(4) == '0') {
            fri.setBackgroundResource(R.drawable.bttn_release);
        } else {
            fri.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(5) == '0') {
            sat.setBackgroundResource(R.drawable.bttn_release);
        } else {
            sat.setBackgroundResource(R.drawable.bttn_depressed);
        }

        if (dosageDays.charAt(6) == '0') {
            sun.setBackgroundResource(R.drawable.bttn_release);
        } else {
            sun.setBackgroundResource(R.drawable.bttn_depressed);
        }

    }

    // Set the value of the time fields based on m/a/e/n times
    void setTimeFields()
    {
        String[] mTiming = mTime.split(":");
        String[] aTiming = aTime.split(":");
        String[] eTiming = eTime.split(":");
        String[] nTiming = nTime.split(":");
        m_hour.setText(mTiming[0]);
        m_min.setText(mTiming[1]);
        a_hour.setText(aTiming[0]);
        a_min.setText(aTiming[1]);
        e_hour.setText(eTiming[0]);
        e_min.setText(eTiming[1]);
        n_hour.setText(nTiming[0]);
        n_min.setText(nTiming[1]);

    }

    // Update the background of day buttons as well as dosageDays when buttons are pressed
    void updateDayFields(int day_num)
    {
        String dayString = null;
        if (day_num == 1) {
            if (dosageDays.charAt(0) == '0') {
                dayString = '1' + dosageDays.substring(1);
            } else if (dosageDays.charAt(0) == '1') {
                dayString = '0' + dosageDays.substring(1);
            }
        } else if (day_num == 2) {
            if (dosageDays.charAt(1) == '0') {
                dayString = dosageDays.substring(0,1) + '1' + dosageDays.substring(2);
            } else if (dosageDays.charAt(1) == '1') {
                dayString = dosageDays.substring(0,1) + '0' + dosageDays.substring(2);
            }
        } else if (day_num == 3) {
            if (dosageDays.charAt(2) == '0') {
                dayString = dosageDays.substring(0,2) + '1' + dosageDays.substring(3);
            } else if (dosageDays.charAt(2) == '1') {
                dayString = dosageDays.substring(0,2) + '0' + dosageDays.substring(3);
            }
        } else if (day_num == 4) {
            if (dosageDays.charAt(3) == '0') {
                dayString = dosageDays.substring(0,3) + '1' + dosageDays.substring(4);
            } else if (dosageDays.charAt(3) == '1') {
                dayString = dosageDays.substring(0,3) + '0' + dosageDays.substring(4);
            }
        } else if (day_num == 5) {
            if (dosageDays.charAt(4) == '0') {
                dayString = dosageDays.substring(0,4) + '1' + dosageDays.substring(5);
            } else if (dosageDays.charAt(4) == '1') {
                dayString = dosageDays.substring(0,4) + '0' + dosageDays.substring(5);
            }
        } else if (day_num == 6) {
            if (dosageDays.charAt(5) == '0') {
                dayString = dosageDays.substring(0,5) + '1' + dosageDays.substring(6);
            } else if (dosageDays.charAt(5) == '1') {
                dayString = dosageDays.substring(0,5) + '0' + dosageDays.substring(6);
            }
        } else if (day_num == 7) {
            if (dosageDays.charAt(6) == '0') {
                dayString = dosageDays.substring(0,6) + '1';
            } else if (dosageDays.charAt(6) == '1') {
                dayString = dosageDays.substring(0,6) + '0';
            }
        }
        dosageDays = dayString;
        setDayFields();
    }

    // Updates database with all the information
    void updateDatabase()
    {
        mTime = m_hour.getText().toString() + ':' + m_min.getText().toString();
        aTime = a_hour.getText().toString() + ':' + a_min.getText().toString();
        eTime = e_hour.getText().toString() + ':' + e_min.getText().toString();
        nTime = n_hour.getText().toString() + ':' + n_min.getText().toString();
        dosageAmount = Integer.parseInt(dosageQty.getText().toString());

        Handler handler1 = new Handler();
        handler1.post(() -> {
            String[] field1 = new String[5];
            field1[0] = "id";
            field1[1] = "container";
            field1[2] = "dosagedays";
            field1[3] = "dosagetimes";
            field1[4] = "dosageamount";
            String[] data1 = new String[5];
            data1[0] = Integer.toString(userid);
            data1[1] = Integer.toString(storage_num);
            data1[2] = Integer.toString(binaryToInt(dosageDays));
            data1[3] = Integer.toString(binaryToInt(dosageTimes));
            data1[4] = Integer.toString(dosageAmount);
            PutData putData1 = new PutData("https://orbital-cygnus.herokuapp.com/addDosage.php", "POST", field1, data1);
            if (putData1.startPut()) {
                if (putData1.onComplete()) {
                    String result1 = putData1.getResult();
                    if (result1.equals("Added Successfully")) {
                        Handler handler2 = new Handler();
                        handler2.post(() -> {
                            String[] field2 = new String[6];
                            field2[0] = "id";
                            field2[1] = "container";
                            field2[2] = "mTime";
                            field2[3] = "aTime";
                            field2[4] = "eTime";
                            field2[5] = "nTime";
                            String[] data2 = new String[6];
                            data2[0] = Integer.toString(userid);
                            data2[1] = Integer.toString(storage_num);
                            data2[2] = mTime;
                            data2[3] = aTime;
                            data2[4] = eTime;
                            data2[5] = nTime;
                            PutData putData2 = new PutData("https://orbital-cygnus.herokuapp.com/addTime.php", "POST", field2, data2);
                            if (putData2.startPut()) {
                                if (putData2.onComplete()) {
                                    String result2 = putData2.getResult();
                                    if (result2.equals("Added Successfully")) {
                                        Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                        cancelSelection();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // Checks if there are time/quantity errors before updating database
    void confirmSelection()
    {
        if (checkTime() && checkMaxQty()) {
            updateDatabase();
        } else if (!checkTime()){
            Toast.makeText(getActivity(), "Invalid time intervals", Toast.LENGTH_SHORT).show();
        } else if (!checkMaxQty()) {
            Toast.makeText(getActivity(), "Invalid dosage quantity", Toast.LENGTH_SHORT).show();
        }
    }

    // Closes the dosage menu and returns to default screen with all storages
    void cancelSelection()
    {
        doseInformation.setVisibility(View.GONE);
        storage_1.setVisibility(View.VISIBLE);
        storage_2.setVisibility(View.VISIBLE);
        storage_3.setVisibility(View.VISIBLE);
        storage_4.setVisibility(View.VISIBLE);
        storage_5.setVisibility(View.VISIBLE);
        storage_6.setVisibility(View.VISIBLE);
        setButtonClickable();
    }

    // Convert from integer to binary string
    String intToBinary(int num, int length)
    {
        String binaryValue = "";
        int count = 0;
        while (num != 0) {
            if (num % 2 == 1) {
                binaryValue = "1" + binaryValue;
            } else {
                binaryValue = "0" + binaryValue;
            }
            num /= 2;
            count++;
        }
        for (int i = count; i < length; i++) {
            binaryValue = "0" + binaryValue;
        }
        return binaryValue;
    }

    // Convert from binary string to integer
    int binaryToInt(String value)
    {
        int num = 0;
        int multiplier = 1;
        for (int i = value.length() - 1; i >= 0; i--) {
            if (value.charAt(i) == '1') {
                num += multiplier;
            }
            multiplier *= 2;
        }
        return num;
    }

}
