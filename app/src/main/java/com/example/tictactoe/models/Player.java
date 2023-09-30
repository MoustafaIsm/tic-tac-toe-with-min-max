package com.example.tictactoe.models;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class Player {

    private String name;
    private int score;
    private boolean isTurn;
    private final Drawable symbol;
    private boolean isComputer;

    public Player(String name, Drawable symbol) {
        this.name = name;
        this.score = 0;
        this.isTurn = false;
        this.symbol = symbol;
        this.isComputer = false;
    }

    public String getName() {
        return name;
    }

    public Drawable getSymbol() {
        return symbol;
    }

    public int getScore() {
        return score;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public void addScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public void changeTurn() {
        isTurn = !isTurn;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
        if (isComputer) {
            this.name = "Computer";
        }

    }

    @NonNull
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", isTurn=" + isTurn +
                '}';
    }

}
