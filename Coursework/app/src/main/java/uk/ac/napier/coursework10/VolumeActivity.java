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

public class VolumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        Spinner dropdown = findViewById(R.id.dropDownVolume);
        String[] items = new String[]{"ml", "cl", "L", "gal (UK)", "gal (US)", "pt (UK)", "pt (US)", "fl oz (US)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btnGoVolume);
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
                Spinner spinner = findViewById(R.id.dropDownVolume);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.volumeInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to L
                if (selected.equals("ml")) {
                    value = value / 1000;
                }
                else if (selected.equals("cl")) {
                    value = value / 100;
                }
                else if (selected.equals("L")) {
                    value = value / 1;
                }
                else if (selected.equals("gal (UK)")) {
                    value = value / 0.219969;
                }
                else if (selected.equals("gal (US)")) {
                    value = value / 0.264172;
                }
                else if (selected.equals("pt (UK)")) {
                    value = value / 1.75975;
                }
                else if (selected.equals("pt (US)")) {
                    value = value / 2.11338;
                }
                else if (selected.equals("fl oz (US)")) {
                    value = value / 33.814;
                }

                // Convert the L value to every other unit
                double ml = value * 1000;
                double cl = value * 100;
                double L = value;
                double galUK = value * 0.219969;
                double galUS = value * 0.264172;
                double ptUK = value * 1.75975;
                double ptUS = value * 2.11338;
                double oz = value * 33.814;

                // Round values
                BigDecimal bigml = new BigDecimal(ml).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                ml = bigml.doubleValue();
                BigDecimal bigcl = new BigDecimal(cl).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                cl = bigcl.doubleValue();
                BigDecimal bigL = new BigDecimal(L).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                L = bigL.doubleValue();
                BigDecimal biggalUK = new BigDecimal(galUK).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                galUK = biggalUK.doubleValue();
                BigDecimal biggalUS = new BigDecimal(galUS).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                galUS = biggalUS.doubleValue();
                BigDecimal bigptUK = new BigDecimal(ptUK).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                ptUK = bigptUK.doubleValue();
                BigDecimal bigptUS = new BigDecimal(ptUS).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                ptUS = bigptUS.doubleValue();
                BigDecimal bigoz = new BigDecimal(oz).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                oz = bigoz.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringMl = Double.toString(ml) + " ml";
                String stringCl = Double.toString(cl) + " cl";
                String stringL = Double.toString(L) + " L";
                String stringGalUK = Double.toString(galUK) + " gal (UK)";
                String stringGalUS = Double.toString(galUS) + " gal (US)";
                String stringPtUK = Double.toString(ptUK) + " pt (UK)";
                String stringPtUS = Double.toString(ptUS) + " pt (US)";
                String stringOz = Double.toString(oz) + " fl oz (US)";

                // Populate array of length conversions
                String[] volumes = {stringMl, stringCl, stringL, stringGalUK, stringGalUS, stringPtUK, stringPtUS, stringOz};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(VolumeActivity.this, R.layout.layout_textview, volumes);
                final ListView listView = findViewById(R.id.volumeList);
                listView.setAdapter(adapter);
            }
        });
    }
}
