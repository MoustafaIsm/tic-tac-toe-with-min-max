package com.example.tictactoe.models;

import android.widget.ImageView;

public class Board {

    private Cell[][] cells;
    private Player player1;
    private Player player2;

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

    public boolean playable(Cell cell) {
        return cell.getImageView().getDrawable() == null;
    }

    public void play(Cell cell) {

        if (!playable(cell)) {
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