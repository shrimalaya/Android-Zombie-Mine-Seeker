package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

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
                        gridButtonClicked(finalRow, finalCol);
                    }
                });
                tableRow.addView(button);
                buttons[i][j] = button;
            }
        }
    }

    private void gridButtonClicked(int x, int y) {
        Button button = buttons[x][y];
        //Make text not clip on small buttons
        button.setPadding(0,0,0,0);

        // Lock button sizes
        lockButtonSizes();

        // Does not scale background to button
        //button.setBackgroundResource(R.drawable.clear_action);

        // Scale background to button
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clear_action);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource =  getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        // Set the text
        button.setText("" + y);
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

}
