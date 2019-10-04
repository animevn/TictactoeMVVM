package com.haanhgs.tictactoemvvm.viewmodel;

import com.haanhgs.tictactoemvvm.model.Board;
import com.haanhgs.tictactoemvvm.model.Player;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;

public class TictactoeModel implements ViewModel {

    private Board model;
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    public final ObservableField<String> winner = new ObservableField<>();

    public TictactoeModel(){
        model = new Board();
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

    public void onClickedCellAt(int row, int col){
        Player player = model.mark(row, col);
        cells.put("" + row + col, player == null ? null : player.toString());
        winner.set(model.getWinner() == null ? null : model.getWinner().toString());
    }

    public void onResetSelected(){
        model.restart();
        winner.set(null);
        cells.clear();
    }
}
