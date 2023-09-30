package com.example.tictactoe.models;

import android.widget.ImageView;

public class Cell {

    private int row;
    private int column;
    private ImageView imageView;

    public Cell(int row, int column, ImageView imageView) {
        this.row = row;
        this.column = column;
        this.imageView = imageView;
    }

    public void resetCell() {
        imageView.setImageDrawable(null);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", column=" + column + '}';
    }


}
