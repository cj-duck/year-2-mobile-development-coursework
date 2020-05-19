package uk.ac.napier.coursework10;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner dropdown = findViewById(R.id.dropdownDp);
        String[] dpItems = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dpItems);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(3);

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
                dropdown.setSelection(Integer.parseInt(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btn = findViewById(R.id.btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the users decimal place selection
                Spinner spinner = findViewById(R.id.dropdownDp);
                String dpSelected = spinner.getSelectedItem().toString();

                String filename = "decimalPlace";
                FileOutputStream outStream;

                try {
                    outStream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    outStream.write(dpSelected.getBytes());
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
