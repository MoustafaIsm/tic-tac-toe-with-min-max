package com.example.tictactoe.models;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Cell[][] cells;
    private final Player player1;
    private final Player player2;

    public Board(Player player1, Player player2, Cell[][] cells) {
        this.player1 = player1;
        this.player2 = player2;
        this.cells = cells;
        resetBoard();
    }

    public void resetBoard() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.resetCell();
            }
        }

        player1.resetScore();
        player2.resetScore();

        player1.setTurn(true);
        player2.setTurn(false);
    }

    public Player getCurrentPlayer() {
        if (player1.isTurn()) {
            return player1;
        } else {
            return player2;
        }
    }

    public boolean isPlayable(Cell cell) {
        return cell.getImageView().getDrawable() == null;
    }

    public void play(Cell cell) {

        if (!isPlayable(cell)) {
            return;
        }

        if (player1.isTurn()) {
            cell.getImageView().setImageDrawable(player1.getSymbol());
        } else {
            cell.getImageView().setImageDrawable(player2.getSymbol());
        }

        changeTurn();
    }

    public boolean isBoardFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getImageView().getDrawable() == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isWinner(Player player) {
        return isHorizontalWinner(player) || isVerticalWinner(player) || isDiagonalWinner(player);
    }

    private boolean isHorizontalWinner(Player player) {
        for (Cell[] row : cells) {
            boolean isWinner = true;
            for (Cell cell : row) {
                if (cell.getImageView().getDrawable() != player.getSymbol()) {
                    isWinner = false;
                    break;
                }
            }

            if (isWinner) {
                return true;
            }
        }

        return false;
    }

    private boolean isVerticalWinner(Player player) {
        for (int i = 0; i < cells.length; i++) {
            boolean isWinner = true;
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[j][i].getImageView().getDrawable() != player.getSymbol()) {
                    isWinner = false;
                    break;
                }
            }

            if (isWinner) {
                return true;
            }
        }

        return false;
    }

    private boolean isDiagonalWinner(Player player) {
        boolean isWinner = true;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i][i].getImageView().getDrawable() != player.getSymbol()) {
                isWinner = false;
                break;
            }
        }

        if (isWinner) {
            return true;
        }

        isWinner = true;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i][cells.length - 1 - i].getImageView().getDrawable() != player.getSymbol()) {
                isWinner = false;
                break;
            }
        }

        return isWinner;
    }

    public void changeTurn() {
        player1.changeTurn();
        player2.changeTurn();
    }

    public Cell getNextMove(String mode) {
        Log.d("Board", "getNextMove: " + mode);
        if (mode.equals("Easy")) {
            return getRandomMove();
        } else {
            if (mode.equals("Medium")) {
                if (Math.random() < 0.5) {
                    return getRandomMove();
                }
            }
        }
        return getBestMove();
    }

    private Cell getBestMove() {

        Cell bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getImageView().getDrawable() != null) {
                    continue;
                }

                cell.getImageView().setImageDrawable(player2.getSymbol());
                int score = minimax(0, false);
                cell.getImageView().setImageDrawable(null);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = cell;
                }

            }
        }

        return bestMove;
    }

    private Cell getRandomMove() {
        List<Cell> availableMoves = new ArrayList<>();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getImageView().getDrawable() == null) {
                    availableMoves.add(cell);
                }
            }
        }

        Log.d("Board", "getRandomMove: " + availableMoves.size());

        if (!availableMoves.isEmpty()) {
            int randomIndex = (int) (Math.random() * availableMoves.size());
            Log.d("Board", "getRandomMove: " + randomIndex);
            return availableMoves.get(randomIndex);
        } else {
            return null;
        }
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (isWinner(player1)) {
            return -10;
        } else if (isWinner(player2)) {
            return 10;
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Cell[] row : cells) {
                for (Cell cell : row) {
                    if (cell.getImageView().getDrawable() == null) {
                        cell.getImageView().setImageDrawable(player2.getSymbol());
                        int score = minimax(depth + 1, false);
                        cell.getImageView().setImageDrawable(null);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Cell[] row : cells) {
                for (Cell cell : row) {
                    if (cell.getImageView().getDrawable() == null) {
                        cell.getImageView().setImageDrawable(player1.getSymbol());
                        int score = minimax(depth + 1, true);
                        cell.getImageView().setImageDrawable(null);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

}