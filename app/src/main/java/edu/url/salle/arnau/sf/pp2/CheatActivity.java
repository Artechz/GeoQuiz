package edu.url.salle.arnau.sf.pp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity responsible for showing and managing the cheating in the game.
 * Including UI and data validation.
 */
public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "ANSWER";
    private static final String EXTRA_QUESTION = "QUESTION";
    private static final String EXTRA_HASCHEATED = "CHEATED";

    private Button buttonCheat;
    private Button buttonBack;
    private TextView txtviewQuestion;
    private TextView txtviewAnswer;
    private String sAnswer;

    /**
     * Helper method to know if the user cheated with the data sent back from CheatAct
     * to QuizAct.
     * @param intent data sent from QuizA (previously sent from CheatA).
     * @return TRUE if the user cheated, FALSE if they didn't.
     */
    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_HASCHEATED, false);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        buttonCheat = findViewById(R.id.cheat_button);
        buttonBack = findViewById(R.id.back_button);
        txtviewAnswer = findViewById(R.id.answer_text);
        txtviewQuestion = findViewById(R.id.question_text);

        sAnswer = getIntent().getStringExtra(EXTRA_ANSWER);
        txtviewQuestion.setText(getIntent().getStringExtra(EXTRA_QUESTION));

        buttonCheat.setOnClickListener(v -> {
            txtviewAnswer.setText(sAnswer);
            setResult(RESULT_OK, new Intent().putExtra(EXTRA_HASCHEATED, true));
        });

        buttonBack.setOnClickListener(v -> {
            finish();
        });


    }

    /**
     * Helper method to create the intent for this activity in a more comfy way. Standardizes incoming
     * intents to the activity by setting its params itself.
     * @param packageContext Context it's called from (MainActivity.this).
     * @param answer data to put into Intent (String).
     * @param question data to put into Intent (String).
     * @return tailor-made Intent for the CheatActivity.
     */
    public static Intent newIntent(Context packageContext, String answer, String question) {
        return new Intent(packageContext, CheatActivity.class).putExtra(EXTRA_ANSWER, answer).putExtra(EXTRA_QUESTION, question);
    }
}