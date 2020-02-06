package com.haanhgs.tictactoemvvm.viewmodel;


import com.haanhgs.tictactoemvvm.model.Board;
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

    public void onClickedCellAt(int row, int col){
        if (board.getState() == InProgress){
            Player player = board.makeMove(row, col);
            buttons.put("" + row + col, player == null ? null : player.toString());
            showTextAfterEachMove();
        }
    }



}
