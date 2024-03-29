package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cmpt276_a3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * First activity to be seen on game
 * Lasts for maximum of 8.5 seconds (including all animation)
 * Can be skipped using the on-screen FAB
 * Displays Author information and welcome message
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, WelcomeActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    Animation rotateAnimation;
    Animation slideInLeft;
    Animation slideInRight;
    ImageView image;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);

        image = findViewById(R.id.imgWelcome);
        image.setImageResource(R.drawable.shri);

        animate();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hideUIelements();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 8500);   // Delay of 8.5 seconds

    }

    /**
     * Learned from: https://www.youtube.com/watch?v=goVoYf2qie0
     */
    private void animate() {
        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_welcome);
        slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right_welcome);

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        image.startAnimation(rotateAnimation);

        TextView text1 = findViewById(R.id.textWelcome);
        text1.startAnimation(slideInLeft);

        TextView text2 = findViewById(R.id.txtAuthor);
        text2.startAnimation(slideInRight);
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
}
