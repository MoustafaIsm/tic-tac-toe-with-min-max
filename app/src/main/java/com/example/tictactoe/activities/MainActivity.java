package com.example.tictactoe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.R;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private String[] modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnStart = findViewById(R.id.btn_start);
        this.modes = getResources().getStringArray(R.array.mode);

        this.btnStart.setOnClickListener(v -> {
            openDifficultyModal();
        });

    }

    private void openDifficultyModal() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.choose_mode);

        RadioGroup radioGroup = setupModalView();
        builder.setView(radioGroup);

        AlertDialog dialog = builder.create();

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Start", (dialog1, which) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String mode = this.modes[selectedId];
            openGameActivity(mode);
            dialog1.dismiss();
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog1, which) -> {
            dialog1.dismiss();
        });

        dialog.show();
    }

    private RadioGroup setupModalView() {
        RadioGroup radioGroup = new RadioGroup(MainActivity.this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        radioGroup.setPadding(50, 50, 50, 50);

        // Create radio buttons
        for (int i = 0; i < modes.length; i++) {
            RadioButton radioButton = new RadioButton(MainActivity.this);
            radioButton.setText(modes[i]);
            radioButton.setId(i);

            radioGroup.addView(radioButton);

            if (i == 0) {
                radioButton.setChecked(true);
            }
        }

        return radioGroup;
    }

    private void openGameActivity(String mode) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}