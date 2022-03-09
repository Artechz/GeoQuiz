package edu.url.salle.arnau.sf.pp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText edittxtPlayer1;
    private EditText edittxtPlayer2;
    private CheckBox checkboxMultiplayer;
    private Button buttonDone;

    private static final String EXTRA_PLAYER1 = "PLAYER1";
    private static final String EXTRA_PLAYER2 = "PLAYER2";
    private static final String EXTRA_MULTIPLAYER = "MULTIPLAYER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edittxtPlayer1 = findViewById(R.id.player_1);
        edittxtPlayer2 = findViewById(R.id.player_2);
        checkboxMultiplayer = findViewById(R.id.checkbox_multiplayer);
        buttonDone = findViewById(R.id.button_play);

        checkboxMultiplayer.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked())
                edittxtPlayer2.setVisibility(View.VISIBLE);
            else
                edittxtPlayer2.setVisibility(View.INVISIBLE);
        });

        buttonDone.setOnClickListener(v -> {
            if (edittxtPlayer1.getText().toString().equals("")) return;
            if (checkboxMultiplayer.isChecked() && edittxtPlayer2.getText().toString().equals("")) return;
            startActivity(new Intent(this, MainActivity.class).putExtra(EXTRA_PLAYER1, edittxtPlayer1.getText().toString()).putExtra(EXTRA_PLAYER2, edittxtPlayer2.getText().toString()).putExtra(EXTRA_MULTIPLAYER, checkboxMultiplayer.isChecked()));//launch game (MainActivity) with Intent
            finish();
        });
    }

    public static String getPlayerOneName(@NonNull Intent intent) {
        return intent.getStringExtra(EXTRA_PLAYER1);
    }
    public static String getPlayerTwoName(@NonNull Intent intent) {
        return intent.getStringExtra(EXTRA_PLAYER2);
    }
    public static boolean getMultiplayer(@NonNull Intent intent) {
        return intent.getBooleanExtra(EXTRA_MULTIPLAYER, false);
    }
}