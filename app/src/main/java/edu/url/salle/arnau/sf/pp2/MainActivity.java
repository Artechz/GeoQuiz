package edu.url.salle.arnau.sf.pp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private ArrayList<Question> rQuestions = new ArrayList<>();
    private int nQuestionsIndex = 0;

    private boolean bHasCheated;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInsanceState) {
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

        String[] as = getResources().getStringArray(R.array.answers);
        //instancing Question objects from q&a string arrays in Resources.
        int i = 0;
        for (String s : getResources().getStringArray(R.array.questions)) {
            rQuestions.add(new Question(s, as[i++]));
        }

        txtviewQuestion.setText(rQuestions.get(nQuestionsIndex).getText());

        buttonTrue.setOnClickListener(v -> {
            if (rQuestions.get(nQuestionsIndex).isCorrect("TRUE"))
                Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        buttonFalse.setOnClickListener(v -> {
            if (rQuestions.get(nQuestionsIndex).isCorrect("FALSE"))
                Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
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
            txtviewQuestion.setText(rQuestions.get(nQuestionsIndex).getText());
            return true;
        }
    }

    private boolean previousQuestion() {
        if (nQuestionsIndex <= 0) return false;
        else {
            nQuestionsIndex--;
            txtviewQuestion.setText(rQuestions.get(nQuestionsIndex).getText());
            return true;
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode  != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CHEATED) {
            if (intent == null) return;
            bHasCheated = CheatActivity.wasAnswerShown(intent);
        }


    }
}