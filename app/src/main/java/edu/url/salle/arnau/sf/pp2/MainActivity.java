package edu.url.salle.arnau.sf.pp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionText;

    private ArrayList<Question> questions = new ArrayList<>();
    private int questionsIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
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

        mQuestionText.setText(questions.get(0).getText());

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

        mNextButton.setOnClickListener(v -> {
            if(!nextQuestion()) Toast.makeText(MainActivity.this, R.string.game_over, Toast.LENGTH_SHORT).show();
        });

        mPrevButton.setOnClickListener(v -> previousQuestion());

        mQuestionText.setOnClickListener(v -> nextQuestion());

    }

    private boolean nextQuestion() {
        if (questionsIndex >= questions.size()-1) return false;
        else {
            questionsIndex++;
            mQuestionText.setText(questions.get(questionsIndex).getText());
            return true;
        }
    }

    private boolean previousQuestion() {
        if (questionsIndex <= 0) return false;
        else {
            questionsIndex--;
            mQuestionText.setText(questions.get(questionsIndex).getText());
            return true;
        }
    }
}