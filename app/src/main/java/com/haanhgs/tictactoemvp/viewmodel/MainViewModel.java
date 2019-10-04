package com.haanhgs.tictactoemvp.viewmodel;

import com.haanhgs.tictactoemvp.model.Board;
import com.haanhgs.tictactoemvp.model.Player;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;

public class MainViewModel implements ViewModel {

    private Board model;
    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    public final ObservableField<String> winner = new ObservableField<>();

    public MainViewModel(){
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

    public void onButtonClicked(int row, int col){
        Player player = model.mark(row, col);
        cells.put("" + row + col, player == null ? null : player.toString());
    }

    public void onResetSelected(){
        model.restart();
        winner.set(null);
        cells.clear();
    }
}
