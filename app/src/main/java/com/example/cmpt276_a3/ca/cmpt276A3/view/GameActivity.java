package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.cmpt276_a3.ca.cmpt276A3.model.Mine;
import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.cmpt276_a3.R;

public class GameActivity extends AppCompatActivity {
    MineManager manager = MineManager.getInstance();
    int num_scans = 0;
    int num_found = 0;

    Button buttons[][] = new Button[manager.getRows()][manager.getColumns()];

    private static final String EXTRA_MESSAGE = "Extra";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateButtons();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableMines);
        for(int i=0; i<manager.getRows(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int j=0; j<manager.getColumns(); j++) {
                final int finalCol = j;
                final int finalRow = i;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mine temp = manager.getMines(finalRow, finalCol);
                        if(temp.isPresent()) {
                            if (temp.isRevealed()) {
                                num_scans++;
                                gridButtonClicked(finalRow, finalCol);
                            } else {
                                setRevealed(finalRow, finalCol);
                                num_found++;
                            }
                        }
                        else {
                            num_scans++;
                            displayInfo(finalRow, finalCol);
                        }
                    }
                });
                tableRow.addView(button);
                buttons[i][j] = button;
            }
        }
    }

    /**
     * PRE: Mine is already revealed
     * Display mine
     * Display count of unrevealed mines in same row and column
     */
    private void gridButtonClicked(int x, int y) {
        Button button = buttons[x][y];
        //Make text not clip on small buttons
        button.setPadding(0,0,0,0);

        // Lock button sizes
        lockButtonSizes();

        // Scale background to button
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_history);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource =  getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        // Set the text
        button.setText("" + initScan(x, y));

    }

    private void lockButtonSizes() {
        for(int i = 0; i < manager.getRows(); i++){
            for(int j = 0; j < manager.getColumns(); j++) {
                Button button = buttons[i][j];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getWidth();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    /**
     * Set the mine as revealed
     * Display the mine but do not display any text
     */
    private void setRevealed(int x, int y) {
        manager.getMines(x, y).reveal();
        Button button = buttons[x][y];
        //Make text not clip on small buttons
        button.setPadding(0,0,0,0);

        // Lock button sizes
        lockButtonSizes();

        // Scale background to button
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_history);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource =  getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        // Set the text
        button.setText("");
    }

    /**
     * PRE: No mine present at selected index
     * Set index as revealed
     * Display count of unrevealed mines in same row and column
     */
    private void displayInfo(int x, int y) {
        manager.getMines(x, y).reveal();

    }

    private int initScan(int x, int y) {
        int scans = 0;
        // TODO: Implement function

        return scans;
    }
}
