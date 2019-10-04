package com.haanhgs.tictactoemvvm.viewmodel;

import com.haanhgs.tictactoemvvm.model.Board;
import com.haanhgs.tictactoemvvm.model.Player;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;
import static com.haanhgs.tictactoemvvm.model.GameState.Draw;
import static com.haanhgs.tictactoemvvm.model.GameState.Finished;

public class TictactoeModel implements ViewModel {

    private Board model;
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    public final ObservableField<String> text = new ObservableField<>();

    private void restart(){
        String string =  model.getCurrentTurn().toString() + " to play";
        text.set(string);
    }

    public TictactoeModel(){
        model = new Board();
        restart();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onPause() {}

    @Override
    public void onResume() {}

    @Override
    public void onDestroy() {}

    private void showTextAfterEachMove(){
        if (model.getState() == Finished){
            String string = model.getWinner() == null ? "" : model.getWinner().toString();
            text.set(string + " is winner");
        }else if (model.getState() == Draw){
            text.set("Draw");
        } else {
            String string =  model.getCurrentTurn().toString() + " to play";
            text.set(string);
        }
    }

    public void onClickedCellAt(int row, int col){
        Player player = model.mark(row, col);
        cells.put("" + row + col, player == null ? null : player.toString());
        showTextAfterEachMove();
    }

    public void onResetSelected(){
        model.restart();
        cells.clear();
        restart();
    }
}
