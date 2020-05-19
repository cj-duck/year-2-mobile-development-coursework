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

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Spinner dropdown = findViewById(R.id.dropDownData);
        String[] items = new String[]{"bit", "Byte", "KB", "MB", "GB", "TB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btnGoData);
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
                Spinner spinner = findViewById(R.id.dropDownData);
                String selected = spinner.getSelectedItem().toString();

                // Get the users integer value
                EditText txt = findViewById(R.id.dataInput);
                String stringValue = txt.getText().toString();
                double value = Double.parseDouble(stringValue);

                // Convert the input to gb
                if (selected.equals("bit")) {
                    value = value / 8E+9;
                }
                else if (selected.equals("Byte")) {
                    value = value / 1E+9;
                }
                else if (selected.equals("KB")) {
                    value = value / 1E+6;
                }
                else if (selected.equals("MB")) {
                    value = value / 1000;
                }
                else if (selected.equals("GB")) {
                    value = value / 1;
                }
                else if (selected.equals("TB")) {
                    value = value / 0.001;
                }

                // Convert the gb value to every other unit
                double bit = value * 8E+9;
                double bytes = value * 1E+9;
                double kb = value * 1E+6;
                double mb = value * 1000;
                double gb = value;
                double tb = value * 0.001;

                // Round values
                BigDecimal bigbit = new BigDecimal(bit).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                bit = bigbit.doubleValue();
                BigDecimal bigbytes = new BigDecimal(bytes).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                bytes = bigbytes.doubleValue();
                BigDecimal bigkb = new BigDecimal(kb).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                kb = bigkb.doubleValue();
                BigDecimal bigmb = new BigDecimal(mb).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                mb = bigmb.doubleValue();
                BigDecimal biggb = new BigDecimal(gb).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                gb = biggb.doubleValue();
                BigDecimal bigtb = new BigDecimal(tb).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
                tb = bigtb.doubleValue();

                // Form strings to populate the array that will be displayed by the list
                String stringBit = Double.toString(bit) + " bits";
                String stringByte = Double.toString(bytes) + " bytes";
                String stringKB = Double.toString(kb) + " KB";
                String stringMB = Double.toString(mb) + " MB";
                String stringGB = Double.toString(gb) + " GB";
                String stringTB = Double.toString(tb) + " TB";

                // Populate array of length conversions
                String[] data = {stringBit, stringByte, stringKB, stringMB, stringGB, stringTB};

                // Show the values using the ListView
                ArrayAdapter adapter = new ArrayAdapter<>(DataActivity.this, R.layout.layout_textview, data);
                final ListView listView = findViewById(R.id.dataList);
                listView.setAdapter(adapter);
            }
        });
    }
}
