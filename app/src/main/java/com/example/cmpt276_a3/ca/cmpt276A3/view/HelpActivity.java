package com.example.cmpt276_a3.ca.cmpt276A3.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cmpt276_a3.R;

/**
 * Help Activity
 * Displays author information
 * Shows all citations as hyperlinked text
 * Guides user how to play the game
 */
public class HelpActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, HelpActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);

        activateLinks();
    }

    private void activateLinks() {
        TextView about = findViewById(R.id.txtAboutAuthor);
        about.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gameLogo = findViewById(R.id.txtGameLogo);
        gameLogo.setMovementMethod(LinkMovementMethod.getInstance());

        TextView background = findViewById(R.id.txtBackground);
        background.setMovementMethod(LinkMovementMethod.getInstance());

        TextView mineIcon = findViewById(R.id.txtMineIcon);
        mineIcon.setMovementMethod(LinkMovementMethod.getInstance());

        TextView welcomeScreen = findViewById(R.id.txtWelcomeCartoon);
        welcomeScreen.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
