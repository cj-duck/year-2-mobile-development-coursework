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

public class WeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        Spinner dropdown = findViewById(R.id.dropDownWeight);
        String[] items = new String[]{"g", "kg", "lb", "oz", "tonne", "ton (UK)", "ton (US)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btnGoWeight);
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
                Spinner spinner = findViewById(R.id.dropDownWeight);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.weightInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to kg
                if (selected.equals("g")) {
                    value = value * 0.001;
                }
                else if (selected.equals("kg")) {
                    value = value / 1;
                }
                else if (selected.equals("lb")) {
                    value = value / 2.20462;
                }
                else if (selected.equals("oz")) {
                    value = value / 35.274;
                }
                else if (selected.equals("tonne")) {
                    value = value / 0.001;
                }
                else if (selected.equals("ton (UK)")) {
                    value = value / 0.000984207;
                }
                else if (selected.equals("ton (US)")) {
                    value = value / 0.00110231;
                }

                // Convert the meter value to every other unit
                double g = value / 0.001;
                double kg = value;
                double lb = value * 2.20462;
                double oz = value * 35.274;
                double tonne = value * 0.001;
                double tonUK = value * 0.000984207;
                double tonUS = value * 0.00110231;

                // Round values
                BigDecimal bigg = new BigDecimal(g).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                g = bigg.doubleValue();
                BigDecimal bigkg = new BigDecimal(kg).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                kg = bigkg.doubleValue();
                BigDecimal biglb = new BigDecimal(lb).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                lb = biglb.doubleValue();
                BigDecimal bigoz = new BigDecimal(oz).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                oz = bigoz.doubleValue();
                BigDecimal bigtonne = new BigDecimal(tonne).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tonne = bigtonne.doubleValue();
                BigDecimal bigtonUK = new BigDecimal(tonUK).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tonUK = bigtonUK.doubleValue();
                BigDecimal bigtonUS = new BigDecimal(tonUS).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tonUS = bigtonUS.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringG = Double.toString(g) + " g";
                String stringKg = Double.toString(kg) + " kg";
                String stringLb = Double.toString(lb) + " lb";
                String stringOz = Double.toString(oz) + " oz";
                String stringTonne = Double.toString(tonne) + " tonne";
                String stringTonUK = Double.toString(tonUK) + " ton (UK)";
                String stringTonUS = Double.toString(tonUS) + " ton (US)";

                // Populate array of length conversions
                String[] lengths = {stringG, stringKg, stringLb, stringOz, stringTonne, stringTonUK, stringTonUS};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(WeightActivity.this, R.layout.layout_textview, lengths);
                final ListView listView = findViewById(R.id.weightList);
                listView.setAdapter(adapter);
            }
        });
    }
}
