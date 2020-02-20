package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmpt276_a3.R;

public class OptionsActivity extends AppCompatActivity {

    private MineManager manager = MineManager.getInstance();

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
        checkTotalReset();
        manager.populateMines();
    }

    private void createRadioNumMines() {
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
                }
            });

            // Add to radio group
            numMines.addView(button);
        }
    }

    private void createRadioSizes() {
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

}
