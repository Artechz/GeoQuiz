package edu.url.salle.arnau.sf.pp2;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionText;
    private TextView mAnswerText;

    private ArrayList<Question> questions = new ArrayList<>();
    private int questionsIndex = 0;

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
        savedInsanceState.putInt(KEY_INDEX, questionsIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            questionsIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mAnswerText = findViewById(R.id.answer_text);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.previous_button);
        mQuestionText = findViewById(R.id.question_text);

        String[] qs = getResources().getStringArray(R.array.questions);
        String[] as = getResources().getStringArray(R.array.answers);

        //instancing Question objects from q&a string arrays in Resources.
        int i = 0;
        for (String s : qs) {
            questions.add(new Question(s, as[i++]));
        }

        mQuestionText.setText(questions.get(questionsIndex).getText());

        mTrueButton.setOnClickListener(v -> {
            if (questions.get(questionsIndex).isCorrect("TRUE"))
                Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        mFalseButton.setOnClickListener(v -> {
            if (questions.get(questionsIndex).isCorrect("FALSE"))
                Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        mCheatButton.setOnClickListener(v -> {
            mAnswerText.setText(questions.get(questionsIndex).getAnswer());
        });

        mNextButton.setOnClickListener(v -> {
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        mPrevButton.setOnClickListener(v -> previousQuestion());

        mQuestionText.setOnClickListener(v -> nextQuestion());

    }

    private boolean nextQuestion() {
        if (questionsIndex >= questions.size()-1) return false;
        else {
            mAnswerText.setText("");
            questionsIndex++;
            mQuestionText.setText(questions.get(questionsIndex).getText());
            return true;
        }
    }

    private boolean previousQuestion() {
        if (questionsIndex <= 0) return false;
        else {
            mAnswerText.setText("");
            questionsIndex--;
            mQuestionText.setText(questions.get(questionsIndex).getText());
            return true;
        }
    }
}