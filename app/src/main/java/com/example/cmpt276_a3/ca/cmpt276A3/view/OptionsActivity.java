package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276_a3.MainActivity;
import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmpt276_a3.R;

public class OptionsActivity extends AppCompatActivity {

    private MineManager manager;

    private static final String EXTRA_MESSAGE = "Extra";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, OptionsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createRadioNumMines();
        createRadioSizes();
        manager = MineManager.getInstance();
        manager.populateMines();

        checkTotalReset();
    }

    private void createRadioNumMines() {
        manager = MineManager.getInstance();
        RadioGroup numMines = findViewById(R.id.radioNumMines);

        int[] mineOptions = getResources().getIntArray(R.array.num_mines);

        // Create the buttons
        for(int i=0; i < mineOptions.length; i++) {
            final int mineOption = mineOptions[i];

            RadioButton button = new RadioButton(this);
            button.setText(mineOption + " mines");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.setCount(mineOption);
                    manager.populateMines();
                }
            });

            // Add to radio group
            numMines.addView(button);
        }
    }

    private void createRadioSizes() {
        manager = MineManager.getInstance();
        RadioGroup sizes = findViewById(R.id.radioSizes);

        int[] rowOptions = getResources().getIntArray(R.array.board_rows);
        int[] colOptions = getResources().getIntArray(R.array.board_cols);

        // Create the buttons
        for(int i=0; i < rowOptions.length; i++) {
            final int rowOption = rowOptions[i];
            final int colOption = colOptions[i];


            final RadioButton button = new RadioButton(this);
            button.setText(rowOption + " rows by " + colOption + " columns");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.setRows(rowOption);
                    manager.setColumns(colOption);
                    manager.populateMines();
                }
            });

            // Add to radio group
            sizes.addView(button);
        }
    }

    private void checkTotalReset() {

        Button button = findViewById(R.id.btnReset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "Reset Total", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case(R.id.options_save):
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
