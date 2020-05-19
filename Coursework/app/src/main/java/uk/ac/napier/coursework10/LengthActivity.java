package uk.ac.napier.coursework10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class LengthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        Spinner dropdown = findViewById(R.id.dropDownLength);
        String[] items = new String[]{"mm", "cm", "m", "inch", "ft", "yd", "mile", "km"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btnGoLength);
        btn1.setOnClickListener(new OnClickListener(){
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
                Spinner spinner = findViewById(R.id.dropDownLength);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.lengthInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to meters
                if (selected.equals("mm")) {
                    value = value / 1000;
                }
                else if (selected.equals("cm")) {
                    value = value / 100;
                }
                else if (selected.equals("m")) {
                    value = value / 1;
                }
                else if (selected.equals("inch")) {
                    value = value / 39.3701;
                }
                else if (selected.equals("ft")) {
                    value = value / 3.28084;
                }
                else if (selected.equals("yd")) {
                    value = value / 1.09361;
                }
                else if (selected.equals("mile")) {
                    value = value / 0.000621371;
                }
                else if (selected.equals("km")) {
                    value = value / 0.001;
                }

                // Convert the meter value to every other unit
                double mm = value * 1000;
                double cm = value * 100;
                double m = value * 1;
                double inch = value * 39.3701;
                double ft = value * 3.28084;
                double yd = value * 1.09361;
                double mile = value * 0.000621371;
                double km = value * 0.001;

                // Round values
                BigDecimal bigMm = new BigDecimal(mm).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                mm = bigMm.doubleValue();
                BigDecimal bigCm = new BigDecimal(cm).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                cm = bigCm.doubleValue();
                BigDecimal bigM = new BigDecimal(m).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                m = bigM.doubleValue();
                BigDecimal bigInch = new BigDecimal(inch).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                inch = bigInch.doubleValue();
                BigDecimal bigFt = new BigDecimal(ft).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                ft = bigFt.doubleValue();
                BigDecimal bigYd = new BigDecimal(yd).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                yd = bigYd.doubleValue();
                BigDecimal bigMile = new BigDecimal(mile).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                mile = bigMile.doubleValue();
                BigDecimal bigKm = new BigDecimal(km).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                km = bigKm.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringMm = Double.toString(mm) + " mm";
                String stringCm = Double.toString(cm) + " cm";
                String stringM = Double.toString(m) + " m";
                String stringInch = Double.toString(inch) + " inch";
                String stringFt = Double.toString(ft) + " ft";
                String stringYd = Double.toString(yd) + " yd";
                String stringMile = Double.toString(mile) + " miles";
                String stringKm = Double.toString(km) + " km";

                // Populate array of length conversions
                String[] lengths = {stringMm, stringCm, stringM, stringInch, stringFt, stringYd, stringMile, stringKm};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(LengthActivity.this, R.layout.layout_textview, lengths);
                final ListView listView = findViewById(R.id.menu_list);
                listView.setAdapter(adapter);
            }
        });
    }
}
