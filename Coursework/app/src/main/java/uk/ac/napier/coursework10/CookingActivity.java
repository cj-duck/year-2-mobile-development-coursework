package uk.ac.napier.coursework10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);

        Spinner dropdown = findViewById(R.id.dropDownCooking);
        String[] items = new String[]{"ml", "tsp", "tbsp", "cup (US)", "cup (UK)", "g (flour)", "fl oz (US)", "fl oz (UK)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btnGoCooking);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Set default decimal places
                int decimalPlaces = 5;

                // Check for saved number of decimal places
                int ch;
                String filename = "decimalPlace";
                StringBuffer inString = new StringBuffer("");
                FileInputStream inStream;

                try {
                    inStream = getApplicationContext().openFileInput(filename);
                    try {
                        while ((ch = inStream.read()) != -1)
                            inString.append((char) ch);
                        String data = new String(inString);
                        decimalPlaces = Integer.parseInt(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Get the users unit selection
                Spinner spinner = findViewById(R.id.dropDownCooking);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.cookingInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to ml
                if (selected.equals("ml")) {
                    value = value / 1 ;
                }
                else if (selected.equals("tsp")) {
                    value = value / 0.168936 ;
                }
                else if (selected.equals("tbsp")) {
                    value = value / 0.0563121;
                }
                else if (selected.equals("cup (US)")) {
                    value = value / 0.00416667;
                }
                else if (selected.equals("cup (UK)")) {
                    value = value / 0.00351951;
                }
                else if (selected.equals("g (flour)")) {
                    value = value / 0.6;
                }
                else if (selected.equals("fl oz (US)")) {
                    value = value / 0.033814;
                }
                else if (selected.equals("fl oz (UK)")) {
                    value = value / 0.0351951;
                }

                // Convert the ml value to every other unit
                double ml = value * 1;
                double tsp = value * 0.168936;
                double tbsp = value * 0.0563121;
                double cupUS = value * 0.00416667;
                double cupUK = value * 0.00351951;
                double gFlour = value * 0.6;
                double flozUS = value * 0.033814;
                double flozUK = value * 0.0351951;

                // Round values
                BigDecimal bigml = new BigDecimal(ml).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                ml = bigml.doubleValue();
                BigDecimal bigtsp = new BigDecimal(tsp).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tsp = bigtsp.doubleValue();
                BigDecimal bigtbsp = new BigDecimal(tbsp).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tbsp = bigtbsp.doubleValue();
                BigDecimal bigcupUS = new BigDecimal(cupUS).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                cupUS = bigcupUS.doubleValue();
                BigDecimal bigcupUK = new BigDecimal(cupUK).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                cupUK = bigcupUK.doubleValue();
                BigDecimal biggFlour = new BigDecimal(gFlour).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                gFlour = biggFlour.doubleValue();
                BigDecimal bigflozUS = new BigDecimal(flozUS).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                flozUS = bigflozUS.doubleValue();
                BigDecimal bigflozUK = new BigDecimal(flozUK).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                flozUK = bigflozUK.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringMl = Double.toString(ml) + " ml";
                String stringTsp = Double.toString(tsp) + " tsp";
                String stringTbsp = Double.toString(tbsp) + " tbsp";
                String stringCupUS = Double.toString(cupUS) + " cups (US)";
                String stringCupUK = Double.toString(cupUK) + " cups (UK)";
                String stringGFlour = Double.toString(gFlour) + " g (Flour)";
                String stringFlozUS = Double.toString(flozUS) + " fl oz (US)";
                String stringFlozUK = Double.toString(flozUK) + " fl oz (UK)";

                // Populate array of length conversions
                String[] cooking = {stringMl, stringTsp, stringTbsp, stringCupUS, stringCupUK, stringGFlour, stringFlozUS, stringFlozUK};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(CookingActivity.this, R.layout.layout_textview, cooking);
                final ListView listView = findViewById(R.id.cookingList);
                listView.setAdapter(adapter);
            }
        });
    }
}
