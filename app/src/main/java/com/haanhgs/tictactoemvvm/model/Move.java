package com.haanhgs.tictactoemvvm.model;

import java.io.Serializable;

public class Move implements Serializable {

    private Player player;
    private int row;
    private int column;
    private State state;
    private static final long serialUID = 2279;

    public Move(Player player, int row, int column, State state) {
        this.player = player;
        this.row = row;
        this.column = column;
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
