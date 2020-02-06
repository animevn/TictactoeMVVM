package com.haanhgs.tictactoemvvm.model;

import static com.haanhgs.tictactoemvvm.model.Player.O;
import static com.haanhgs.tictactoemvvm.model.Player.X;
import static com.haanhgs.tictactoemvvm.model.State.Draw;
import static com.haanhgs.tictactoemvvm.model.State.HasResult;
import static com.haanhgs.tictactoemvvm.model.State.InProgress;

public class Board {

    private Game game;
    private final Cell[][] cells = new Cell[3][3];
    private State state;
    private Player winner;
    private Player currentPlayer;

    public void clearCells(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public void restart(){
        clearCells();
        game = new Game();
        winner = null;
        currentPlayer = X;
        state = InProgress;
    }

    public Board() {
        restart();
    }

    private boolean isWinningMove(Player player, int row, int column){
        return
                cells[row][0].getPlayer() == player
                        && cells[row][1].getPlayer() == player
                        && cells[row][2].getPlayer() == player
                        || cells[0][column].getPlayer() == player
                        && cells[1][column].getPlayer() == player
                        && cells[2][column].getPlayer() == player
                        || cells[0][0].getPlayer() == player
                        && cells[1][1].getPlayer() == player
                        && cells[2][2].getPlayer() == player
                        || cells[0][2].getPlayer() == player
                        && cells[1][1].getPlayer() == player
                        && cells[2][0].getPlayer() == player;
    }

    private boolean isCoordiateValid(int coord){
        return coord >= 0 && coord <= 2;
    }

    private boolean isCellEmpty(int row, int column){
        return cells[row][column].getPlayer() == null;
    }

    private boolean isCellValid(int row, int column){
        return state == InProgress && isCoordiateValid(row)
                && isCoordiateValid(column) && isCellEmpty(row, column);
    }

    private boolean isBoardFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (cells[i][j].getPlayer() == null) return  false;
            }
        }
        return true;
    }

    public void flipSide(){
        currentPlayer = currentPlayer == X ? O : X;
    }

    //when users move back and forth with buttons, if they choose a new move
    //then the game will continue from that move, and all moves that come following
    //that move will be deleted.
    private void deleteMoveAfterCurrentMove(){
        int currentMove = game.getCurrentMove();
        if (currentMove < game.getMoves().size()){
            game.setMoves(game.getMoves().subList(0, currentMove));
            state = InProgress;
        }
    }

    //add current board move to game
    private void addMoveToGame(Player player, int row, int column){
        if (player != null){
            game.getMoves().add(new Move(player, row, column, state));
            game.setCurrentMove(game.getCurrentMove() + 1);
        }
    }

    public Player makeMove(int row, int column){
        Player player = null;
        if (isCellValid(row, column)){
            deleteMoveAfterCurrentMove();
            cells[row][column].setPlayer(currentPlayer);
            player = currentPlayer;
            if (isWinningMove(player, row, column)){
                state = HasResult;
                winner = player;
            }
            if (isBoardFull() && state == InProgress){
                state = Draw;
            }
            addMoveToGame(player, row, column);
            if (state == InProgress)flipSide();
        }
        return player;
    }

    public void clearOneCell(int row, int column){
        cells[row][column] = new Cell();
    }

    public void fillOneCell(Player player, int row, int column){
        if (isCellEmpty(row, column)) cells[row][column].setPlayer(player);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
