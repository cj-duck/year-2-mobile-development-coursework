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

public class AreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        Spinner dropdown = findViewById(R.id.dropDownArea);
        String[] items = new String[]{"cm²", "m²", "inch²", "ft²", "yd²", "acre", "mile²", "km²"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Button btn1 = findViewById(R.id.btnGoArea);
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
                Spinner spinner = findViewById(R.id.dropDownArea);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.areaInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to meters squared
                if (selected.equals("cm²")) {
                    value = value / 10000;
                }
                else if (selected.equals("m²")) {
                    value = value / 1;
                }
                else if (selected.equals("inch²")) {
                    value = value / 1550;
                }
                else if (selected.equals("ft²")) {
                    value = value / 10.7639;
                }
                else if (selected.equals("yd²")) {
                    value = value / 1.19599;
                }
                else if (selected.equals("acre")) {
                    value = value / 0.000247105;
                }
                else if (selected.equals("mile²")) {
                    value = value / 3.861e-7;
                }
                else if (selected.equals("km²")) {
                    value = value * 1e-6;
                }

                // Convert the meter squared value to every other unit
                double cm = value * 10000;
                double m = value;
                double inch = value * 1550;
                double ft = value * 10.7639;
                double yd = value * 1.19599;
                double acre = value * 0.000247105;
                double mile = value * 3.861e-7;
                double km = value * 1e-6;

                // Round values
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
                BigDecimal bigAcre = new BigDecimal(acre).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                acre = bigAcre.doubleValue();
                BigDecimal bigMile = new BigDecimal(mile).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                mile = bigMile.doubleValue();
                BigDecimal bigKm = new BigDecimal(km).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                km = bigKm.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringCm = Double.toString(cm) + " cm²";
                String stringM = Double.toString(m) + " m²";
                String stringInch = Double.toString(inch) + " inch²";
                String stringFt = Double.toString(ft) + " ft²";
                String stringYd = Double.toString(yd) + " yd²";
                String stringAcre = Double.toString(acre) + " acres";
                String stringMile = Double.toString(mile) + " mile²";
                String stringKm = Double.toString(km) + " km²";

                // Populate array of length conversions
                String[] areas = {stringCm, stringM, stringInch, stringFt, stringYd, stringAcre, stringMile, stringKm};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(AreaActivity.this, R.layout.layout_textview, areas);
                final ListView listView = findViewById(R.id.areaList);
                listView.setAdapter(adapter);
            }
        });
    }
}
