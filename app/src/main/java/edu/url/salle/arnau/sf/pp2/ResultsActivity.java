package edu.url.salle.arnau.sf.pp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SortedList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultsActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYER1_NAME = "P1_NAME";
    private static final String EXTRA_PLAYER1_SCORE = "P1_SCORE";
    private static final String EXTRA_PLAYER1_CHEATER = "P1_CHEATER";
    private static final String EXTRA_PLAYER2_NAME = "P2_NAME";
    private static final String EXTRA_PLAYER2_SCORE = "P2_SCORE";
    private static final String EXTRA_PLAYER2_CHEATER = "P2_CHEATER";
    private static final String EXTRA_IS_MULTIPLAYER = "IS_MP";
    private static final String SP_SAVED_DATA = "PLAYER_DATA";

    private TextView txtviewPlayerOne;
    private TextView txtviewPlayerTwo;
    private TextView[] rTxtViewLeaderboard = new TextView[10];
    private int pointerID = 0;

    private Player playerOne;
    private Player playerTwo;
    private boolean bMultiplayer;
    private ArrayList<Player> players = new ArrayList<>();

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        bMultiplayer = getIntent().getBooleanExtra(EXTRA_IS_MULTIPLAYER, false);
        playerOne = new Player(getIntent().getStringExtra(EXTRA_PLAYER1_NAME), getIntent().getIntExtra(EXTRA_PLAYER1_SCORE, 0), getIntent().getBooleanExtra(EXTRA_PLAYER1_CHEATER, false));
        players.add(playerOne);
        txtviewPlayerOne = findViewById(R.id.result_playerOne);
        txtviewPlayerOne.setText(getResources().getString(R.string.result_player, players.get(0).getName(), players.get(0).getScore()));
        if (bMultiplayer) {
            playerTwo = new Player(getIntent().getStringExtra(EXTRA_PLAYER2_NAME), getIntent().getIntExtra(EXTRA_PLAYER2_SCORE, 0), getIntent().getBooleanExtra(EXTRA_PLAYER2_CHEATER, false));
            players.add(playerTwo);
            txtviewPlayerTwo = findViewById(R.id.result_playerTwo);
            txtviewPlayerTwo.setText(getResources().getString(R.string.result_player, players.get(1).getName(), players.get(1).getScore()));
            txtviewPlayerTwo.setVisibility(View.VISIBLE);
        }

        sharedpreferences = getSharedPreferences(SP_SAVED_DATA, Context.MODE_PRIVATE);
        while(sharedpreferences.contains(Integer.toString(pointerID))) {
            //reading players from SharedPreferences
            players.add(
                    new Player(sharedpreferences.getString(Integer.toString(pointerID++), "a"),
                            sharedpreferences.getInt(Integer.toString(pointerID++), 0),
                            sharedpreferences.getBoolean(Integer.toString(pointerID++), false)));
        }
        //TODO ingest data from intent; connect to UI; save and load data from SharedPreferences; sort Players with the new ones and display Leadrboard;
        //after ingesting users:
        players.sort(new SortByScore());

        rTxtViewLeaderboard[0] = findViewById(R.id.leaderboard_1);
        rTxtViewLeaderboard[1] = findViewById(R.id.leaderboard_2);
        rTxtViewLeaderboard[2] = findViewById(R.id.leaderboard_3);
        rTxtViewLeaderboard[3] = findViewById(R.id.leaderboard_4);
        rTxtViewLeaderboard[4] = findViewById(R.id.leaderboard_5);
        rTxtViewLeaderboard[5] = findViewById(R.id.leaderboard_6);
        rTxtViewLeaderboard[6] = findViewById(R.id.leaderboard_7);
        rTxtViewLeaderboard[7] = findViewById(R.id.leaderboard_8);
        rTxtViewLeaderboard[8] = findViewById(R.id.leaderboard_9);
        rTxtViewLeaderboard[9] = findViewById(R.id.leaderboard_10);

        int i = 0;
        for (Player p : players) {
            rTxtViewLeaderboard[i].setText(getResources().getString(R.string.result_player, p.getName(), p.getScore()));
            if (i == 9) break;
            i++;
        }



    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor editSP = sharedpreferences.edit();
        editSP.putString(Integer.toString(pointerID++), playerOne.getName()).putInt(Integer.toString(pointerID++), playerOne.getScore()).putBoolean(Integer.toString(pointerID++), playerOne.isCheater());
        if (bMultiplayer) editSP.putString(Integer.toString(pointerID++), playerTwo.getName()).putInt(Integer.toString(pointerID++), playerTwo.getScore()).putBoolean(Integer.toString(pointerID++), playerTwo.isCheater());
        editSP.commit();
    }

    public static Intent newIntent(Context packageContext, @NonNull Player playerOne, @NonNull Player playerTwo, boolean isMultiplayer) {
        if (isMultiplayer) return new Intent(packageContext, ResultsActivity.class).putExtra(EXTRA_IS_MULTIPLAYER, isMultiplayer)
                .putExtra(EXTRA_PLAYER1_NAME, playerOne.getName()).putExtra(EXTRA_PLAYER1_SCORE, playerOne.getScore()).putExtra(EXTRA_PLAYER1_CHEATER, playerOne.isCheater())
                .putExtra(EXTRA_PLAYER2_NAME, playerTwo.getName()).putExtra(EXTRA_PLAYER2_SCORE, playerTwo.getScore()).putExtra(EXTRA_PLAYER2_CHEATER, playerTwo.isCheater());
        return new Intent(packageContext, ResultsActivity.class).putExtra(EXTRA_IS_MULTIPLAYER, isMultiplayer)
                .putExtra(EXTRA_PLAYER1_NAME, playerOne.getName()).putExtra(EXTRA_PLAYER1_SCORE, playerOne.getScore()).putExtra(EXTRA_PLAYER1_CHEATER, playerOne.isCheater());
    }
}

class SortByScore implements Comparator<Player> {
    public int compare (Player a, Player b) {
        return b.getScore() - a.getScore();
    }
}