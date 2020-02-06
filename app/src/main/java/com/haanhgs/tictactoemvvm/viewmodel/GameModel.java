package com.haanhgs.tictactoemvvm.viewmodel;


import com.haanhgs.tictactoemvvm.model.Board;
import com.haanhgs.tictactoemvvm.model.Move;
import com.haanhgs.tictactoemvvm.model.Player;
import com.haanhgs.tictactoemvvm.model.State;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;

import static com.haanhgs.tictactoemvvm.model.State.Draw;
import static com.haanhgs.tictactoemvvm.model.State.HasResult;
import static com.haanhgs.tictactoemvvm.model.State.InProgress;

public class GameModel{

    private final Board board;
    public final ObservableArrayMap<String, String> buttons = new ObservableArrayMap<>();
    public final ObservableField<String> text = new ObservableField<>();

    private void restart(){
        String string = board.getCurrentPlayer().toString() + " to play";
        text.set(string);
    }

    public GameModel() {
        board = new Board();
        restart();
    }

    public void onButtonNewGamePressed(){
        board.restart();
        buttons.clear();
        restart();
    }

    private void showTextAfterEachMove(){
        if (board.getState() == HasResult){
            String string = board.getWinner() == null ? "" : board.getWinner().toString();
            text.set(string + " is winner");
        }else if (board.getState() == Draw){
            text.set("Draw");
        } else {
            String string =  board.getCurrentPlayer().toString() + " to play";
            text.set(string);
        }
    }

    private void showTextAfterEachMove(Move move){
        if (move.getState() == HasResult){
            text.set(move.getPlayer().toString() + " is winner");
        }else if (board.getState() == Draw){
            text.set("Draw");
        } else {
            String string =  board.getCurrentPlayer().toString() + " to play";
            text.set(string);
        }
    }


    public void onClickedCellAt(int row, int col){
        if (board.getState() == InProgress){
            Player player = board.makeMove(row, col);
            buttons.put("" + row + col, player == null ? null : player.toString());
            showTextAfterEachMove();
        }
    }

    public void moveFirst(){
        int currentMove = board.getGame().getCurrentMove();
        if (currentMove > 0){
            board.getGame().setCurrentMove(0);
            board.setCurrentPlayer(board.getGame().getMoves().get(0).getPlayer());
            board.setState(board.getGame().getMoves().get(0).getState());
            buttons.clear();
            showTextAfterEachMove(board.getGame().getMoves().get(0));
        }
    }

    public void moveNext(){
        int currentMove = board.getGame().getCurrentMove();
        if (currentMove < board.getGame().getMoves().size()){
            Move move = board.getGame().getMoves().get(currentMove);
            buttons.put("" + move.getRow() + move.getColumn(), move.getPlayer().toString());
            board.fillOneCell(move.getPlayer(), move.getRow(), move.getColumn());
            board.getGame().setCurrentMove(++currentMove);
            board.setState(move.getState());
            board.flipSide();
            showTextAfterEachMove(move);
        }
    }

    public void moveBack(){
        int currentMove = board.getGame().getCurrentMove();
        if (currentMove > 0){
            Move move = board.getGame().getMoves().get(currentMove - 1);
            buttons.remove("" + move.getRow() + move.getColumn());
            board.clearOneCell(move.getRow(), move.getColumn());
            board.getGame().setCurrentMove(currentMove - 1);
            board.setState(move.getState());
            board.flipSide();
            showTextAfterEachMove(move);
        }
    }

    public void moveLast(){
        int currentMove = board.getGame().getCurrentMove();
        int gameSize = board.getGame().getMoves().size();
        if (currentMove < gameSize){
            for (int i = currentMove; i < gameSize; i++){
                Move move = board.getGame().getMoves().get(i);
                buttons.put("" + move.getRow() + move.getColumn(), move.getPlayer().toString());
                board.fillOneCell(move.getPlayer(), move.getRow(), move.getColumn());
                board.flipSide();
                board.setState(move.getState());
                showTextAfterEachMove(move);
            }
            board.getGame().setCurrentMove(gameSize);
        }
    }



}
