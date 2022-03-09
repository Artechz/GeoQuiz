package edu.url.salle.arnau.sf.pp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYER1_NAME = "P1_NAME";
    private static final String EXTRA_PLAYER1_SCORE = "P1_SCORE";
    private static final String EXTRA_PLAYER1_CHEATER = "P1_CHEATER";
    private static final String EXTRA_PLAYER2_NAME = "P2_NAME";
    private static final String EXTRA_PLAYER2_SCORE = "P2_SCORE";
    private static final String EXTRA_PLAYER2_CHEATER = "P2_CHEATER";
    private static final String EXTRA_IS_MULTIPLAYER = "IS_MP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //TODO ingest data from intent; connect to UI; save and load data from SharedPreferences; sort Players with the new ones and display Leadrboard;
    }

    public static Intent newIntent(Context packageContext, @NonNull Player playerOne, @NonNull Player playerTwo, boolean isMultiplayer) {
        return new Intent(packageContext, ResultsActivity.class).putExtra(EXTRA_IS_MULTIPLAYER, isMultiplayer)
                .putExtra(EXTRA_PLAYER1_NAME, playerOne.getName()).putExtra(EXTRA_PLAYER1_SCORE, playerOne.getScore()).putExtra(EXTRA_PLAYER1_CHEATER, playerOne.isCheater())
                .putExtra(EXTRA_PLAYER2_NAME, playerTwo.getName()).putExtra(EXTRA_PLAYER2_SCORE, playerTwo.getScore()).putExtra(EXTRA_PLAYER2_CHEATER, playerTwo.isCheater());
    }
}