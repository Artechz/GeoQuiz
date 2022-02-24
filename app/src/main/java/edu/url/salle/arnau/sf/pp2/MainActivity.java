package edu.url.salle.arnau.sf.pp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.correct_feedback, Toast.LENGTH_SHORT).show();
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.wrong_feedback, Toast.LENGTH_SHORT).show();
        });
    }
}