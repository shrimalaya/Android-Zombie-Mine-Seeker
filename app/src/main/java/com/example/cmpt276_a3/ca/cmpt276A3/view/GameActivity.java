package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.cmpt276_a3.ca.cmpt276A3.model.Mine;
import com.example.cmpt276_a3.ca.cmpt276A3.model.MineManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cmpt276_a3.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    MineManager manager = MineManager.getInstance();
    int num_scans = 0;
    int num_found = 0;

    Button buttons[][] = new Button[manager.getRows()][manager.getColumns()];
    Animation rotateAnimation;
    Animation animation;

    Timer timer;

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
        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);

        manager = MineManager.getInstance();
        populateButtons();
        TextView textView = findViewById(R.id.txtMaxMines);
        textView.setText("" + manager.getCount());
        updateUI();

        Button goBack = findViewById(R.id.btnGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    private void populateButtons() {
        manager = MineManager.getInstance();
        manager.populateMines();
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
                                manager.getMines(finalRow, finalCol).showText();
                                updateUI();
                            } else {
                                num_found++;
                                setMineRevealed(finalRow, finalCol);
                                updateUI();
                            }
                        }
                        else {
                            num_scans++;
                            manager.getMines(finalRow, finalCol).reveal();
                            manager.getMines(finalRow, finalCol).showText();
                            updateUI();
                        }
                    }
                });

                tableRow.addView(button);
                buttons[i][j] = button;
            }
        }
    }

    private void lockButtonSizes() {
        manager = MineManager.getInstance();
        for(int i = 0; i < manager.getRows(); i++){
            for(int j = 0; j < manager.getColumns(); j++) {
                Button button = buttons[i][j];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    /**
     * Set the mine as revealed
     * Display the mine but do not display any text
     */
    private void setMineRevealed(int x, int y) {
        manager = MineManager.getInstance();
        manager.getMines(x, y).reveal();
        Button button = buttons[x][y];
        //Make text not clip on small buttons
        button.setPadding(0,0,0,0);

        // Lock button sizes
        lockButtonSizes();

        // Scale background to button
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mine_icon);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource =  getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if(num_found < manager.getCount()) {
            button.startAnimation(rotateAnimation);
        }

        // Set the text
        button.setText("");
    }

    /**
     * Update number of scans and mines found
     * Update the number of unrevealed mines in the same column and row
     * The current mine should be already revealed for the helper func to work
     */
    private void updateUI() {
        manager = MineManager.getInstance();
        TextView found = findViewById(R.id.txtFoundMines);
        TextView totalScans = findViewById(R.id.txtScans);

        found.setText("" + num_found);
        totalScans.setText("" + num_scans);
        for (int i = 0; i < manager.getRows(); i++) {
            for (int j = 0; j < manager.getColumns(); j++) {
                updateRowsAndCols(i, j);
            }
        }
    }

    private void updateRowsAndCols(int x, int y) {
        manager = MineManager.getInstance();
        animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        int count = 0;
        for (int i = 0; i < manager.getColumns(); i++) {
            Mine temp = manager.getMines(x, i);
            if (temp.isPresent() && !(temp.isRevealed())) {
                count++;
            }
        }

        for (int j = 0; j < manager.getRows(); j++) {
            Mine temp = manager.getMines(j, y);
            if (temp.isPresent() && !(temp.isRevealed())) {
                count++;
            }
        }

        if(num_found >= manager.getCount()) {
            if (manager.getMines(x, y).isRevealed() && manager.getMines(x, y).showsText()) {
                Button button = buttons[x][y];
                button.setText("" + count);
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gameOver();
                }
            }, 500);
        }
        else {
            if (manager.getMines(x, y).isRevealed() && manager.getMines(x, y).showsText()) {
                Button button = buttons[x][y];
                button.setText("" + count);
                button.setTextSize(18);
                if(manager.getMines(x, y).isPresent())
                {
                    button.setTextColor(Color.parseColor("#ffffff"));
                }
                button.startAnimation(animation);
            }
        }
    }

    private void gameOver() {
        FragmentManager fragManager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(fragManager, "MessageDialog");
    }
}
