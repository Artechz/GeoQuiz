package edu.url.salle.arnau.sf.pp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogLifecycleCallbacks";
    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CHEATED = 0;

    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonCheat;
    private ImageButton imgbuttonNext;
    private ImageButton imgbuttonPrev;
    private TextView txtviewQuestion;
    private TextView txtviewPlayerTurn;

    private TextView txtviewPlayerName;
    private TextView txtviewCheated;
    private TextView txtviewInfoQuestionNumber;
    private TextView txtviewInfoQuestionAnswered;


    private Player[] players = new Player[2];
    private int nPlayersIndex = 0;
    private boolean bMultiplayer;

    private final ArrayList<Question> rQuestions = new ArrayList<>();
    private final ArrayList<Question> rQuestionsMP = new ArrayList<>();
    private int nQuestionsIndex = 0;
    private int nAnsweredQuestions = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInsanceState) {
        super.onSaveInstanceState(savedInsanceState);
        Log.i(TAG, "onSaveInstanceState() called");
        savedInsanceState.putInt(KEY_INDEX, nQuestionsIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            nQuestionsIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        buttonTrue = findViewById(R.id.true_button);
        buttonFalse = findViewById(R.id.false_button);
        buttonCheat = findViewById(R.id.cheat_button);
        imgbuttonNext = findViewById(R.id.next_button);
        imgbuttonPrev = findViewById(R.id.previous_button);
        txtviewQuestion = findViewById(R.id.question_text);
        txtviewPlayerTurn = findViewById(R.id.player_turn);

        txtviewPlayerName = findViewById(R.id.info_player_name);
        txtviewCheated = findViewById(R.id.info_has_cheated);
        txtviewInfoQuestionNumber = findViewById(R.id.info_question_num);
        txtviewInfoQuestionAnswered = findViewById(R.id.info_question_answered);

        players[0] = new Player(RegisterActivity.getPlayerOneName(getIntent()));
        bMultiplayer = RegisterActivity.getMultiplayer(getIntent());
        if(bMultiplayer) {
            players[1] = new Player(RegisterActivity.getPlayerTwoName(getIntent()));
            txtviewPlayerTurn.setVisibility(View.VISIBLE);
            txtviewPlayerTurn.setText(getResources().getString(R.string.info_player_turn, players[0].getName()));
        }

        txtviewPlayerName.setText(players[nPlayersIndex].getName());

        String[] as = getResources().getStringArray(R.array.answers);
        //instancing Question objects from q&a string arrays in Resources.
        int i = 0;
        for (String s : getResources().getStringArray(R.array.questions)) {
            rQuestions.add(new Question(s, as[i]));
            if(bMultiplayer) rQuestionsMP.add(new Question(s, as[i]));
            i++;
        }

        txtviewQuestion.setText(rQuestions.get(nQuestionsIndex).getText());
        txtviewCheated.setText(getResources().getString(R.string.info_has_cheated, players[nPlayersIndex].isCheater()));
        updateInfo();

        buttonTrue.setOnClickListener(v -> {
            userAnswer("TRUE");
        });

        buttonFalse.setOnClickListener(v -> {
            userAnswer("FALSE");
        });

        buttonCheat.setOnClickListener(v -> {
            startActivityForResult(CheatActivity.newIntent(MainActivity.this, rQuestions.get(nQuestionsIndex).getAnswer(), rQuestions.get(nQuestionsIndex).getText()), REQUEST_CHEATED);
        });

        imgbuttonNext.setOnClickListener(v -> {
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        imgbuttonPrev.setOnClickListener(v -> previousQuestion());

        txtviewQuestion.setOnClickListener(v -> nextQuestion());

    }

    private boolean nextQuestion() {
        if (nQuestionsIndex >= rQuestions.size()-1) return false;
        else {
            nQuestionsIndex++;
            updateInfo();
            return true;
        }
    }

    private boolean previousQuestion() {
        if (nQuestionsIndex <= 0) return false;
        else {
            nQuestionsIndex--;
            updateInfo();
            return true;
        }
    }

    private void updateInfo () {
        if (bMultiplayer) {
            txtviewPlayerTurn.setText(getResources().getString(R.string.info_player_turn, players[nPlayersIndex].getName()));
            txtviewPlayerName.setText(players[nPlayersIndex].getName());
        }
        txtviewQuestion.setText(rQuestions.get(nQuestionsIndex).getText());
        txtviewInfoQuestionNumber.setText(getResources().getString(R.string.info_question_num, nQuestionsIndex+1, rQuestions.size()));
        txtviewInfoQuestionAnswered.setText(getResources().getString(R.string.info_questions_answered, nAnsweredQuestions, rQuestions.size(), (int)(((float)nAnsweredQuestions/(float)rQuestions.size())*100)));
    }

    private void userAnswer(String choice) {
        if (rQuestions.get(nQuestionsIndex).beenAnswered()) {
            Toast.makeText(MainActivity.this, R.string.question_answered, Toast.LENGTH_SHORT).show();
        }
        else {
            if (rQuestions.get(nQuestionsIndex).isCorrect(choice)) {
                Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
                players[nPlayersIndex].addPoint();
            } else
                Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
            nAnsweredQuestions++;
            if (nAnsweredQuestions == rQuestions.size()) {
                //if multiplayer nextPlayer
                if (bMultiplayer && nPlayersIndex == 0) {
                    //player 2 plays
                    nPlayersIndex++;
                    nQuestionsIndex = 0;
                    nAnsweredQuestions = 0;
                    for(Question q : rQuestions) {
                        q.resetAnswered();
                    }
                    updateInfo();
                //else endGame();
                } else {
                    startActivity(ResultsActivity.newIntent(MainActivity.this, players[0], players[1], bMultiplayer)); //intent w needed data for results; start results - leaderboard
                }
            } else
                if (!nextQuestion()) {
                    Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
                    updateInfo();
                }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode  != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CHEATED) {
            if (intent == null) return;
            players[nPlayersIndex].justCheated(CheatActivity.wasAnswerShown(intent));
            txtviewCheated.setText(getResources().getString(R.string.info_has_cheated, players[nPlayersIndex].isCheater()));
        }
    }
}