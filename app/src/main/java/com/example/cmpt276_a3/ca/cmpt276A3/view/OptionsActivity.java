package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
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
        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);

        manager = MineManager.getInstance();

        int savedCount = getNumMinesSelected(this);
        manager.setCount(savedCount);

        int savedRows = getNumRowsSelected(this);
        int savedCols = getNumColsSelected(this);
        manager.setColumns(savedCols);
        manager.setRows(savedRows);

        manager.populateMines();

        createRadioNumMines();
        createRadioSizes();
        checkTotalReset();
        hideUIelements();
    }


    /**
     * Citation: Learned from developer.android.com/reference/android/view
     */
    private void hideUIelements() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Re-hide the status bar whenever it reappears
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                View decorView = getWindow().getDecorView();
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });
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
                    saveCount(mineOption);

                    manager.populateMines();
                }
            });

            // Add to radio group
            numMines.addView(button);

            // Select default button:
            if(mineOption == getNumMinesSelected(this)) {
                button.setChecked(true);
            }
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
                    saveCols(colOption);
                    saveRows(rowOption);

                    manager.populateMines();
                }
            });

            // Add to radio group
            sizes.addView(button);

            // Select default button:
            if(rowOption == getNumRowsSelected(this)) {
                button.setChecked(true);
            }
        }
    }

    private void saveTotal(int total) {
        SharedPreferences pref = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Total times played", total);
        editor.apply();
    }

    private void saveCount(int mineOption) {
        SharedPreferences pref = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Num of mines selected", mineOption);
        editor.apply();
    }

    private void saveRows(int rowOption) {
        SharedPreferences pref = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Num of rows selected", rowOption);
        editor.apply();
    }

    private void saveCols(int colOption) {
        SharedPreferences pref = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Num of columns selected", colOption);
        editor.apply();
    }

    static public int getNumMinesSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);

        int defaultValue = context.getResources().getInteger(R.integer.default_num_mines);
        return prefs.getInt("Num of mines selected", defaultValue);
    }

    static public int getNumRowsSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);

        int defaultValue = context.getResources().getInteger(R.integer.default_num_rows);
        return prefs.getInt("Num of rows selected", defaultValue);
    }

    static public int getNumColsSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);

        int defaultValue = context.getResources().getInteger(R.integer.default_num_cols);
        return prefs.getInt("Num of columns selected", defaultValue);
    }

    private void checkTotalReset() {
        Button button = findViewById(R.id.btnReset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "Reset Total", Toast.LENGTH_SHORT).show();
                saveTotal(0);
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
