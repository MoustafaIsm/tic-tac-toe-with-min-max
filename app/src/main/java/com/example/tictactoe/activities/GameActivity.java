package com.example.tictactoe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tictactoe.R;
import com.example.tictactoe.models.Board;
import com.example.tictactoe.models.Cell;
import com.example.tictactoe.models.Player;

import java.text.MessageFormat;

public class GameActivity extends AppCompatActivity {

    private TextView tvTurn;
    private Button btnRestart;
    private Board board;
    private boolean gameDone;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        // Initialize controls
        tvTurn = findViewById(R.id.tv_turn);
        TextView tvMode = findViewById(R.id.tv_mode);
        btnRestart = findViewById(R.id.btn_restart);

        initializeGame(mode);

        // Manage controls
        tvMode.setText(MessageFormat.format("Mode: {0}", mode));

    }

    private void setGameDone(boolean gameDone) {
        this.gameDone = gameDone;
        this.btnRestart.setEnabled(gameDone);
    }

    private void initializeGame(String mode) {

        setGameDone(false);
        Cell[][] cells = getCells();
        Player player1 = new Player("Player 1", ResourcesCompat.getDrawable(getResources(), R.drawable.x, null));
        Player player2 = new Player("Player 2", ResourcesCompat.getDrawable(getResources(), R.drawable.o, null));

        player2.setComputer(!mode.equals(getResources().getString(R.string.pvp)));

        this.board = new Board(player1, player2, cells);

        Player currentPlayer = this.board.getCurrentPlayer();
        tvTurn.setText(MessageFormat.format("{0}'s turn", currentPlayer.getName()));
    }

    public void onImageClick(View view) {

        if (!(view instanceof ImageView)) {
            return;
        }

        if (gameDone) {
            return;
        }

        ImageView imageView = (ImageView) view;

        // Get the content description of the clicked ImageView
        String contentDescription = imageView.getContentDescription().toString();
        int row = getCoordinate(contentDescription, 0);
        int column = getCoordinate(contentDescription, 1);

        Player currentPlayer = board.getCurrentPlayer();

        board.play(board.getCell(row, column));

        if (board.isBoardFull()) {
            tvTurn.setText(R.string.game_over);
            setGameDone(true);
            return;
        }

        if (board.isWinner(currentPlayer)) {
            tvTurn.setText(MessageFormat.format("{0} wins!", currentPlayer.getName()));
            setGameDone(true);
            return;
        }

        currentPlayer = board.getCurrentPlayer();
        tvTurn.setText(MessageFormat.format("{0}'s turn", currentPlayer.getName()));

        if (currentPlayer.isComputer() && !gameDone) {

            Cell bestMove = board.getNextMove(mode);
            board.play(bestMove);

            imageView = bestMove.getImageView();
            imageView.setImageDrawable(currentPlayer.getSymbol());

            if (board.isBoardFull()) {
                tvTurn.setText(R.string.game_over);
                setGameDone(true);
                return;
            }
            if (board.isWinner(currentPlayer)) {
                tvTurn.setText(MessageFormat.format("{0} wins!", currentPlayer.getName()));
                setGameDone(true);
                return;
            }
        }

        currentPlayer = board.getCurrentPlayer();
        tvTurn.setText(MessageFormat.format("{0}'s turn", currentPlayer.getName()));

    }

    public void onRestartClick(View view) {
        if (gameDone) {
            initializeGame(mode);
        }
    }

    private Cell[][] getCells() {
        Cell[][] cells = new Cell[3][3];

        cells[0][0] = new Cell(0, 0, findViewById(R.id.iv_00));
        cells[0][1] = new Cell(0, 1, findViewById(R.id.iv_01));
        cells[0][2] = new Cell(0, 2, findViewById(R.id.iv_02));

        cells[1][0] = new Cell(1, 0, findViewById(R.id.iv_10));
        cells[1][1] = new Cell(1, 1, findViewById(R.id.iv_11));
        cells[1][2] = new Cell(1, 2, findViewById(R.id.iv_12));

        cells[2][0] = new Cell(2, 0, findViewById(R.id.iv_20));
        cells[2][1] = new Cell(2, 1, findViewById(R.id.iv_21));
        cells[2][2] = new Cell(2, 2, findViewById(R.id.iv_22));

        return cells;
    }

    private int getCoordinate(@NonNull String contentDescription, int index) {
        return Integer.parseInt(contentDescription.split(",")[index].trim());
    }

}