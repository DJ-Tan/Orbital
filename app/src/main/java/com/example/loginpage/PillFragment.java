package com.example.loginpage;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PillFragment extends Fragment {

    int userid, pillColour_num, pillShape_num, pill_num = 0;
    Boolean colourAll = true, shapeAll = true;
    ScrollView storageView, customView;
    TextView title, nameError;
    EditText pillEditName, pillEditQuantity;
    LinearLayout shapeTop, shapeBottom;
    Button confirmReturn, clearStorage;
    Button pill_1, pill_2, pill_3, pill_4, pill_5, pill_6;
    Button colour_0, colour_1, colour_2, colour_3, colour_4, colour_5;
    Button shape_0, shape_1, shape_2, shape_3, shape_4, shape_5, shape_6;
    String pillName_1, pillName_2, pillName_3, pillName_4, pillName_5, pillName_6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pill, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userid = bundle.getInt("userid");
        }

        // Binding all variables to the corresponding fields
        storageView = view.findViewById(R.id.ps_storage);
        customView = view.findViewById(R.id.ps_custom);
        title = view.findViewById(R.id.ps_title);
        nameError = view.findViewById(R.id.ps_nameError);
        pillEditName = view.findViewById(R.id.ps_pillEditName);
        pillEditQuantity = view.findViewById(R.id.ps_pillEditQuantity);
        shapeTop = view.findViewById(R.id.ps_shapeTop);
        shapeBottom = view.findViewById(R.id.ps_shapeBottom);

        confirmReturn = view.findViewById(R.id.ps_confirm);
        clearStorage = view.findViewById(R.id.ps_clear);

        pill_1 = view.findViewById(R.id.ps_pill1);
        pill_2 = view.findViewById(R.id.ps_pill2);
        pill_3 = view.findViewById(R.id.ps_pill3);
        pill_4 = view.findViewById(R.id.ps_pill4);
        pill_5 = view.findViewById(R.id.ps_pill5);
        pill_6 = view.findViewById(R.id.ps_pill6);

        colour_0 = view.findViewById(R.id.ps_colour0);
        colour_1 = view.findViewById(R.id.ps_colour1);
        colour_2 = view.findViewById(R.id.ps_colour2);
        colour_3 = view.findViewById(R.id.ps_colour3);
        colour_4 = view.findViewById(R.id.ps_colour4);
        colour_5 = view.findViewById(R.id.ps_colour5);

        shape_0 = view.findViewById(R.id.ps_shape0);
        shape_1 = view.findViewById(R.id.ps_shape1);
        shape_2 = view.findViewById(R.id.ps_shape2);
        shape_3 = view.findViewById(R.id.ps_shape3);
        shape_4 = view.findViewById(R.id.ps_shape4);
        shape_5 = view.findViewById(R.id.ps_shape5);
        shape_6 = view.findViewById(R.id.ps_shape6);

        // Set initial view for fragment
        storageView.setVisibility(View.VISIBLE);
        customView.setVisibility(View.GONE);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Buttons for selecting which pill container to edit

        pill_1.setOnClickListener(v -> {
            pill_num = 1;
            obtainPillDetails();
            toggleView(true);
        });

        pill_2.setOnClickListener(v -> {
            pill_num = 2;
            obtainPillDetails();
            toggleView(true);
        });

        pill_3.setOnClickListener(v -> {
            pill_num = 3;
            obtainPillDetails();
            toggleView(true);
        });

        pill_4.setOnClickListener(v -> {
            pill_num = 4;
            obtainPillDetails();
            toggleView(true);
        });

        pill_5.setOnClickListener(v -> {
            pill_num = 5;
            obtainPillDetails();
            toggleView(true);
        });

        pill_6.setOnClickListener(v -> {
            pill_num = 6;
            obtainPillDetails();
            toggleView(true);
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Buttons for selecting the colour of the pills

        colour_0.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(0);
            } else {
                toggleColour(-1);
            }
        });

        colour_1.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(1);
            } else {
                toggleColour(-1);
            }
        });

        colour_2.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(2);
            } else {
                toggleColour(-1);
            }
        });

        colour_3.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(3);
            } else {
                toggleColour(-1);
            }
        });

        colour_4.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(4);
            } else {
                toggleColour(-1);
            }
        });

        colour_5.setOnClickListener(v -> {
            if (colourAll) {
                toggleColour(5);
            } else {
                toggleColour(-1);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Buttons for selecting the shape of the pills

        shape_0.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(0);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_1.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(1);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_2.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(2);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_3.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(3);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_4.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(4);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_5.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(5);
            } else {
                toggleShapeView(-1);
            }
        });

        shape_6.setOnClickListener(v -> {
            if (shapeAll) {
                toggleShapeView(6);
            } else {
                toggleShapeView(-1);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Button to return to storage after making changes

        confirmReturn.setOnClickListener(v -> {
            if (nameCheck(pillEditName.getText().toString())) {
                nameError.setVisibility(View.GONE);
                updatePillDetails();
                updatePillStorageName(pill_num);
                toggleView(false);
            } else {
                nameError.setVisibility(View.VISIBLE);
            }
        });

        // Button to clear storage
        clearStorage.setOnClickListener(v -> clearStorageContainer());

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Query SQL database for pill names if any

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
                    pillName_1 = "  Storage 1: " + words[0];
                    pillName_2 = "  Storage 2: " + words[1];
                    pillName_3 = "  Storage 3: " + words[2];
                    pillName_4 = "  Storage 4: " + words[3];
                    pillName_5 = "  Storage 5: " + words[4];
                    pillName_6 = "  Storage 6: " + words[5];

                    pill_1.setText(pillName_1);
                    pill_2.setText(pillName_2);
                    pill_3.setText(pillName_3);
                    pill_4.setText(pillName_4);
                    pill_5.setText(pillName_5);
                    pill_6.setText(pillName_6);
                }
            }
        });

        return view;
    }

    void updatePillDetails()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[6];
            field[0] = "id";
            field[1] = "container";
            field[2] = "name";
            field[3] = "quantity";
            field[4] = "colour";
            field[5] = "shape";
            String[] data = new String[6];
            data[0] = Integer.toString(userid);
            data[1] = Integer.toString(pill_num);
            data[2] = pillEditName.getText().toString();
            data[3] = pillEditQuantity.getText().toString();
            data[4] = Integer.toString(pillColour_num);
            data[5] = Integer.toString(pillShape_num);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/updatePillDetails.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Updated Successfully")) {
                        Toast.makeText(getActivity(), "Pill Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void obtainPillDetails()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[2];
            field[0] = "id";
            field[1] = "container";
            String[] data = new String[2];
            data[0] = Integer.toString(userid);
            data[1] = Integer.toString(pill_num);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/pillDetails.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String[] words = result.split("#");
                    pillEditName.setText(words[0]);
                    pillEditQuantity.setText(words[1]);
                    pillColour_num = Integer.parseInt(words[2]);
                    pillShape_num = Integer.parseInt(words[3]);
                    toggleColour(pillColour_num);
                    toggleShapeView(pillShape_num);
                    if (pillColour_num == -1 || pillShape_num == -1) {
                        confirmReturn.setVisibility(View.GONE);
                    }
                    colourAll = pillColour_num == -1;
                    shapeAll = pillShape_num == -1;
                }
            }
        });
    }

    // True: Edit view / False: Storage View
    void toggleView(boolean toggle)
    {
        if (toggle) {
            title.setText("Pill Edit");
            storageView.setVisibility(View.GONE);
            customView.setVisibility(View.VISIBLE);
        } else {
            title.setText("Pill Storage");
            storageView.setVisibility(View.VISIBLE);
            customView.setVisibility(View.GONE);
        }
    }

    // Toggles view of the colour (-1 to show all colours)
    void toggleColourView(int pill_colour)
    {
        // Sets all to gone at first
        colour_0.setVisibility(View.GONE);
        colour_1.setVisibility(View.GONE);
        colour_2.setVisibility(View.GONE);
        colour_3.setVisibility(View.GONE);
        colour_4.setVisibility(View.GONE);
        colour_5.setVisibility(View.GONE);
        colourAll = false;

        // Set the pill colour num
        if (pill_colour != -1) {
            pillColour_num = pill_colour;
        }

        if (pill_colour == -1) {
            colour_0.setVisibility(View.VISIBLE);
            colour_1.setVisibility(View.VISIBLE);
            colour_2.setVisibility(View.VISIBLE);
            colour_3.setVisibility(View.VISIBLE);
            colour_4.setVisibility(View.VISIBLE);
            colour_5.setVisibility(View.VISIBLE);
            colourAll = true;
        } else if (pill_colour == 0) {
            colour_0.setVisibility(View.VISIBLE);
        } else if (pill_colour == 1) {
            colour_1.setVisibility(View.VISIBLE);
        } else if (pill_colour == 2) {
            colour_2.setVisibility(View.VISIBLE);
        } else if (pill_colour == 3) {
            colour_3.setVisibility(View.VISIBLE);
        } else if (pill_colour == 4) {
            colour_4.setVisibility(View.VISIBLE);
        } else if (pill_colour == 5) {
            colour_5.setVisibility(View.VISIBLE);
        }

        // Set confirm button visibility
        if (shapeAll || colourAll) {
            confirmReturn.setVisibility(View.GONE);
        } else {
            confirmReturn.setVisibility(View.VISIBLE);
        }
    }

    // Toggles view of the colour (-1 to show all shapes)
    void toggleShapeView(int pill_shape)
    {
        // Sets all to gone at first
        shapeTop.setVisibility(View.GONE);
        shapeBottom.setVisibility(View.GONE);
        shape_0.setVisibility(View.GONE);
        shape_1.setVisibility(View.GONE);
        shape_2.setVisibility(View.GONE);
        shape_3.setVisibility(View.GONE);
        shape_4.setVisibility(View.GONE);
        shape_5.setVisibility(View.GONE);
        shape_6.setVisibility(View.GONE);
        shapeAll = false;

        // Set the pill shape num
        if (pill_shape != -1) {
            pillShape_num = pill_shape;
        }

        // Show all options
        if (pill_shape == -1) {
            shapeTop.setVisibility(View.VISIBLE);
            shapeBottom.setVisibility(View.VISIBLE);
            shape_0.setVisibility(View.VISIBLE);
            shape_1.setVisibility(View.VISIBLE);
            shape_2.setVisibility(View.VISIBLE);
            shape_3.setVisibility(View.VISIBLE);
            shape_4.setVisibility(View.VISIBLE);
            shape_5.setVisibility(View.VISIBLE);
            shape_6.setVisibility(View.VISIBLE);
            shapeAll = true;
        } else if (pill_shape >= 0 && pill_shape <= 3) {
            shapeTop.setVisibility(View.VISIBLE);
            if (pill_shape == 0) {
                shape_0.setVisibility(View.VISIBLE);
            } else if (pill_shape == 1) {
                shape_1.setVisibility(View.VISIBLE);
            } else if (pill_shape == 2) {
                shape_2.setVisibility(View.VISIBLE);
            } else {
                shape_3.setVisibility(View.VISIBLE);
            }
        } else if (pill_shape >= 4 && pill_shape <= 6) {
            shapeBottom.setVisibility(View.VISIBLE);
            if (pill_shape == 4) {
                shape_4.setVisibility(View.VISIBLE);
            } else if (pill_shape == 5) {
                shape_5.setVisibility(View.VISIBLE);
            } else {
                shape_6.setVisibility(View.VISIBLE);
            }
        }

        // Set confirm button visibility
        if (shapeAll || colourAll) {
            confirmReturn.setVisibility(View.GONE);
        } else {
            confirmReturn.setVisibility(View.VISIBLE);
        }
    }

    // Toggles view of the shape colour
    void toggleShapeColour(int pill_colour)
    {
        if (pill_colour == 0) {
            shape_0.setBackgroundResource(R.drawable.pill00);
            shape_1.setBackgroundResource(R.drawable.pill10);
            shape_2.setBackgroundResource(R.drawable.pill20);
            shape_3.setBackgroundResource(R.drawable.pill30);
            shape_4.setBackgroundResource(R.drawable.pill40);
            shape_5.setBackgroundResource(R.drawable.pill50);
            shape_6.setBackgroundResource(R.drawable.pill60);
        } else if (pill_colour == 1) {
            shape_0.setBackgroundResource(R.drawable.pill01);
            shape_1.setBackgroundResource(R.drawable.pill11);
            shape_2.setBackgroundResource(R.drawable.pill21);
            shape_3.setBackgroundResource(R.drawable.pill31);
            shape_4.setBackgroundResource(R.drawable.pill41);
            shape_5.setBackgroundResource(R.drawable.pill51);
            shape_6.setBackgroundResource(R.drawable.pill61);
        } else if (pill_colour == 2) {
            shape_0.setBackgroundResource(R.drawable.pill02);
            shape_1.setBackgroundResource(R.drawable.pill12);
            shape_2.setBackgroundResource(R.drawable.pill22);
            shape_3.setBackgroundResource(R.drawable.pill32);
            shape_4.setBackgroundResource(R.drawable.pill42);
            shape_5.setBackgroundResource(R.drawable.pill52);
            shape_6.setBackgroundResource(R.drawable.pill62);
        } else if (pill_colour == 3) {
            shape_0.setBackgroundResource(R.drawable.pill03);
            shape_1.setBackgroundResource(R.drawable.pill13);
            shape_2.setBackgroundResource(R.drawable.pill23);
            shape_3.setBackgroundResource(R.drawable.pill33);
            shape_4.setBackgroundResource(R.drawable.pill43);
            shape_5.setBackgroundResource(R.drawable.pill53);
            shape_6.setBackgroundResource(R.drawable.pill63);
        } else if (pill_colour == 4) {
            shape_0.setBackgroundResource(R.drawable.pill04);
            shape_1.setBackgroundResource(R.drawable.pill14);
            shape_2.setBackgroundResource(R.drawable.pill24);
            shape_3.setBackgroundResource(R.drawable.pill34);
            shape_4.setBackgroundResource(R.drawable.pill44);
            shape_5.setBackgroundResource(R.drawable.pill54);
            shape_6.setBackgroundResource(R.drawable.pill64);
        } else if (pill_colour == 5) {
            shape_0.setBackgroundResource(R.drawable.pill05);
            shape_1.setBackgroundResource(R.drawable.pill15);
            shape_2.setBackgroundResource(R.drawable.pill25);
            shape_3.setBackgroundResource(R.drawable.pill35);
            shape_4.setBackgroundResource(R.drawable.pill45);
            shape_5.setBackgroundResource(R.drawable.pill55);
            shape_6.setBackgroundResource(R.drawable.pill65);
        }
    }

    // Combines the function for toggleColourView and toggleShapeColour
    void toggleColour(int pill_colour)
    {
        toggleColourView(pill_colour);
        toggleShapeColour(pill_colour);
    }

    // Checks if the name field is left blank
    boolean nameCheck(String name)
    {
        if (name.isEmpty()) {
            return false;
        } else return !name.contains("#");
    }

    void updatePillStorageName(int storage)
    {
        String medicine = pillEditName.getText().toString();

        if (storage == 1) {
            pillName_1 = "  Storage 1: " + medicine;
            pill_1.setText(pillName_1);
        } else if (storage == 2) {
            pillName_2 = "  Storage 2: " + medicine;
            pill_2.setText(pillName_2);
        } else if (storage == 3) {
            pillName_3 = "  Storage 3: " + medicine;
            pill_3.setText(pillName_3);
        } else if (storage == 4) {
            pillName_4 = "  Storage 4: " + medicine;
            pill_4.setText(pillName_4);
        } else if (storage == 5) {
            pillName_5 = "  Storage 5: " + medicine;
            pill_5.setText(pillName_5);
        } else if (storage == 6) {
            pillName_6 = "  Storage 6: " + medicine;
            pill_6.setText(pillName_6);
        }
    }

    void clearStorageContainer()
    {
        Handler handler = new Handler();
        handler.post(() -> {
            String[] field = new String[2];
            field[0] = "id";
            field[1] = "container";
            String[] data = new String[2];
            data[0] = Integer.toString(userid);
            data[1] = Integer.toString(pill_num);
            PutData putData = new PutData("https://orbital-cygnus.herokuapp.com/clearContainer.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String response;
                    if (result.equals("Cleared Successfully")) {
                        response = "Storage " + data[1] + " successfully cleared";
                        pillEditName.setText("NIL");
                        updatePillStorageName(pill_num);
                        toggleView(false);
                    } else {
                        response = "Failed to clear storage " + data[1];
                    }
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
