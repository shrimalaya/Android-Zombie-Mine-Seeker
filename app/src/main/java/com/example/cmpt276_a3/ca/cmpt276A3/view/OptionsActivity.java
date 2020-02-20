package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        recordPreferences();
    }

    private void createRadioNumMines() {
        RadioGroup numMines = findViewById(R.id.radioNumMines);

        int[] mineOptions = getResources().getIntArray(R.array.num_mines);

        // Create the buttons
        for(int i=0; i < mineOptions.length; i++) {
            int mineOption = mineOptions[i];

            RadioButton button = new RadioButton(this);
            button.setText(mineOption + " mines");

            // TODO: Set on-click callbacks

            // Add to radio group
            numMines.addView(button);
        }
    }

    private void createRadioSizes() {
        RadioGroup sizes = findViewById(R.id.radioSizes);

        String[] sizeOptions = getResources().getStringArray(R.array.board_sizes);

        // Create the buttons
        for(int i=0; i < sizeOptions.length; i++) {
            String sizeOption = sizeOptions[i];


            RadioButton button = new RadioButton(this);
            button.setText(sizeOption);

            // TODO: Set on-click callbacks

            // Add to radio group
            sizes.addView(button);
        }
    }

    private void recordPreferences() {
        Button radioSize;
        Button radioMines;

    }

}
